package com.example.market.service;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import com.example.market.domain.Member;
import com.example.market.dto.cart.CartAddItemsDto;
import com.example.market.dto.cart.CartRemoveItemsDto;
import com.example.market.dto.cart.CartPatchItemsDto;
import com.example.market.dto.cart.CartQueryItemsDto;
import com.example.market.exception.CustomException;
import com.example.market.repository.CartItemRepository;
import com.example.market.repository.ItemRepository;
import com.example.market.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.market.type.ErrCode.*;
import static com.example.market.utils.SecurityUtils.getCurrentUsername;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public List<CartAddItemsDto.Response> addCartItems(List<CartAddItemsDto.Request> request) {
        Member member = findMemberByCurrentUsername();
        Cart cart = member.getCart();
        List<CartItem> cartItems = convertRequestToCartItems(request, cart);
        cartItemRepository.saveAll(cartItems);

        return cartItems.stream()
                .map(CartItem::convertToCartAddItemsDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CartQueryItemsDto.Response> findCartItems() {
        Member member = findMemberByCurrentUsername();
        Cart cart = member.getCart();
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId())
                .orElseThrow(() -> new CustomException(CART_NOT_EXIST));

        return cartItems.stream()
                .map(CartItem::convertToCartQueryItemDto)
                .collect(Collectors.toList());
    }

    // TODO: QueryDsl을 이용해 수정해야함.
    @Transactional
    public String modifyCartItems
    (List<CartPatchItemsDto.Request> request) {
        // find cart id
//        Cart cart = findMemberByRepository().getCart();

        // TODO: queryDsl을 이용해 짜도록 하자.
        return "성공!";
    }

    @Transactional
    public List<CartRemoveItemsDto.Response> removeCartItems(CartRemoveItemsDto.Request request) {
        Cart cart = findMemberByCurrentUsername().getCart();
        List<CartItem> cartItems = cartItemRepository.findAllByCartIdAndItemIds(cart.getId(), request.getItemId())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CustomException(ITEM_NOT_DELETED));
        cartItemRepository.deleteAll(cartItems);

        return cartItems.stream()
                .map(CartItem::convertToCartRemoveItemsDto)
                .collect(Collectors.toList());
    }

    private Member findMemberByCurrentUsername() {
        return memberRepository.findByEmail(getCurrentUsername()).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private List<CartItem> convertRequestToCartItems
            (List<CartAddItemsDto.Request> requests, Cart cart) {
        return requests.stream()
                .map(request -> {
                    Item item = itemRepository.findById(request.getItemId())
                            .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST));
                    if (item.getStock() < request.getCount()) {
                        throw new CustomException(ITEM_STOCK_NOT_ENOUGH);
                    }
                    return cartItemRepository.findByItemIdAndCartId(item.getId(), cart.getId())
                            .map(cartItem -> {
                                cartItem.increaseCount(request.getCount(), item.getPrice() * request.getCount());
                                return cartItem;
                            })
                            .orElseGet(() -> request.convertToCartItem(cart, item));
                })
                .collect(Collectors.toList());
    }
}
