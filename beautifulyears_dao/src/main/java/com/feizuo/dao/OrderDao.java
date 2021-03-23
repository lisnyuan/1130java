package com.feizuo.dao;

import com.feizuo.pojo.Order;

import java.util.Map;

public interface OrderDao {

    Order queryOrderByMemberMap(Map mapMem);

    void addOrder(Order newOrder);

    Map findById(Integer id);
}
