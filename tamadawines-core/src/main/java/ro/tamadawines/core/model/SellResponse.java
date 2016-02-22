package ro.tamadawines.core.model;

import ro.tamadawines.core.dto.ProductDto;

import java.util.List;

public class SellResponse {

    private String verboseMessage;
    private List<ProductDto> products;

    public SellResponse() {
    }

    public String getVerboseMessage() {
        return verboseMessage;
    }

    public void setVerboseMessage(String verboseMessage) {
        this.verboseMessage = verboseMessage;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "SellResponse{" +
                "verboseMessage=" + verboseMessage +
                ", products=" + products +
                '}';
    }
}
