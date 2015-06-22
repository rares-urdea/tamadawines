package ro.tamadawines.core.service;

import ro.tamadawines.core.dto.ProductDto;
import ro.tamadawines.core.status.model.SellResponse;
import ro.tamadawines.core.status.model.Status;

import java.util.List;

public class SellResponseAssemblerService {

    public SellResponseAssemblerService() {
    }

    public SellResponse buildSuccessResponse(List<ProductDto> products) {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setProducts(products);
        sellResponse.setStatus(Status.SUCCESS);

        return sellResponse;
    }

    public SellResponse buildAvailChangeResponse(List<ProductDto> productsWithNoAvail) {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setProducts(productsWithNoAvail);
        sellResponse.setStatus(Status.AVAILABILITY_CHANGE);

        return sellResponse;
    }

    public SellResponse buildUtterFailureResponse() {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setStatus(Status.UTTER_FAILURE);

        return sellResponse;
    }

    public SellResponse buildProductsNotFoundResponse(List<ProductDto> unavailableProducts) {
        SellResponse sellResponse = new SellResponse();
        sellResponse.setProducts(unavailableProducts);
        sellResponse.setStatus(Status.PRODUCT_NOT_FOUND);

        return sellResponse;
    }
}
