package com.github.mrgrtt.taohub.model;

public class HistoryListItem {
    private Product product;
    private long time;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Product getProduct() {
        return product;
    }

    public long getTime() {
        return time;
    }


}
