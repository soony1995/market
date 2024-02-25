package com.example.market.service;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import com.example.market.domain.Member;
import com.example.market.dto.cart.CartAddItems;
import com.example.market.dto.cart.CartPatchItems;
import com.example.market.dto.cart.CartQueryItems;
import com.example.market.exception.CartItemException;
import com.example.market.exception.CustomException;
import com.example.market.exception.ItemException;
import com.example.market.exception.MemberException;
import com.example.market.repository.CartItemRepository;
import com.example.market.repository.ItemRepository;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.market.utils.SecurityUtils.getCurrentUsername;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public String addCartItems(List<CartAddItems.Request> request) {
        Cart cart = findMemberByRepository().getCart();

        List<CartItem> cartItems = handleAddItemsToCart(request, cart);

        cartItemRepository.saveAll(cartItems);

        return "성공!";
    }

    @Transactional(readOnly = true)
    public List<CartQueryItems.Response> getCartItems() {
        List<CartItem> cartItems = cartItemRepository.findByCartId(findMemberByRepository().getCart().getId()).orElseThrow(() -> new CartItemException(ErrCode.CART_NOT_FOUND));

        return cartItems.stream()
                .map(CartQueryItems.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public String patchCartItems(List<CartPatchItems.Request> request) {
        // find cart id
        Cart cart = findMemberByRepository().getCart();

        // TODO: queryDsl을 이용해 짜도록 하자.
        return "성공!";
    }

    @Transactional
    public String deleteCartItems(List<Long> ids) {
        Cart cart = findMemberByRepository().getCart();

        // ID 목록에 해당하는 Person 엔티티들을 조회
        List<CartItem> findCartItems = cartItemRepository.findAllByCartIdAndIds(cart.getId(), ids)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CartItemException(ErrCode.ITEM_NOT_DELETED));

        // 조회된 엔티티들을 삭제
        cartItemRepository.deleteAll(findCartItems);

        return "성공!";
    }

    private Member findMemberByRepository() {
        return memberRepository.findByEmail(getCurrentUsername()).orElseThrow(() -> new MemberException(ErrCode.ACCOUNT_NOT_FOUND));
    }

    private List<CartItem> handleAddItemsToCart(List<CartAddItems.Request> requests, Cart cart) {
        return requests.stream()
                .map(request -> {
                    Item item = itemRepository.findById(request.getItemId())
                            .orElseThrow(() -> new ItemException(ErrCode.ITEM_NOT_FOUND));

                    // 재고 확인
                    checkStockIsValid(request, item);

                    // 카트 존재 확인
                    Optional<CartItem> existingCartItem = cartItemRepository.findByItemIdAndCartId(item.getId(), cart.getId());
                    if (existingCartItem.isPresent()) {
                        CartItem cartItem = existingCartItem.get();
                        cartItem.increaseCount(request.getCount(), item.getPrice() * request.getCount());
                        return cartItem;
                    } else {
                        return request.toEntity(cart, item);
                    }
                })
                .collect(Collectors.toList());
    }

    private static void checkStockIsValid(CartAddItems.Request request, Item item) {
        if (item.getStock() < request.getCount()) {
            throw new ItemException(ErrCode.STOCK_NOT_ENOUGH);
        }
    }
}
