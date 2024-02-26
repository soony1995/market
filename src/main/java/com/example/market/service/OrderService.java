package com.example.market.service;

import com.example.market.domain.*;
import com.example.market.dto.order.OrderCreateDto;
import com.example.market.dto.order.OrderDto;
import com.example.market.dto.order.OrderInfoDto;
import com.example.market.dto.order.OrderModifyStatus;
import com.example.market.exception.CartException;
import com.example.market.exception.CartItemException;
import com.example.market.exception.MemberException;
import com.example.market.exception.OrderItemException;
import com.example.market.repository.*;
import com.example.market.type.ErrCode;
import com.example.market.type.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.market.utils.SecurityUtils.getCurrentUsername;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public String addOrder(OrderCreateDto.Request request) {
        // 사용자 인증
        Member findMember = memberRepository.findByEmail(getCurrentUsername()).orElseThrow(() -> new MemberException(ErrCode.ACCOUNT_NOT_FOUND));

        if (findMember.getCart().getId() != request.getCartId()) {
            throw new CartException(ErrCode.CART_NOT_FOUND);
        }

        List<CartItem> findCartItems = cartItemRepository.findByCartId(request.getCartId()).orElseThrow(() -> new CartItemException(ErrCode.ITEM_NOT_FOUND));

        // Order 엔티티 생성 및 초기 설정
        Order newOrder = createOrder(findMember);

        // findCartItems의 요소들을 OrderItem 엔티티로 변환 및 저장
        orderItemRepository.saveAll(convertCartItemToOrderItems(request, findCartItems, newOrder));

        // 성공 후 CartItem 삭제
        cartItemRepository.deleteAll(findCartItems);

        return "성공!";
    }

    private Order createOrder(Member member) {
        Order order = Order.builder()
                .member(member)
                .status(OrderStatus.ORDER)
                .build();
        return orderRepository.save(order);
    }

    private static List<OrderItem> convertCartItemToOrderItems(OrderCreateDto.Request request, List<CartItem> findCartItems, Order newOrder) {
        return findCartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .order(newOrder)
                        .item(cartItem.getItem())
                        .count(cartItem.getCount())
                        .location(request.getLocation())
                        .orderPrice(cartItem.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDto.Response> findAllOrders() {
        Member currentMember = memberRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByMemberId(currentMember.getId()).orElseThrow(() -> new RuntimeException("order not found"));

        return orders.stream()
                .map(OrderDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderInfoDto.Response> findOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getMember().getEmail().equals(getCurrentUsername())) {
            throw new RuntimeException("Access deny");
        }

        List<OrderItem> findOrderItem = orderItemRepository.findByOrderId(order.getId()).orElseThrow(() -> new OrderItemException(ErrCode.ORDER_NOT_FOUND));

        return findOrderItem.stream()
                .map(orderItem -> OrderInfoDto.Response.fromEntity(order, orderItem))
                .collect(Collectors.toList());
    }

    @Transactional
    public String modifyOrderStatus(long orderId, OrderModifyStatus.Request request) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));

        if (!findOrder.getMember().getEmail().equals(getCurrentUsername())) {
            throw new RuntimeException("Access deny");
        }

        findOrder.modifyOrderStatus(request.getOrderStatus());

        return "성공!";
    }
}
