package org.example.domain;

public class Product extends Entity {
    private String name;
    private float price;
    private float quantity;

    public Product(int idEntity, String name, float price, float quantity) {
        super(idEntity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getIdEntity() +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", cantity=" + quantity +
                '}';
    }
}
