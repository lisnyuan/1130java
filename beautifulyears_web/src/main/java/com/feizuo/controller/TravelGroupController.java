package com.feizuo.controller;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.feizuo.constant.MessageConstant;
import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.entity.Result;
import com.feizuo.pojo.TravelGroup;
import com.feizuo.service.TravelGroupService;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author li
 */
@RestController
@RequestMapping(value = "/travelGroup")
public class TravelGroupController {

    @Reference
    TravelGroupService travelGroupService;

    @RequestMapping(value = "/queryTravelGroupAll.do")
    public Result queryTravelGroupAll() {
        List<TravelGroup> travelGroupList = null;
        try {
            travelGroupList = travelGroupService.queryTravelGroupAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL, travelGroupList);

        }
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelGroupList);
    }

    @RequestMapping(value = "/addTravelGroup.do")
    public Result addTravelGroup(@RequestBody TravelGroup travelGroup,Integer[] travelItemIds){

        travelGroupService.addTravelGroup(travelGroup,travelItemIds);
        return new Result(true,MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping(value = "/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return travelGroupService.findPage(queryPageBean);
    }

    @RequestMapping(value = "/selectById.do")
    public Result selectById(Integer id){
        TravelGroup travelGroup = travelGroupService.selectById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    @RequestMapping(value = "/findTravelItemIdByTravelGroupId.do")
    public Result findTravelItemIdByTravelGroupId(Integer id){
        List<Integer> travelItemIds = null;
        try {
            travelItemIds = travelGroupService.findTravelItemIdByTravelGroupId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL,travelItemIds);
        }
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItemIds);
    }

    @RequestMapping(value = "/edit.do")
    public Result edit(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        travelGroupService.edit(travelGroup,travelItemIds);
        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping(value = "/deleteById")
    public Result deleteById(Integer id){
        try {
            travelGroupService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
    }



}
