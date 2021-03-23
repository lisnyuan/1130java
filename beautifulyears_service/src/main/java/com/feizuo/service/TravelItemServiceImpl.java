package com.feizuo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feizuo.dao.TravelItemDao;
import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.pojo.TravelItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author li
 */
@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
    @Autowired
    TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page = travelItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
       long count = travelItemDao.queryt_travelgroup_travelitemTravelItemById(id);
       if (count > 0){
           throw new RuntimeException("暂时无法删除项目");
       }
        travelItemDao.deleteById(id);
    }

    @Override
    public TravelItem queryById(Integer id) {
        TravelItem travelItem = travelItemDao.queryById(id);
        return travelItem;
    }

    @Override
    public void editById(TravelItem travelItem) {
        travelItemDao.editById(travelItem);
    }
}
