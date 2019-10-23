package com.service.impl;

import java.util.*;
import com.entity.*;
import com.dao.*;
import com.service.ISUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@MapperScan(basePackages = "com.dao")
@Service(value = "suserService")
public class SUserService implements ISUserService{

    @Autowired
    private ISUserDao dao ;

    public List<SUser> selectAll(Map<String,Object> mp) {
        return dao.selectAll(mp);
    }

    @Override
    public SUser selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public SUser selectLogin(Map<String, Object> mp) {
        return dao.selectLogin(mp);
    }

    @Override
    public Integer insert(SUser u) {
        return dao.insert(u);
    }

    @Override
    public Integer selectCount(Map<String, Object> mp) {
        return dao.selectCount(mp);
    }

      //  分页
    public HashMap selectPage(Map<String, Object> mp) {
        HashMap<String,Object> pm = new HashMap();
        int pageNum = 1;
        int pageCount = 1;
        int pageNo = Integer.parseInt(mp.get("pageNo").toString());
        int pageSize = Integer.parseInt(mp.get("pageSize").toString());
        int count = dao.selectCount(mp);
        List<SUser> list = new ArrayList();
       if(count>0){
           if(count % pageSize ==0){
               pageCount = count / pageSize;
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
    public Integer  updateByPrimaryKeySelective(SUser u) {
        return dao.updateByPrimaryKeySelective(u);
    }

    @Override
    public SUser selectIfName(String username) {
        return dao.selectIfName(username);
    }

    @Override
    public SUser selectPassword(Map<String, Object> mp) {
        return dao.selectPassword(mp);
    }
}
