package com.feizuo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feizuo.dao.MemberDao;
import com.feizuo.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author li
 */
@Service(interfaceClass = MemberService.class)
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member queryMemberByTelepnone(String telephone) {
        return memberDao.queryMemberByTelephone(telephone);
    }

    @Override
    public void addNewMember(Member member) {
        memberDao.addMember(member);
    }
}
