package com.feizuo.service;


import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.pojo.Setmeal;

import java.util.List;

public interface SetmealService  {

    void addSetmeal(Setmeal setmeal, Integer... travelgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> queryAllSetmeal();

    Setmeal findSetmealById(Integer id);
}
