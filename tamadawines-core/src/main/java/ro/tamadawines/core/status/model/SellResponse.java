package ro.tamadawines.core.status.model;

import ro.tamadawines.core.dto.ProductDto;

import java.util.List;

public class SellResponse {

    private Status status;
    private List<ProductDto> products;

    public SellResponse() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
