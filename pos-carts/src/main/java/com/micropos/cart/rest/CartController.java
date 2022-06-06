package com.micropos.cart.rest;

import com.micropos.api.CartsApi;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.cart.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class CartController implements CartsApi {

    private final CartMapper cartMapper;
    private CartService cartService;

    public CartController(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;

    }

    @Override
    public ResponseEntity<CartDto> addItemToCart(Integer cartId, CartItemDto cartItemDto) {
        Optional<Cart> cart = cartService.getCart(cartId);
        if (cart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // better check cartItem is valid.
        CartDto cartDto = cartMapper.toCartDto(cart.get());
        Item item = cartMapper.toItem(cartItemDto, cartDto);

//        System.out.println(cart.get());
        Cart _cart = cartService.add(cart.get(), item);
        if (_cart == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
                cartMapper.toCartDto(_cart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartDto> createCart() {
        Cart cart = cartService.createCart();
        if (cart != null) {
            return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CartDto>> listCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return new ResponseEntity<>(cartMapper.toCartDtos(carts), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartDto> showCartById(Integer cartId) {
        return CartsApi.super.showCartById(cartId);
    }

    @Override
    public ResponseEntity<Double> showCartTotal(Integer cartId) {
        Double total = cartService.checkout(cartId);

        if (total == -1d) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(total);
    }

    @GetMapping(value= "/columns/name/item")
    public List<String> tableColumnsName()
    {
        List<String> Columns = new ArrayList<String>();
        Field[] fields = Item.class.getDeclaredFields();

        for (Field field : fields) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                Columns.add(col.name());
                System.out.println("Columns: "+col);
            }
        }
        return Columns;
    }

    @GetMapping(value= "/columns/name/cart")
    public List<String> tableColumnsNameCart()
    {
        List<String> Columns = new ArrayList<String>();
        Field[] fields = Cart.class.getDeclaredFields();

        for (Field field : fields) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                Columns.add(col.name());
                System.out.println("Columns: "+col);
            }
        }
        return Columns;
    }
}
