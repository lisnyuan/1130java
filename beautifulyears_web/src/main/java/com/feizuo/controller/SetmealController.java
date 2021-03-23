package com.feizuo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.feizuo.constant.MessageConstant;
import com.feizuo.constant.RedisConstant;
import com.feizuo.entity.PageResult;
import com.feizuo.entity.QueryPageBean;
import com.feizuo.utils.QiniuUtils;
import com.feizuo.entity.Result;
import com.feizuo.pojo.Setmeal;
import com.feizuo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @author li
 */
@RestController
@RequestMapping(value = "/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/upload.do")
    public Result upload(MultipartFile imgFile) {
        try {
            //1 获取照片原始名字名字
            String originalFilename = imgFile.getOriginalFilename();
            //2 获取文件名和文件后缀
            //2.1 获取"."最后一次出现的索引.0开始数
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //2.2 截取字符串后半部分,从指定的位置开始,包括起始位置
            String substring = originalFilename.substring(lastIndexOf);
            //3 为照片创建新文件名,防止重名
            String newFileName = UUID.randomUUID().toString() + substring;
            //4 使用七牛工具类上传照片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), newFileName);
            // 5 将图片的名称保存在redis数据库,代表已存入七牛云
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_QINIU_RESOURCES,newFileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
        }
    }

    @RequestMapping(value = "/addSetmeal.do")
    public Result addSetmeal(@RequestBody Setmeal setmeal,Integer[] travelgroupIds){
        try {
            setmealService.addSetmeal(setmeal,travelgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping(value = "/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }
}
