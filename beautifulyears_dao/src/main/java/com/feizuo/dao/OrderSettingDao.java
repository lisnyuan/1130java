package com.feizuo.dao;

import com.feizuo.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * @author li
 */
public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    void updateOrderNumberByDate(OrderSetting orderSetting);

    long queryOrderCountByDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(String date);

    OrderSetting queryReservationsByDate(Date orderDate);

    void addByDate(OrderSetting orderSetting);

    void updateReservationsByDate(OrderSetting orderSetting);

    OrderSetting queryOrderSettingByDate(Date orderDate);
}
