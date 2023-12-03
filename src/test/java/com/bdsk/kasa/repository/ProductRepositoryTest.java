package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    private ProductRepository repository;

    @Mock
    private Product mockedProduct;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        repository = new ProductRepository();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void saveProduct_shouldSaveProduct() throws IOException {
        // Arrange
        Product productToSave = new Product();
        productToSave.setId(1);
        productToSave.setName("prod");
        productToSave.setDescription("b,ob;");
        productToSave.setPrice(1234);
        productToSave.setAuthorId(1);
        when(mockedProduct.getId()).thenReturn(1);
        when(mockedProduct.getName()).thenReturn("prod");
        when(mockedProduct.getPrice()).thenReturn(1234.0);

        Product savedProduct = repository.save(productToSave);

        assertEquals(productToSave, savedProduct);
        Optional<Product> retrievedProduct = repository.findById(1);
        assertTrue(retrievedProduct.isPresent());
        assertEquals("prod", retrievedProduct.get().getName());
    }

    @Test
    void findById_shouldReturnProduct() throws IOException {
        Product productToSave = new Product();
        productToSave.setId(1);
        productToSave.setName("prod");
        productToSave.setDescription("b,ob;");
        productToSave.setPrice(1234);
        productToSave.setAuthorId(1);
        repository.save(productToSave);

        Optional<Product> retrievedProduct = repository.findById(1);

        assertTrue(retrievedProduct.isPresent());
        assertEquals("prod", retrievedProduct.get().getName());
    }

    @Test
    void findById_shouldReturnEmptyOptionalForNonExistingProduct() throws IOException {

        Optional<Product> retrievedProduct = repository.findById(999);

        assertFalse(retrievedProduct.isPresent());
    }

    @Test
    void findByName_shouldReturnProduct() throws IOException {
        Product productToSave = new Product();
        productToSave.setId(1);
        productToSave.setName("prod");
        productToSave.setDescription("b,ob;");
        productToSave.setPrice(1234);
        productToSave.setAuthorId(1);
        repository.save(productToSave);

        Optional<Product> retrievedProduct = repository.findByName("prod");

        assertTrue(retrievedProduct.isPresent());
        assertEquals(1234.0, retrievedProduct.get().getPrice());
    }

    @Test
    void findByName_shouldReturnEmptyOptionalForNonExistingProduct() throws IOException {

        Optional<Product> retrievedProduct = repository.findByName("NonExistingProduct");

        assertFalse(retrievedProduct.isPresent());
    }

    @Test
    void findAll_shouldReturnAllProducts() throws IOException {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("prod1");
        product1.setDescription("b,ob;");
        product1.setPrice(1234);
        product1.setAuthorId(1);
        Product product2 = new Product();
        product2.setId(2);
        product2.setName("prod2");
        product2.setDescription("b,ob;");
        product2.setPrice(12345);
        product2.setAuthorId(2);
        repository.save(product1);
        repository.save(product2);

        List<Product> allProducts = repository.findAll();

        assertEquals(2, allProducts.size());
        assertEquals("prod2", allProducts.get(1).getName());
        assertEquals(1234.0, allProducts.get(0).getPrice());
    }

    @Test
    void deleteById_shouldDeleteProduct() throws IOException {
        Product productToSave = new Product();
        productToSave.setId(1);
        productToSave.setName("prod");
        productToSave.setDescription("b,ob;");
        productToSave.setPrice(1234);
        productToSave.setAuthorId(1);
        repository.save(productToSave);

        repository.deleteById(1);

        Optional<Product> retrievedProduct = repository.findById(1);
        assertFalse(retrievedProduct.isPresent());
    }
}