package ro.tamadawines.core;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.tamadawines.core.dto.ProductDto;
import ro.tamadawines.core.dto.ShoppingOrder;
import ro.tamadawines.core.main.TamadawinesConfiguration;
import ro.tamadawines.core.resource.ProductResource;
import ro.tamadawines.core.status.model.SellResponse;
import ro.tamadawines.core.status.model.Status;
import ro.tamadawines.persistence.dao.CounterDao;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.model.Counter;
import ro.tamadawines.persistence.model.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProducResourceTest {

    private static final String PRODUCT_A_NAME = "ProductA";
    private static final String PRODUCT_B_NAME = "ProductB";
    private static final String PRODUCT_C_NAME = "ProductC";
    private static final int PRODUCT_A_ID = 1;
    private static final int PRODUCT_A_STOCK = 5;
    private static final int PRODUCT_B_ID = 2;
    private static final int PRODUCT_B_STOCK = 9999;
    private static final int PRODUCT_C_ID = 2;
    private static final int PRODUCT_C_STOCK = 98;
    private static ShoppingOrder availableProductsShoppingOrder;
    private static ShoppingOrder availChangedShoppingOrder;
    private static Product productA;
    private static Product productB;
    private static Product productC;
    private static ProductDto productDtoA;
    private static ProductDto productDtoB;
    private static ProductDto productDtoExceedingStock;

    @Mock
    private ProductDao productDao;
    @Mock
    private CounterDao counterDao;

    private ProductResource productResource;

    @BeforeClass
    public static void init() {
        availableProductsShoppingOrder = new ShoppingOrder();
        List<ProductDto> availableProductsList = new ArrayList<>();
        productDtoA = new ProductDto(1, PRODUCT_A_NAME, 1);
        productDtoB = new ProductDto(2, PRODUCT_B_NAME, 50);
        productDtoExceedingStock = new ProductDto(3, PRODUCT_C_NAME, 99);
        availableProductsList.add(productDtoA);
        availableProductsList.add(productDtoB);
        availableProductsShoppingOrder.setProducts(availableProductsList);

        availChangedShoppingOrder = new ShoppingOrder();
        List<ProductDto> availChangedProductList = new ArrayList<>();
        availChangedProductList.add(productDtoA);
        availChangedProductList.add(productDtoExceedingStock);
        availChangedShoppingOrder.setProducts(availChangedProductList);

        productA = new Product();
        productA.setId(PRODUCT_A_ID);
        productA.setName(PRODUCT_A_NAME);
        productA.setStock(PRODUCT_A_STOCK);
        productB = new Product();
        productB.setId(PRODUCT_B_ID);
        productB.setName(PRODUCT_B_NAME);
        productB.setStock(PRODUCT_B_STOCK);
        productC = new Product();
        productC.setId(PRODUCT_C_ID);
        productC.setName(PRODUCT_C_NAME);
        productC.setStock(PRODUCT_C_STOCK);
    }

    @Before
    public void setup() {
        productResource = new ProductResource(productDao, counterDao, new TamadawinesConfiguration());

        when(productDao.findById(1)).thenReturn(Optional.fromNullable(productA));
        when(productDao.findById(2)).thenReturn(Optional.fromNullable(productB));
        when(productDao.findById(3)).thenReturn(Optional.fromNullable(productC));
        when(counterDao.getByName(anyString())).thenReturn(new Counter() {{
            setValue(1l);
        }});
    }

    @Test
    public void testSuccessResponseStatus() {
        SellResponse successResponse = productResource.sellProducts(availableProductsShoppingOrder);
        assertEquals(Status.SUCCESS, successResponse.getStatus());
    }

    @Test
    public void testSuccessResponseListSize() {
        SellResponse successResponse = productResource.sellProducts(availableProductsShoppingOrder);
        assertEquals(2, successResponse.getProducts().size());
    }

    @Test
    public void testSuccessResponseProductA() {
        SellResponse successResponse = productResource.sellProducts(availableProductsShoppingOrder);
        assertEquals(productDtoA, successResponse.getProducts().get(0));
    }

    @Test
    public void testSuccessResponseProductB() {
        SellResponse successResponse = productResource.sellProducts(availableProductsShoppingOrder);
        assertEquals(productDtoB, successResponse.getProducts().get(1));
    }

    @Test
    public void testUnavailableProductsResponseStatus() {
        SellResponse availChangedResponse = productResource.sellProducts(availChangedShoppingOrder);
        assertEquals(Status.AVAILABILITY_CHANGE, availChangedResponse.getStatus());
    }

    @Test
    public void testUnavailableProductsListSize() {
        SellResponse availChangedResponse = productResource.sellProducts(availChangedShoppingOrder);
        assertEquals(1, availChangedResponse.getProducts().size());
    }

    @Test
    public void testUnavailableProductsCorrectQuantity() {
        SellResponse availChangedResponse = productResource.sellProducts(availChangedShoppingOrder);
        assertNotEquals(productDtoExceedingStock, availChangedResponse.getProducts().get(0));
    }

    @Test
    public void testUnavailableProductsName() {
        SellResponse availChangedResponse = productResource.sellProducts(availChangedShoppingOrder);
        assertEquals(productDtoExceedingStock.getName(), availChangedResponse.getProducts().get(0).getName());
    }

    @Test
    public void testUnavailableProductsId() {
        SellResponse availChangedResponse = productResource.sellProducts(availChangedShoppingOrder);
        assertEquals(productDtoExceedingStock.getId(), availChangedResponse.getProducts().get(0).getId());
    }
}
