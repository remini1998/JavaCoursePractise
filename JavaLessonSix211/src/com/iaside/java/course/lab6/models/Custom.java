package com.iaside.java.course.lab6.models;

import java.util.ArrayList;

public class Custom {
    private long id = 0;
    private String name = "会员";
    /**
     * 总花销
     */
    private float totalSpent = 0;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getTotalSpent() {
        return totalSpent;
    }

    public Custom(String name){
        this.name = name;
    }

    public Custom(long id, String name, double totalSpent){
        this.id = id;
        this.name = name;
        this.totalSpent = (float) totalSpent;
    }

    public Custom(long id, String name){
        this.id = id;
        this.name = name;
    }
}
