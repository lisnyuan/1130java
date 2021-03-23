package com.feizuo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.feizuo.constant.MessageConstant;
import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.entity.Result;
import com.feizuo.pojo.TravelItem;
import com.feizuo.service.TravelItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author li
 */
@RestController
@RequestMapping(value = "/travelItem")
public class TravelItemController {

    @Reference
    TravelItemService travelItemService;

    @RequestMapping(value = "/add.do")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @RequestMapping(value = "/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return travelItemService.findPage(queryPageBean);
    }

    @RequestMapping(value = "/deleteById.do")
    public Result deleteById(Integer id){
        try {
            travelItemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }

    @RequestMapping(value = "/queryById.do")
    public Result queryById(Integer id){
        TravelItem travelItem = null;
        try {
           travelItem = travelItemService.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
        }
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping(value = "/editById.do")
    public Result editById(@RequestBody TravelItem travelItem){
        try {
            System.out.println(travelItem);
            travelItemService.editById(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }
}
