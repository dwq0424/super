package com.service;

import com.entity.Sbill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISbillService {
    Integer selectCount(Map<String,Object> mp);

    List<Sbill> selectAll(Map<String,Object> mp);

    HashMap selectPage(Map<String,Object> mp);


    Integer deleteByPrimaryKey(Long id);

    Integer insert(Sbill sbill);

    Integer updateByPrimaryKeySelective(Sbill sbill);

    Sbill selectByPrimaryKey(Long id);

    Sbill selectIfBillName(String billcode);
}
