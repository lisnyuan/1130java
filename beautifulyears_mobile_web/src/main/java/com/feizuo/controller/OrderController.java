package com.feizuo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.feizuo.constant.MessageConstant;
import com.feizuo.constant.RedisMessageConstant;
import com.feizuo.entity.Result;
import com.feizuo.pojo.Order;
import com.feizuo.service.OrderService;
import com.feizuo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

/**
 * @author li
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/addOrder")
    public Result addOrder(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String cliCode = (String) map.get("validateCode");
        String redisCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + telephone);
        if (cliCode == null || !cliCode.equals(redisCode)) {
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            return orderService.addOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/findById.do")
    public Map findById(Integer id) throws Exception {
        Map map = orderService.findById(id);
        if (map != null) {
            String orderDate = DateUtils.parseDate2String((Date) map.get("orderDate"));
            map.put("orderDate", orderDate);
        }
        return map;
    }
}
