package com.feizuo.service;

import com.feizuo.pojo.Member;

/**
 * @author li
 */
public interface MemberService {
    Member queryMemberByTelepnone(String telephone);

    void addNewMember(Member member);
}
