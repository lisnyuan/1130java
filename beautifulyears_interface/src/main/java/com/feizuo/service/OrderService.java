package com.feizuo.service;

import com.feizuo.entity.Result;
import com.feizuo.pojo.Order;

import java.util.Map;

/**
 * @author li
 */
public interface OrderService {

    Result addOrder(Map map) throws Exception;

    Map findById(Integer id);
}
