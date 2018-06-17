package com.iaside.java.course.lab6.models.mappers;

import com.iaside.java.course.lab6.models.Custom;
import com.iaside.java.course.lab6.models.Item;
import com.iaside.java.course.lab6.models.Trade;
import org.apache.ibatis.annotations.Select;

import java.util.Vector;

/**
 * 不计算附加信息的查询
 */
public interface GetAllMapper {
//    带参数写法
//    @Select("SELECT * FROM trade WHERE id = #{id}")
//    Trade selectTrades(int id);
    @Select("SELECT * FROM custom")
    Vector<Custom> selectCustoms();
    @Select("SELECT * FROM item")
    Vector<Item> selectItems();
}
