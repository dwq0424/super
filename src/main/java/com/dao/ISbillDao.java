package com.dao;

import com.entity.Sbill;

import java.util.*;

public interface ISbillDao {

   Integer selectCount(Map<String,Object> mp);

   List<Sbill> selectAll(Map<String,Object> mp);

   Integer deleteByPrimaryKey(Long id);

   Integer insert(Sbill sbill);

   Integer updateByPrimaryKeySelective(Sbill sbill);

   Sbill selectByPrimaryKey(Long id);

   Sbill selectIfBillName(String billcode);
}
