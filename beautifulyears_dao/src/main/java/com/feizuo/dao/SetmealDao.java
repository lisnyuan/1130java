package com.feizuo.dao;

import com.feizuo.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetmealDao {

    void addSetmeal(Setmeal setmeal);

    void addt_setmeal_travelgroupFromAddSetmeal(Integer id, Integer[] travelgroupIds);

    Page findPage(String queryString);

    List<Setmeal> queryAllSetmeal();

    Setmeal findSetmealById(Integer id);
}
