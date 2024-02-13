package com.example.market.service;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import com.example.market.domain.Member;
import com.example.market.dto.cart.CartAddItems;
import com.example.market.exception.ItemException;
import com.example.market.exception.MemberException;
import com.example.market.repository.CartItemRepository;
import com.example.market.repository.ItemRepository;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.market.util.SecurityUtils.getCurrentUsername;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Override
    public boolean addItem(List<CartAddItems.Request> requests) {
        // 토큰에서 username 추출
        String username = getCurrentUsername();
        if (username == null) {
            throw new MemberException(ErrCode.ACCOUNT_NOT_FOUND);
        }

        // username으로 Member.Cart를 추출 후 request에서 itemID를 받아 Item 객체 생성.
        // 이미 같은 item이 존재할 경우 기존의 item의 가격과 수량에 더함.
        Member findMember = memberRepository.findByEmail(username).orElseThrow(() -> new MemberException(ErrCode.ACCOUNT_NOT_FOUND));
        Cart cart = findMember.getCart();

        List<CartItem> cartItems = requests.stream()
                .map(request -> {
                    Item item = itemRepository.findById(request.getItemId())
                            .orElseThrow(() -> new ItemException(ErrCode.ITEM_NOT_FOUND));
                    // 재고 확인
                    if (item.getStock() < request.getCount()) {
                        throw new ItemException(ErrCode.STOCK_NOT_ENOUGH);
                    }
                    Optional<CartItem> existingCartItem = cartItemRepository.findByItemIdAndCartId(item.getId(), cart.getId());
                    if (existingCartItem.isPresent()) {
                        CartItem cartItem = existingCartItem.get();
                        cartItem.increaseCount(request.getCount(), request.getPrice());
                        return cartItem;
                    } else {
                        return request.toEntity(cart, item);
                    }
                })
                .collect(Collectors.toList());

        nullPointException

                try {}
        catch (NullPointerException e) {
                    new ItemException("메세지!", e)
        }

        // list를 저장할 때에는 saveAll을 쓰자.
        // ASK: 에러처리는 어떻게?
        cartItemRepository.saveAll(cartItems);

        return true;
    }
}
