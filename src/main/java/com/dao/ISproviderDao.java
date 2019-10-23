package com.dao;

import com.entity.Sprovider;

import java.util.List;
import java.util.Map;

public interface ISproviderDao {

    Integer selectCount(Map<String, Object> mp);

    List<Sprovider> selectAll(Map<String, Object> mp);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Sprovider sprovider);

    Integer updateByPrimaryKeySelective(Sprovider sprovider);

    Sprovider selectByPrimaryKey(Long id);

    Sprovider selectIfProviderName(String procode);

}
