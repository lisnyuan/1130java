package com.feizuo.dao;

import com.feizuo.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author li
 */
@Repository
public interface TravelItemDao {

    int add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    long queryt_travelgroup_travelitemTravelItemById(Integer id);

    int deleteById(Integer id);

    TravelItem queryById(Integer id);

    int editById(TravelItem travelItem);

    List<TravelItem> queryTravelItemByTravelGroupId(Integer id);
}
