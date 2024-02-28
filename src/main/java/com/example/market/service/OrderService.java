package com.example.market.service;

import com.example.market.domain.*;
import com.example.market.dto.order.OrderCreateDto;
import com.example.market.dto.order.OrdersDto;
import com.example.market.dto.order.OrderItemsDto;
import com.example.market.dto.order.OrderModifyStatusDto;
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
    public List<OrderCreateDto.Response> addOrder(OrderCreateDto.Request request) {
        Member member = findMemberByCurrentUsername();
        Order newOrder = createOrder(member);
        List<CartItem> cartItems = cartItemRepository.findByCartId(request.getCartId())
                .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST));
        List<OrderItem> orderItems = orderItemRepository.saveAll(convertCartItemToOrderItems(request, cartItems, newOrder));
        cartItemRepository.deleteAll(cartItems);

        return orderItems.stream()
                .map(OrderItem::convertToOrderCreateDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrdersDto.Response> findAllOrders() {
        Member member = findMemberByCurrentUsername();
        List<Order> orders = orderRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));

        return orders.stream()
                .map(Order::convertToOrderDetailsDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderItemsDto.Response> findOrder(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST));

        return orderItems.stream()
                .map(OrderItem::convertToOrderItemsDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderModifyStatusDto.Response modifyOrderStatus(long orderId, OrderModifyStatusDto.Request request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ORDER_NOT_EXIST));
        order.modifyOrderStatus(request.getOrderStatus());

        return order.convertToOrderModifyStatusDto();
    }

    private Member findMemberByCurrentUsername() {
        return memberRepository.findByEmail(getCurrentUsername()).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
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
}
