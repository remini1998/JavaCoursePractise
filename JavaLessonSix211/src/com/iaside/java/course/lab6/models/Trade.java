package com.iaside.java.course.lab6.models;

import java.util.ArrayList;

public class Trade {
    private long id = 0;
    private long buyerId = 0;
    private String buyerName = "";
    private long itemBoughtId = 0;
    private String itemBoughtName = "";
    private float price = 0;
    private float amount = 0;

    public long getId() {
        return id;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public long getItemBoughtId() {
        return itemBoughtId;
    }

    public String getItemBoughtName() {
        return itemBoughtName;
    }

    public float getPrice() {
        return price;
    }

    public float getAmount() {
        return amount;
    }


    public Trade(long id, long buyerId, long itemBoughtId, float amount){
        this.id = id;
        this.buyerId = buyerId;
        this.itemBoughtId = itemBoughtId;
        this.amount = amount;
    }

    public Trade(long buyerId, long itemBoughtId, float amount){
        this.buyerId = buyerId;
        this.itemBoughtId = itemBoughtId;
        this.amount = amount;
    }
    public Trade(long id, long buyerId, String buyerName, long itemBoughtId, String itemBoughtName, float price, float amount){
        this.id = id;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.itemBoughtId = itemBoughtId;
        this.itemBoughtName = itemBoughtName;
        this.price = price;
        this.amount = amount;
    }

    public float getTotalPrice() {
        return price * amount;
    }
}
