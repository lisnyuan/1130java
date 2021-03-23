package com.feizuo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feizuo.dao.OrderSettingDao;
import com.feizuo.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author li
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional(rollbackFor = Exception.class)
public class OederSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
          long count =  orderSettingDao.queryOrderCountByDate(orderSetting.getOrderDate());
            if (count > 0){
                orderSettingDao.updateOrderNumberByDate(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(date);
        System.out.println("orderSettings = " + orderSettings);
        List<Map> list = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map map = new HashMap(3);
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            list.add(map);
        }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting orderSettingQuery  = queryReservationsByDate(orderSetting.getOrderDate());
        if (orderSettingQuery == null){
            addByDate(orderSetting);
        }else if(orderSetting.getReservations() <= orderSettingQuery.getNumber()) {
            updateReservationsByDate(orderSetting);
        }else {
            throw new RuntimeException("预约人数超出范围！");
        }

    }

    private void updateReservationsByDate(OrderSetting orderSetting) {
        orderSettingDao.updateReservationsByDate(orderSetting);
    }

    private void addByDate(OrderSetting orderSetting) {
        orderSettingDao.addByDate(orderSetting);
    }

    private OrderSetting queryReservationsByDate(Date orderDate) {
        return orderSettingDao.queryReservationsByDate(orderDate);
    }
}
