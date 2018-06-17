package com.iaside.java.course.lab6.models.mappers;

import com.iaside.java.course.lab6.models.Item;

import java.util.Vector;

public interface ItemMapper {
    Vector<Item> search(String name);
    Item select(long id);
    Vector<Item> selectAll();
    long insert(Item Item);
    long update(Item Item);
    boolean delete(Item Item);
    boolean delete(long id);
}
