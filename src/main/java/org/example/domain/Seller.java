package org.example.domain;

import java.util.List;

public class Seller extends Entity {
    private String name;
    private boolean vegetableSeller;
    private boolean fruitSeller;
    private int locationId;
    private List<Integer> productsIds;

    public Seller(int idEntity, String name, boolean vegetableSeller, boolean fruitSeller, int locationId, List<Integer> productsIds) {
        super(idEntity);
        this.name = name;
        this.vegetableSeller = vegetableSeller;
        this.fruitSeller = fruitSeller;
        this.locationId = locationId;
        this.productsIds =productsIds;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetableSeller() {
        return vegetableSeller;
    }

    public boolean isFruitSeller() {
        return fruitSeller;
    }

    public int getLocationId() {
        return locationId;
    }

    public List<Integer> getProductsIds() {
        return productsIds;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                ", vegetableSeller=" + vegetableSeller +
                ", fruitSeller=" + fruitSeller +
                ", locationId=" + locationId +
                '}';
    }
}
