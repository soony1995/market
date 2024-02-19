package com.example.market.service;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import com.example.market.domain.Member;
import com.example.market.dto.cart.CartAddItems;
import com.example.market.dto.cart.CartQueryItems;
import com.example.market.dto.item.ItemInfo;
import com.example.market.exception.CartItemException;
import com.example.market.exception.ItemException;
import com.example.market.exception.MemberException;
import com.example.market.repository.CartItemRepository;
import com.example.market.repository.ItemRepository;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.market.utils.SecurityUtils.getCurrentUsername;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public String addItemsToCart(List<CartAddItems.Request> requests) {
        Cart cart = findMemberByRepository().getCart();

        List<CartItem> cartItems = handleAddItemsToCart(requests, cart);

        List<CartItem> savedCartItems = cartItemRepository.saveAll(cartItems);

        if (savedCartItems.size() != 0) {
            return "성공";
        } else {
            return "실패";
        }
    }

    @Transactional(readOnly = true)
    public List<CartQueryItems.Response> getItemsFromCart() {
        List<CartItem> cartItems = cartItemRepository.findByCartId(findMemberByRepository().getCart().getId()).orElseThrow(() -> new CartItemException(ErrCode.CART_NOT_FOUND));

        return cartItems.stream()
                .map(CartQueryItems.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteItemsFromCart() {
        Member member = findMemberByRepository();
        return true;
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
