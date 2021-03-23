package com.feizuo.service;

import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.pojo.TravelGroup;

import java.util.List;

/**
 * @author li
 */
public interface TravelGroupService {

    List<TravelGroup> queryTravelGroupAll();

    void addTravelGroup(TravelGroup travelGroup, Integer[] travelItems);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelGroup selectById(Integer id);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    void edit(TravelGroup travelGroup, Integer[] travelItemIds);

    void deleteById(Integer id);
}
