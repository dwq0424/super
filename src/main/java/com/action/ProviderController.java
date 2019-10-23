package com.action;


import com.entity.SUser;
import com.entity.Sprovider;
import com.service.ISproviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true",maxAge = 3600)
@PropertySource(value = "classpath:param.properties",encoding = "UTF-8")
public class ProviderController {
    @Autowired
    private ISproviderService ps;

    @RequestMapping(value = "/allProvider",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap  allProvider(@RequestParam HashMap<String,Object> mp,HttpSession session)throws Exception{
        List<Sprovider> list = ps.selectAll(mp);
        HashMap<String,Object> rm = new HashMap();
        rm.put("list",list);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        rm.put("name",name);
        return rm;
    }

    @RequestMapping(value = "/oneProvider/{id}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap oneProvider(@PathVariable(value = "id") Long id,HttpSession session)throws Exception{
        HashMap<String,Object> rm = new HashMap();
        Sprovider spr = ps.selectByPrimaryKey(id);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        rm.put("name",name);
        rm.put("spr",spr);
        return rm;
    }

    @RequestMapping(value = "/delProvider/{id}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public int delProvider(@PathVariable(value = "id") Long id)throws Exception{
        return ps.deleteByPrimaryKey(id);
    }

    @RequestMapping(value = "/updateProvider/{id}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public int updateProvider(@PathVariable(value = "id") Long id,Sprovider sprovider)throws Exception{
        Sprovider sprovider1 = ps.selectByPrimaryKey(id);
        sprovider1.setProcode(sprovider.getProcode());
        sprovider1.setProname(sprovider.getProname());
        sprovider1.setProcontact(sprovider.getProcontact());
        sprovider1.setProphone(sprovider.getProphone());
        sprovider1.setProaddress(sprovider.getProaddress());
        sprovider1.setProfax(sprovider.getProfax());
        sprovider1.setProdesc(sprovider.getProdesc());
        Integer num =ps.updateByPrimaryKeySelective(sprovider1);
        return num;
    }

    @RequestMapping(value = "/addProvider",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public int addProvider(Sprovider sprovider)throws Exception{
        return ps.insert(sprovider);
    }

    @RequestMapping(value = "/IfProviderName/{procode}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap IfProviderName(@PathVariable(value = "procode") String procode,HttpSession session)throws Exception{
         System.out.println("procode:"+procode);
         Sprovider sprovider  = ps.selectIfProviderName(procode);
         int num =1;
            if(sprovider==null){
               num=0;
            }
        HashMap<String,Object> mp = new HashMap();
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        mp.put("name",name);
        mp.put("num",num);
        return mp;
    }

    //分页
    @RequestMapping(value = "/providerPage",produces  = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap providerPage(@RequestParam HashMap<String,Object> mp, HttpSession session)throws Exception{
        System.out.println("供应商主页进来：");
        System.out.println("拿到的pageNo是："+mp.get("pageNo"));
        HashMap<String,Object> rm = jixi(mp);
        System.out.println("pageSize1:"+rm.get("pageSize"));
        HashMap<String,Object> pm = ps.selectPage(rm);
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
                }catch (Exception e){
                    pno=1;
                }
                rm.put("pageNo",pno);
            }else{
                rm.put("pageNo",1);
            }

            if(pm.containsKey("procode")){
                String procode =  pm.get("procode").toString();
                System.out.println("查询的procode："+procode);
                if(!procode.trim().equals("")){
                    rm.put("procode","%"+procode+"%");
                }
            }
            if(pm.containsKey("proname")){
                String proname =  pm.get("proname").toString();
                System.out.println("查询的proname："+proname);
                if(!proname.trim().equals("")){
                    rm.put("proname",proname);
                }
            }
        }else{
            rm.put("pageNo",1);
        }
        return rm;
    }
}
