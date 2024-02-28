package com.example.market.service;

import com.example.market.domain.*;
import com.example.market.dto.order.OrderCreateDto;
import com.example.market.dto.order.OrderDto;
import com.example.market.dto.order.OrderInfoDto;
import com.example.market.dto.order.OrderModifyStatus;
import com.example.market.exception.CustomException;
import com.example.market.repository.*;
import com.example.market.type.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.market.type.ErrCode.*;
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
        Member findMember = memberRepository.findByEmail(getCurrentUsername()).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        if (findMember.getCart().getId() != request.getCartId()) {
            throw new CustomException(CART_NOT_EXIST);
        }
        List<CartItem> findCartItems = cartItemRepository.findByCartId(request.getCartId()).orElseThrow(() -> new CustomException(ITEM_NOT_EXIST));
        Order newOrder = createOrder(findMember);
        orderItemRepository.saveAll(convertCartItemToOrderItems(request, findCartItems, newOrder));
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
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        List<Order> orders = orderRepository.findByMemberId(currentMember.getId()).orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));

        return orders.stream()
                .map(OrderDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderInfoDto.Response> findOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));
        if (!order.getMember().getEmail().equals(getCurrentUsername())) {
            throw new CustomException(MEMBER_NOT_AUTHORIZATION);
        }
        List<OrderItem> findOrderItem = orderItemRepository.findByOrderId(order.getId()).orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));

        return findOrderItem.stream()
                .map(orderItem -> OrderInfoDto.Response.fromEntity(order, orderItem))
                .collect(Collectors.toList());
    }

    @Transactional
    public String modifyOrderStatus(long orderId, OrderModifyStatus.Request request) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));
        if (!findOrder.getMember().getEmail().equals(getCurrentUsername())) {
            throw new CustomException(MEMBER_NOT_AUTHORIZATION);
        }
        findOrder.modifyOrderStatus(request.getOrderStatus());

        return "성공!";
    }
}
