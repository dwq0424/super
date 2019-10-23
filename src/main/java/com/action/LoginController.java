package com.action;

import com.util.RondomNumUtil;
import com.util.SendSMSValidate;
import com.entity.SUser;
import com.service.ISUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(allowCredentials = "true",maxAge = 3600)
@RestController
public class LoginController {

    @Autowired
    private ISUserService us ;


    //登录
    @RequestMapping(value = "/login",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public SUser login(@RequestParam Map<String,Object> mp, HttpSession session) throws Exception {
        SUser user = us.selectLogin(mp);
        System.out.println("登陆进来用户："+user);
        if(user!=null){
            session.setAttribute("userinfo",user);
            return user;
        }else{
            SUser user1 = new SUser();
            return user1;
        }

    }

    //注销
    @RequestMapping(value = "/tuichu",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Integer tuichu( HttpSession session) throws Exception {
        session.removeAttribute("userinfo");
        SUser u = (SUser) session.getAttribute("userinfo");
        if(u!=null){
            return 1;
        }else{
            return 0;
       }18975822196
    }

    //确认旧密码
    @RequestMapping(value = "/updatePassword",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Integer updatePassword(@RequestParam(value = "userpassword") String userpassword, HttpSession session) throws Exception {
        HashMap<String,Object> mp = new HashMap();
        SUser u = (SUser) session.getAttribute("userinfo");
        System.out.println("uid是："+u.getId());
        System.out.println("userpassword是："+userpassword);
        mp.put("id",u.getId());
        mp.put("userpassword",userpassword);
        SUser u1 = us.selectPassword(mp);
        if(u1!=null){
            return 1;
        }
        return 0;
    }

    //修改密码
    @RequestMapping(value = "/updaterPassword",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public boolean updaterPassword(@RequestParam(value = "userpassword") String userpassword, HttpSession session) throws Exception {
        SUser u = (SUser) session.getAttribute("userinfo");
        System.out.println("userpassword是："+userpassword);
        u.setUserpassword(userpassword);
        int num = us.updateByPrimaryKeySelective(u);
        if(num>0){
            return true;
        }
        return false;
    }

    //短信验证
    /*
    AppID  		    （应用ID）
    Account Sid 	（用户sid）
    Auth Token	    （鉴权密钥）
    Rest URL 	    （请求地址）：https://open.ucpaas.com/ol/sms/{function}
    templateid       (模板名称)
    param            (验证码)
    mobile           (手机号码)
     */
    @RequestMapping(value = "/SMS",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap SMS(@RequestParam(value = "mobile")String mobile,HttpSession session)throws Exception{
        System.out.println("进入短信登陆验证。。。。。"+mobile);
        HashMap<String,Object> mp = new HashMap();
        String param = RondomNumUtil.createRandomVcode();
        session.setAttribute("param",param);
        if(mobile.equals("")||mobile==null){
            mp.put("mobile",mobile);
        }else{
            String pattern="^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$"; //验证手机的正则表达式
            boolean ck ;
            if(mobile.matches(pattern)){
                System.out.println("是电话号码");
                Boolean flag = SendSMSValidate.sendSms(mobile,param);
                if(flag){
                    mp.put("flag",flag);
                }else{
                    mp.put("flag",flag);
                }
                ck = true;
                mp.put("ck",ck);
            }else {
                System.out.println("不是电话号码");
                ck = false;
                mp.put("ck",ck);
            }
        }
        return mp;
    }


    @RequestMapping(value = "/inputCode",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public boolean inputCode(@RequestParam(value = "code")String code,HttpSession session){
        String code1 = (String) session.getAttribute("param");
        System.out.println("session中的验证码是："+code1);
        System.out.println("网页传进来的是："+code);
        if(code1.equals(code)){
            return true;
        }
        return false;
    }
}
