package com.action;


import com.entity.SUser;
import com.entity.Sbill;
import com.service.ISbillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource(value = "classpath:param.properties",encoding = "UTF-8")
@CrossOrigin(allowCredentials = "true",maxAge = 3600)
@RestController
public class SbillController {

    @Autowired
    private ISbillService ss;

       //查询所有
    @RequestMapping(value = "allSbill",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Sbill> allSbill(@RequestParam HashMap<String,Object> mp){
        return ss.selectAll(mp);
    }

    //查询单个
    @RequestMapping(value = "/oneSbill/{id}",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap oneSbill(@PathVariable(value = "id") Long id,HttpSession session)throws Exception{
        Sbill sbill = ss.selectByPrimaryKey(id);
        HashMap<String,Object> rm = new HashMap();
        rm.put("sbill",sbill);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        rm.put("name",name);
        return rm;
    }

    //判断
    @RequestMapping(value = "/IfBillName/{billcode}",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public boolean IfBillName(@PathVariable(value = "billcode") String billcode)throws Exception{
        Sbill sbill = ss.selectIfBillName(billcode);
        if(sbill==null){
            return false;
        }
        return true;
    }

    //删除
    @RequestMapping(value = "/delSbill/{id}",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Integer delSbill(@PathVariable(value = "id") Long id)throws Exception{
        int num = ss.deleteByPrimaryKey(id);
        return num;
    }

    //修改
    @RequestMapping(value = "/updateSbill/{id}",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Integer updateSbill(@PathVariable(value = "id") Long id,Sbill sbill)throws Exception{
            Sbill sbill1 = ss.selectByPrimaryKey(id);
            sbill1.setBillcode(sbill.getBillcode());
            sbill1.setProductname(sbill.getProductname());
            sbill1.setProductunit(sbill.getProductunit());
            sbill1.setProductcount(sbill.getProductcount());
            sbill1.setTotalprice(sbill.getTotalprice());
            sbill1.setProviderid(sbill.getProviderid());
            Integer num = ss.updateByPrimaryKeySelective(sbill1);
        return num;
    }

    //增加
    @RequestMapping(value = "/addSbill",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Integer addSbill(Sbill sbill)throws Exception{
        sbill.setCreationdate(new Date());
        int num = ss.insert(sbill);
        return num;
    }


    //分页
    @RequestMapping(value = "/sbillPage",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap sbillPage(@RequestParam HashMap<String,Object> mp, HttpSession session)throws Exception{
        System.out.println("订单主页进来：");
        System.out.println("拿到的pageNo是："+mp.get("pageNo"));
        HashMap<String,Object> rm = jixi(mp);
        System.out.println("pageSize1:"+rm.get("pageSize"));
        HashMap<String,Object> pm = ss.selectPage(rm);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        pm.put("name",name);
        return pm;
    }

    //解析数据
    private HashMap<String,Object> jixi(Map<String, Object> pm){
        HashMap<String,Object>  rm = new HashMap();
        rm.put("pageSize",4);
        if(pm!=null){
            if(pm.containsKey("pageNo")){
                int pno;
                try {
                    pno = Integer.parseInt(pm.get("pageNo").toString());
                    System.out.println("pno1:"+pm.get("pageNo"));
                }catch (Exception e){
                    pno=1;
                }
                System.out.println("查询的pno2："+pno);
                rm.put("pageNo",pno);
            }else{
                rm.put("pageNo",1);
            }

            if(pm.containsKey("productname")){
                String productname =  pm.get("productname").toString();
                System.out.println("查询的productname："+productname);
                if(!productname.trim().equals("")){
                    rm.put("productname","%"+productname+"%");
                }
            }
            if(pm.containsKey("ispayment")){
                String ispayment =  pm.get("ispayment").toString();
                System.out.println("查询的ispayment："+ispayment);
                if(!ispayment.trim().equals("")){
                    rm.put("ispayment",ispayment);
                }
            }
            if(pm.containsKey("providerid")){
                String providerid =  pm.get("providerid").toString();
                System.out.println("查询的providerid："+providerid);
                if(!providerid.trim().equals("")){
                    rm.put("providerid",providerid);
                }
            }

        }else{
            rm.put("pageNo",1);
        }
        return rm;
    }

}
