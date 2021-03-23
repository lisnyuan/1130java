package com.feizuo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.feizuo.constant.MessageConstant;
import com.feizuo.entity.Result;
import com.feizuo.pojo.OrderSetting;
import com.feizuo.service.OrderSettingService;
import com.feizuo.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author li
 */
@RestController
@RequestMapping(value = "/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping(value = "/upload.do")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();
            for (String[] string : strings) {
                OrderSetting orderSetting = new OrderSetting(
                        new Date(string[0]),Integer.parseInt(string[1]));
                orderSettings.add(orderSetting);
            }
            orderSettingService.add(orderSettings);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.UPLOAD_SUCCESS);
    }

    @RequestMapping(value = "/getOrderSettingByMonth.do")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            System.out.println(list);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/editNumberByDate.do")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
