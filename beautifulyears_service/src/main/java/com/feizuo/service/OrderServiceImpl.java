package com.feizuo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feizuo.constant.MessageConstant;
import com.feizuo.dao.MemberDao;
import com.feizuo.dao.OrderDao;
import com.feizuo.dao.OrderSettingDao;
import com.feizuo.entity.Result;
import com.feizuo.pojo.Member;
import com.feizuo.pojo.Order;
import com.feizuo.pojo.OrderSetting;
import com.feizuo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public Result addOrder(Map map) throws Exception {
        Date orderDate = DateUtils.parseString2Date((String) map.get("orderDate"));
        OrderSetting orderSetting = orderSettingDao.queryOrderSettingByDate(orderDate);
        //1 确定是否有套餐
        System.out.println("map.get(\"SetmealId\") = " + map.get("SetmealId"));
        if (orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER,orderDate);
        }else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            //2 确定套餐是否已满
            if (reservations >= number){
                return new Result(false, MessageConstant.ORDER_FULL,orderDate);
            }else {
                String telephone = (String) map.get("telephone");
                //3 确定是否是会员
               Member member = memberDao.queryMemberByTelephone(telephone);
                if (member == null) {
                   member = new Member();
                    member.setPhoneNumber(telephone);
                    member.setName((String) map.get("name"));
                    member.setSex((String) map.get("sex"));
                    member.setIdCard((String) map.get("idCard"));
                    member.setRegTime(new Date());
                   memberDao.addMember(member);
                }else {
                    //4 确定是否重复
                    Map mapMem = new HashMap();
                    mapMem.put("member_id",member.getId());
                    mapMem.put("setmeal_id",map.get("setmealId"));
                    mapMem.put("orderDate",orderDate);
                    Order order = orderDao.queryOrderByMemberMap(mapMem);
                   if (order != null){
                       return new Result(false,MessageConstant.HAS_ORDERED);
                   }
                }
                //5 增加预约数
                orderSetting.setReservations(reservations + 1);
                orderSettingDao.updateReservationsByDate(orderSetting);
                //6 预约
                Order newOrder = new Order(member.getId(),orderDate,(String)map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String) map.get("setmealId")));
                orderDao.addOrder(newOrder);
                return new Result(true,MessageConstant.ORDER_SUCCESS,newOrder);
            }
        }

    }

    @Override
    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}
