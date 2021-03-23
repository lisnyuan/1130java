package com.feizuo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.feizuo.constant.MessageConstant;
import com.feizuo.constant.RedisMessageConstant;
import com.feizuo.entity.Result;
import com.feizuo.pojo.Member;
import com.feizuo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author li
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping(value = "/check.do")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String redisKey = RedisMessageConstant.SENDTYPE_ORDER + telephone;
        String code = jedisPool.getResource().get(redisKey);
        String cliCode = (String) map.get("validateCode");
        if (code == null || !code.equals(cliCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            Member member = memberService.queryMemberByTelepnone(telephone);
            if (member == null) {
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.addNewMember(member);
            }
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }
}

