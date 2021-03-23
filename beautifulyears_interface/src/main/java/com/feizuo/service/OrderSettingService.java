package com.feizuo.service;

import com.feizuo.pojo.OrderSetting;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author li
 */
@Repository
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
