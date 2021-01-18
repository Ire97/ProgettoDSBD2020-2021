package com.baldacchino_gambadoro.orders_management.DataModel;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Order")
public class Order {

    private Integer idProduct;
    private Integer quantity;
    private double price;

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
