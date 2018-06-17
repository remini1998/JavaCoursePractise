package com.iaside.java.course.lab6.models.mappers;

import com.iaside.java.course.lab6.models.Trade;

import java.util.Vector;

public interface TradeMapper {
    Vector<Trade> search(String name);
    Trade select(long id);
    Vector<Trade> selectAll();
    long insert(Trade Trade);
    long update(Trade Trade);
    boolean delete(Trade Trade);
    boolean delete(long id);
}
