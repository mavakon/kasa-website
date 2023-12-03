package com.bdsk.kasa.Service;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.domain.ShoppingCart;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.service.ShoppingCartService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ShoppingCartServiceTest {

    private ShoppingCartService shoppingCartService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCartService = new ShoppingCartService(productRepository);
    }

    @Test
    void testLoadShoppingCartFromCookie() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ShoppingCart emptyCart = shoppingCartService.loadShoppingCartFromCookie(request);
        assertEquals(0, emptyCart.getProducts().size());
    }

    @Test
    void testSaveShoppingCartToCookie() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());
        shoppingCartService.saveShoppingCartToCookie(shoppingCart, response);
    }

    @Test
    void testClearShoppingCart() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        shoppingCartService.clearShoppingCart(response);
    }

    @Test
    void testSerializeShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());
        String json = shoppingCartService.serializeShoppingCart(shoppingCart);
    }

    @Test
    void testDeserializeShoppingCart() {
        Map<Integer, Integer> cartMap = new HashMap<>();
        cartMap.put(1, 2);
        String json = new Gson().toJson(cartMap);

        Product mockProduct = new Product();
        mockProduct.setId(1);
        mockProduct.setName("34tf");
        mockProduct.setDescription("fbentt");
        mockProduct.setPrice(1234.0);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(mockProduct));

        ShoppingCart shoppingCart = shoppingCartService.deserializeShoppingCart(json);
        assertEquals(1, shoppingCart.getProducts().size());
    }
}
