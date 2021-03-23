package com.feizuo.service;

import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.pojo.TravelItem;

/**
 * @author li
 */
public interface TravelItemService {


    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    TravelItem queryById(Integer id);

    void editById(TravelItem travelItem);
}
