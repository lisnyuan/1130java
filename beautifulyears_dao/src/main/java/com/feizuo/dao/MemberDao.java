package com.feizuo.dao;

import com.feizuo.pojo.Member;
import org.springframework.stereotype.Repository;

/**
 * @author li
 */
@Repository
public interface MemberDao {

    Member queryMemberByTelephone(String telephone);

    void addMember(Member newMember);
}
