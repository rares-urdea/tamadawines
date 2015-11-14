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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellResponse)) return false;

        SellResponse that = (SellResponse) o;

        return status == that.status && !(products != null ? !products.equals(that.products) : that.products != null);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SellResponse{" +
                "status=" + status +
                ", products=" + products +
                '}';
    }
}
