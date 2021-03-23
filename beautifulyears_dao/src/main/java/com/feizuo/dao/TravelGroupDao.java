package com.feizuo.dao;

import com.feizuo.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author li
 */
@Repository
public interface TravelGroupDao {

    List<TravelGroup> queryTravelGroupAll();

    void addTravelGroup(TravelGroup travelGroup);

    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup selectById(Integer id);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    void edit(TravelGroup travelGroup);

    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    void deleteById(Integer id);

    long queryt_travelgroup_travelitemTravelItemByTravelGroupId(Integer id);

    List<TravelGroup> queryTravelGroupBySetmealId(Integer id);
}
