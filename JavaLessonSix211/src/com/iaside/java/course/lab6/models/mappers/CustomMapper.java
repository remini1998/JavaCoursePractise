package com.iaside.java.course.lab6.models.mappers;

import com.iaside.java.course.lab6.models.Custom;

import java.util.Vector;

public interface CustomMapper {
    Vector<Custom> search(String name);
    Custom select(long id);
    Vector<Custom> selectAll();
    long insert(Custom custom);
    long update(Custom custom);
    boolean delete(Custom custom);
    boolean delete(long id);
}
