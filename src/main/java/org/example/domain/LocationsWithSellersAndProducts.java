package org.example.domain;

import java.util.List;

public class LocationsWithSellersAndProducts {
    private int locationId;
    private String sellerName;
    private List<String> productNames;

    public LocationsWithSellersAndProducts(int locationId, String sellerName, List<String> productNames) {
        this.locationId = locationId;
        this.sellerName = sellerName;
        this.productNames = productNames;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    @Override
    public String toString() {
        return "LocationsWithSellersAndProducts{" +
                "locationId=" + locationId +
                ", sellerName='" + sellerName + '\'' +
                ", productNames=" + productNames +
                '}';
    }
}
