package com.feizuo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feizuo.constant.RedisConstant;
import com.feizuo.dao.SetmealDao;
import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.pojo.Setmeal;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li
 */
@Service(interfaceClass = SetmealService.class)
@Transactional(rollbackFor = Exception.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;


    @Override
    public void addSetmeal(Setmeal setmeal, Integer... travelgroupIds) {
        setmealDao.addSetmeal(setmeal);
        //将图片保存到redis中,此为数据库中实际存在的图片
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        if (travelgroupIds != null && travelgroupIds.length > 0){
            addt_setmeal_travelgroupFromAddSetmeal(setmeal.getId(),travelgroupIds);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> queryAllSetmeal() {
        return setmealDao.queryAllSetmeal();

    }

    @Override
    public Setmeal findSetmealById(Integer id) {
        return setmealDao.findSetmealById(id);
    }

    private void addt_setmeal_travelgroupFromAddSetmeal(Integer id,Integer...travelgroupIds){
        for (Integer travelgroupId : travelgroupIds) {
            Map<String,Integer> map = new HashMap<String,Integer>();
            map.put("setmeal_id",id);
            map.put("travelgroup_id",travelgroupId);
            setmealDao.addt_setmeal_travelgroupFromAddSetmeal(id,travelgroupIds);
        }


    }
}
