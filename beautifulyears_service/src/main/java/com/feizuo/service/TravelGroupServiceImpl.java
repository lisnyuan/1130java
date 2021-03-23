package com.feizuo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feizuo.dao.TravelGroupDao;
import com.feizuo.dao.TravelItemDao;
import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li
 */
@Service(interfaceClass = TravelGroupService.class)
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupDao travelGroupDao;


    @Override
    public List<TravelGroup> queryTravelGroupAll() {
        return travelGroupDao.queryTravelGroupAll();
    }

    @Override
    public void addTravelGroup(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.addTravelGroup(travelGroup);
        setTravelGroupAndTravelItem(travelGroup,travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelGroup> page = travelGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup selectById(Integer id) {
        return travelGroupDao.selectById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelGroupId(id);
    }

    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.edit(travelGroup);
        deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        setTravelGroupAndTravelItem(travelGroup,travelItemIds);
    }

    @Override
    public void deleteById(Integer id) {
        long count = travelGroupDao.queryt_travelgroup_travelitemTravelItemByTravelGroupId(id);
        System.out.println(count);
        if (count > 0){
            throw new RuntimeException("无法删除");
        }
        travelGroupDao.deleteById(id);
    }


    public void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id){
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(id);
    }


    private void setTravelGroupAndTravelItem(TravelGroup travelGroup, Integer[] travelItemIds){
        if (travelItemIds != null && travelItemIds.length != 0){
            for (Integer travelItemId : travelItemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("travelGroup",travelGroup.getId());
                map.put("travelItem",travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }
}
