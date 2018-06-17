package com.iaside.java.course.lab6.models;

import java.util.Optional;

public class Item {
    private long id = 0;
    private String name = "物品";
    private float price = 0;
    /**
     * 卖出的总数
     */
    private float totalSold;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getTotalSold() {
        return totalSold;
    }

    public Item(String name, float price){
        this.name = name;
        this.price = price;
    }
    public Item(long id, String name, float price, double totalSold){
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalSold = (float) totalSold;
    }
    public Item(long id, String name, float price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
