package com.github.mrgrtt.taohub.model;

public class CartListItem {
    private Product product;
    private int count;
    private long price;
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public Product getProduct() {
        return product;
    }

    public long getId() {
        return id;
    }
}
