package com.example.market.service;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import com.example.market.domain.Member;
import com.example.market.dto.cart.CartAddItemsDto;
import com.example.market.dto.cart.CartDeleteItemsDto;
import com.example.market.dto.cart.CartPatchItemsDto;
import com.example.market.dto.cart.CartQueryItemsDto;
import com.example.market.exception.CartItemException;
import com.example.market.exception.ItemException;
import com.example.market.exception.MemberException;
import com.example.market.repository.CartItemRepository;
import com.example.market.repository.ItemRepository;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String addCartItems(List<CartAddItemsDto.Request> request) {
        Cart cart = findMemberByRepository().getCart();
        List<CartItem> cartItems = handleAddItemsToCart(request, cart);

        cartItemRepository.saveAll(cartItems);
        return "성공!";
    }

    @Transactional(readOnly = true)
    public List<CartQueryItemsDto.Response> findCartItems() {
        List<CartItem> cartItems = cartItemRepository.findByCartId(
                findMemberByRepository().getCart().getId()).orElseThrow(()
                -> new CartItemException(ErrCode.CART_NOT_FOUND));

        return cartItems.stream()
                .map(CartQueryItemsDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // TODO: QueryDsl을 이용해 수정해야함.
    @Transactional
    public String modifyCartItems(List<CartPatchItemsDto.Request> request) {
        // find cart id
        Cart cart = findMemberByRepository().getCart();

        // TODO: queryDsl을 이용해 짜도록 하자.
        return "성공!";
    }

    @Transactional
    public String removeCartItems(CartDeleteItemsDto.Request request) {
        Cart cart = findMemberByRepository().getCart();

        List<CartItem> findCartItems = cartItemRepository.findAllByCartIdAndItemIds(cart.getId(), request.getItemId())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CartItemException(ErrCode.ITEM_NOT_DELETED));

        cartItemRepository.deleteAll(findCartItems);

        return "성공!";
    }

    // TODO: MemberService에 넣는게 좋을 것 같습니당.
    private Member findMemberByRepository() {
        return memberRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> new MemberException(ErrCode.ACCOUNT_NOT_EXIST));
    }

    // TODO : handle add 둘중 선택 아니면 다른거로 (동사가 2개임)
    private List<CartItem> handleAddItemsToCart(List<CartAddItemsDto.Request> requests, Cart cart) {
        return requests.stream()
                .map(request -> {
                    Item item = itemRepository.findById(request.getItemId())
                            .orElseThrow(() -> new ItemException(ErrCode.ITEM_NOT_FOUND));

                    // 재고 확인
                    if (item.getStock() < request.getCount()) {
                        throw new ItemException(ErrCode.STOCK_NOT_ENOUGH);
                    }

                    // 카트 존재 확인
                    Optional<CartItem> existingCartItem =
                            cartItemRepository.findByItemIdAndCartId(item.getId(), cart.getId())
                                    .map(cartItem -> {
                                        CartItem cartItem = existingCartItem.get();
                                        cartItem.increaseCount(request.getCount(), item.getPrice() * request.getCount());
                                        return cartItem;
                                    })
                                    .orElseGet()

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

    private static void checkStockIsValid(CartAddItemsDto.Request request, Item item) {
        if (item.getStock() < request.getCount()) {
            throw new ItemException(ErrCode.STOCK_NOT_ENOUGH);
        }
    }
}
