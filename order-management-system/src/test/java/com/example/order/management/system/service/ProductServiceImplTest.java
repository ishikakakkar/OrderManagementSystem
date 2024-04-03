package com.example.order.management.system.service;

import com.example.order.management.system.exception.ProductAlreadyExistsException;
import com.example.order.management.system.exception.ProductNotFoundException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductList() {
        Product product1 = new Product(
                "JBL Speaker",
                "Speakers",
                100,
                10000
        );
        Product product2 = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );
        ArrayList<Product> productList= new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        Mockito.when(productRepository.findAll())
                .thenReturn(productList);

        ArrayList<Product> returnList = productService.getProductList();

        assertEquals(returnList, productList);

    }

    @Test
    void testGetProductById_Existing() {
        Product product = new Product(
                "JBL Speaker",
                "Speakers",
                100,
                10000
        );

        Mockito.when(productRepository.findById(10))
                .thenReturn(Optional.of(product));

        Product returnProduct = productService.getProductById(10);

        assertEquals(returnProduct, product);
    }

    @Test
    void testGetProductById_NonExisting() {
        Mockito.when(productRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, ()-> productService.getProductById(10));
    }

    @Test
    void testAddProduct_NewProduct() {
        Product product = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        Mockito.when(productRepository.getProductByName("Titan Watch"))
                .thenReturn(null);

        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        String msg = productService.addProduct(product);

        assertEquals(msg, "Product Info added successfully");
    }

    @Test
    public void testAddProduct_ExistingProduct() {
        Product product = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        Mockito.when(productRepository.getProductByName("Titan Watch"))
                .thenReturn(product);

        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        assertThrows(ProductAlreadyExistsException.class, () -> productService.addProduct(product));

    }

    @Test
    void testUpdateProduct_ExistingProduct() {
        Product updateProduct = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        Product existingProduct = new Product(
                "Titan Watch",
                "Watch",
                10,
                1000
        );

        Mockito.when(productRepository.findById(1))
                .thenReturn(Optional.of(existingProduct));

        Mockito.when(productRepository.save(updateProduct))
                .thenReturn(updateProduct);

        Product newProduct = productService.updateProduct(1, updateProduct);

        assertEquals(newProduct, updateProduct);
    }

    @Test
    void testUpdateProduct_NonExistingProduct() {
        Product updateProduct = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        Mockito.when(productRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1, updateProduct));
    }

//    @Test
//    void testUpdateProductQuantityForPlacingOrder()
//    {
//        OrderItem orderItem1 = new OrderItem(
//                100,
//                2
//        );
//        OrderItem orderItem2 = new OrderItem(
//                201,
//                5
//        );
//        ArrayList<OrderItem> orderItemList = new ArrayList<>();
//        orderItemList.add(orderItem1);
//        orderItemList.add(orderItem2);
//
//        Product product = new Product(
//                "Titan Watch",
//                "Wrist Watch",
//                20,
//                2000
//        );
//
//        Mockito.when(productRepository.findById(Mockito.any(Integer.class)))
//                .thenReturn(Optional.of(product));
//
//        Mockito.doNothing()
//                .when(productService)
//                .updateProduct(Mockito.any(Integer.class), product);
//
//
//    }
}