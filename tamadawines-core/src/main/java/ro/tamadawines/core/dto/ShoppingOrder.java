package ro.tamadawines.core.dto;

import ro.tamadawines.persistence.model.Courier;

import java.util.List;

public class ShoppingOrder {

    private List<ProductDto> products;
    private ClientData clientData;
    private Courier courier;

    public ShoppingOrder(List<ProductDto> products, ClientData clientData, Courier courier) {
        this.products = products;
        this.clientData = clientData;
        this.courier = courier;
    }

    public ShoppingOrder() {
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "ShoppingOrder{" +
                "courier=" + courier +
                ", clientData=" + clientData +
                ", products=" + products +
                '}';
    }
}
