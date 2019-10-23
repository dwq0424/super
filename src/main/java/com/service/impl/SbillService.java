package com.service.impl;

import com.dao.ISbillDao;
import com.entity.Sbill;
import com.service.ISbillService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@MapperScan(value = "com.dao")
@Service("sbillService")

public class SbillService implements ISbillService{

    @Autowired
    private ISbillDao dao;

    @Override
    public Integer selectCount(Map<String, Object> mp) {
        return dao.selectCount(mp);
    }

    @Override
    public List<Sbill> selectAll(Map<String, Object> mp) {
        return dao.selectAll(mp);
    }

    //分页
    public HashMap selectPage(Map<String, Object> mp) {
        HashMap<String,Object> rm = new HashMap();
        Integer pageNo = (Integer) mp.get("pageNo");
        Integer pageSize = (Integer) mp.get("pageSize");
        Integer pageNum = 1;
        Integer pageCount = 1;
        Integer count = dao.selectCount(mp);
        List<Sbill> list= new ArrayList();
        if(count>0){
            if(count % pageSize ==0){
                pageCount = count/pageSize ;
            }else{
                pageCount = count/pageSize +1;
            }
            if(pageNo<0){
                pageNum = 1;
            }else if(pageNo>pageCount){
                pageNum = pageCount;
            }else{
                pageNum=pageNo;
            }
            Integer pageStart = (pageNum-1)*pageSize;
            mp.put("pageStart",pageStart);
            list = dao.selectAll(mp);
        }
        rm.put("list",list);
        rm.put("count",count);
        rm.put("pageCount",pageCount);
        rm.put("pageNum",pageNum);
        return rm;
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(Sbill sbill) {
        return dao.insert(sbill);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Sbill sbill) {
        return dao.updateByPrimaryKeySelective(sbill);
    }

    @Override
    public Sbill selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public Sbill selectIfBillName(String billcode) {
        return dao.selectIfBillName(billcode);
    }


}
