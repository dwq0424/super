package com.service.impl;

import com.dao.ISproviderDao;
import com.entity.Sprovider;
import com.service.ISproviderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional
@Service(value = "sproviderService")
@MapperScan(basePackages = "com.dao")
public class SproviderService implements ISproviderService{
    @Autowired
    private ISproviderDao dao;

    @Override
    public Integer selectCount(Map<String, Object> mp) {
        return dao.selectCount(mp);
    }

    @Override
    public List<Sprovider> selectAll(Map<String, Object> mp) {
        return dao.selectAll(mp);
    }

   //分页
    public HashMap selectPage(Map<String, Object> mp) {
        HashMap<String,Object> pm = new HashMap();
        int pageNum = 1;
        int pageCount = 1;
        int pageNo = Integer.parseInt(mp.get("pageNo").toString());
        int pageSize = Integer.parseInt(mp.get("pageSize").toString());
        int count = dao.selectCount(mp);
        System.out.println("总数："+count);
        List<Sprovider> list = new ArrayList();
        if(count>0){
            if(count % pageSize == 0){
                pageCount = count/pageSize;
            }else{
                pageCount = count / pageSize +1;
            }
            if(pageNo<1){
                pageNum=1;
            }else if(pageNo>pageCount){
                pageNum=pageCount;
            }else{
                pageNum=pageNo;
            }
            int pageStart = (pageNum-1)*pageSize;
            mp.put("pageStart",pageStart);
            list = dao.selectAll(mp);
            System.out.println("list"+list.size());
        }
        pm.put("list",list);
        pm.put("pageCount",pageCount);
        pm.put("pageNum",pageNum);
        pm.put("count",count);
        return pm;
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(Sprovider sprovider) {
        return dao.insert( sprovider);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Sprovider sprovider) {
        return dao.updateByPrimaryKeySelective(sprovider);
    }

    @Override
    public Sprovider selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public Sprovider selectIfProviderName(String procode) {
        return dao.selectIfProviderName(procode);
    }
}
