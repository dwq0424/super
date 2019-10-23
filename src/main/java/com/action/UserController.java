package com.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import com.entity.*;
import com.service.ISUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@PropertySource(value = "classpath:param.properties",encoding = "UTF-8")
@CrossOrigin(allowCredentials = "true",maxAge = 3600)
@RestController
public class UserController {

    @Value("${filePath}")
    private String filePath;
    @Value("${webPath}")
    private String webPath;

    @Autowired
    private ISUserService us ;


    //查询单个
    @RequestMapping(value = "/oneuser/{id}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap oneuser(@PathVariable(value = "id") Long id, HttpSession session) throws Exception{
        SUser u = us.selectByPrimaryKey(id);
        HashMap<String,Object> mp = new HashMap();
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        mp.put("name",name);
        if(u == null){
            u = new SUser();
        }
        mp.put("u",u);
        return mp ;
    }

    //修改
    @RequestMapping(value = "/update/{id}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap update(@PathVariable(value = "id") Long id,SUser u,HttpSession session)throws Exception{
        HashMap<String,Object> mp = new HashMap();
        System.out.println("------SUser0"+u);
        SUser u1 = us.selectByPrimaryKey(id);
        u1.setUsername(u.getUsername());
        u1.setGender(u.getGender());
        u1.setBirthday(u.getBirthday());
        u1.setPhone(u.getPhone());
        u1.setAddress(u.getAddress());
        u1.setUserrole(u.getUserrole());
        Integer num = us.updateByPrimaryKeySelective(u1);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        mp.put("name",name);
        mp.put("num",num);
        return mp;
    }

    //判断姓名是否重复
    @RequestMapping(value = "/ifName/{username}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public boolean ifName(@PathVariable(value = "username")String username)throws Exception{
        System.out.println("姓名："+username);
        SUser u = us.selectIfName(username);
        System.out.println("用户对象："+u );
        if(u==null){
            return false;
        }
        return true;
    }

    //新增
    @RequestMapping(value = "/adduser",method = RequestMethod.POST)
    public HashMap adduser( SUser u,HttpSession session) throws Exception{
        HashMap<String,Object> mp = new HashMap();
        Integer num = us.insert(u);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        mp.put("name",name);
        mp.put("num",num);
        return mp ;
    }
    //删除
    @RequestMapping(value = "/delUser/{id}")
    public boolean delUser(@PathVariable(value = "id") Long id)throws Exception{
            int num = us.deleteByPrimaryKey(id);
            if(num>0){
                return true;
            }
        return false;
    }

   //分页&&查询
    @RequestMapping(value = "/userPage" ,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HashMap  userPage(@RequestParam Map<String,Object> mp,HttpSession session) throws Exception{
        System.out.println("user主页面进来");
        System.out.println("拿到的pageNo是："+mp.get("pageNo"));
        HashMap<String,Object> rm = priseMap(mp);
        HashMap<String,Object> pm = us.selectPage(rm);
        SUser user =  (SUser) session.getAttribute("userinfo");
        String name = user.getUsername();
        pm.put("name",name);
        return pm;
    }

        //解析数据
    private HashMap<String,Object> priseMap(Map<String, Object> pm) {
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
                System.out.println("查询的pno："+pno);
                rm.put("pageNo",pno);
            }else{
                rm.put("pageNo",1);
            }

            if(pm.containsKey("username")){
                String username = pm.get("username").toString();
                System.out.println("查询的name："+username);
                if(!username.trim().equals("")){
                    rm.put("username","%"+username+"%");
                }
            }
            if(pm.containsKey("userrole")){
                String userrole = pm.get("userrole").toString();
                System.out.println("查询的userrole："+userrole);
                if(!userrole.trim().equals("")){
                    rm.put("userrole",userrole);
                }
            }
        }else{
            rm.put("pageNo",1);
        }
        return rm;
    }


    //上传文件
    @RequestMapping(value = "/uploadfile",method = RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Boolean uploadfile(@RequestParam(value = "files",required =false) MultipartFile[] files,SUser u){
        boolean ck = false;
        System.out.println("----filePath= "+filePath);
        System.out.println("----webPath= "+webPath);

        List<String> flist = new ArrayList();
        //上传文件
        boolean ckup = true;
        try {
            if(files!=null && files.length>0){
                for (MultipartFile mf:files){
                    String fname = mf.getOriginalFilename();
                    int index = fname.lastIndexOf(".");
                    if(index>0){
                        fname = fname.substring(index);
                        UUID uuid = UUID.randomUUID();
                        fname = uuid.toString().replace("-","")+fname;
                        String webName = webPath+fname;
                        flist.add(webName);
                        File ff = new File(filePath,fname);
                        FileUtils.copyInputStreamToFile(mf.getInputStream(),ff);
                    }
                    //linux给权限
                    Runtime runtime = Runtime.getRuntime();
                    String command = "chmod 755 -R " + filePath;
                    Process process = runtime.exec(command);
                    process.waitFor();
                    int existValue = process.exitValue();
                }
            }
        }catch (Exception e){
            ckup = false;
            e.printStackTrace();
        }
        System.out.println("-----------flist:"+flist);
        if(ckup){
            try {
                Integer num = us.insert(u);
                System.out.println("num=:"+num);
                if(num>0){
                    ck=true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ck;
    }
}
