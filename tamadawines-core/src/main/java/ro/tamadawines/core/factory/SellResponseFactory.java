package ro.tamadawines.core.factory;

import ro.tamadawines.core.dto.ProductDto;
import ro.tamadawines.core.status.model.SellResponse;
import ro.tamadawines.core.status.model.Status;

import java.util.List;

public final class SellResponseFactory {

    private SellResponseFactory() {}

    public static SellResponse buildSuccessResponse(List<ProductDto> products) {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setProducts(products);
        sellResponse.setStatus(Status.SUCCESS);

        return sellResponse;
    }

    public static SellResponse buildAvailChangeResponse(List<ProductDto> productsWithNoAvail) {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setProducts(productsWithNoAvail);
        sellResponse.setStatus(Status.AVAILABILITY_CHANGE);

        return sellResponse;
    }

    public static SellResponse buildUtterFailureResponse() {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setStatus(Status.UTTER_FAILURE);

        return sellResponse;
    }

    public static SellResponse buildProductsNotFoundResponse(List<ProductDto> unavailableProducts) {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setProducts(unavailableProducts);
        sellResponse.setStatus(Status.PRODUCT_NOT_FOUND);

        return sellResponse;
    }
}
