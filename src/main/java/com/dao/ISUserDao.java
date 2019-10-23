package com.dao;

import java.util.*;
import com.entity.*;
import org.springframework.web.bind.annotation.RequestParam;

public interface ISUserDao {

    List<SUser> selectAll(Map<String, Object> mp);

    SUser selectByPrimaryKey(Long id);

    SUser selectLogin(Map<String, Object> mp);

    Integer insert(SUser u);

    Integer selectCount(Map<String, Object> mp);

    Integer deleteByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(SUser u);

    SUser selectIfName( @RequestParam String username);

    SUser selectPassword(Map<String, Object> mp);

}
