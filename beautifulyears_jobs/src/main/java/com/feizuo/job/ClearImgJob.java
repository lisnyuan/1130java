package com.feizuo.job;

import com.feizuo.constant.RedisConstant;
import com.feizuo.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * @author li
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        Set<String> set = jedisPool.getResource().sdiff(
                RedisConstant.SETMEAL_PIC_QINIU_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String img = iterator.next();
            System.out.println("删除的图片名称为："+ img );
            QiniuUtils.deleteFileFromQiniu(img);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
        }
    }
}
