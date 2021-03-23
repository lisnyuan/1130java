package com.feizuo.controller;

import com.feizuo.constant.MessageConstant;
import com.feizuo.constant.RedisMessageConstant;
import com.feizuo.entity.Result;
import com.feizuo.utils.DXSMSUtils;
import com.feizuo.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author li
 */
@RestController
@RequestMapping(value = "/validateCode")
public class ValidateCodeController {
@Autowired
private JedisPool jedisPool;

    @RequestMapping(value = "/send4Order.do")
    public Result send4Order(String telephone){
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        try {
            DXSMSUtils.sendShortMessage(telephone,code);
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+telephone,10*60,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
