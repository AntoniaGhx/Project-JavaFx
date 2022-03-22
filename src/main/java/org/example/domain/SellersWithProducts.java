package org.example.domain;

import java.util.List;

public class SellersWithProducts extends Entity {
    private String sellerName;
    private List<String> productNames;

    public SellersWithProducts(int idEntity, String sellerName, List<String> productNames) {
        super(idEntity);
        this.sellerName = sellerName;
        this.productNames = productNames;
    }

    public String getSellerName() {
        return sellerName;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    @Override
    public String toString() {
        return "SellersWithProducts{" +
                "sellerName='" + sellerName + '\'' +
                ", productNames=" + productNames +
                '}';
    }
}
