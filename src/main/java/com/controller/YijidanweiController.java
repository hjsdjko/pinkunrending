
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 一级单位
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/yijidanwei")
public class YijidanweiController {
    private static final Logger logger = LoggerFactory.getLogger(YijidanweiController.class);

    @Autowired
    private YijidanweiService yijidanweiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service

    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private ErjidanweiService erjidanweiService;
    @Autowired
    private SanjidanweiService sanjidanweiService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("二级单位".equals(role))
            params.put("erjidanweiId",request.getSession().getAttribute("userId"));
        else if("三级单位".equals(role))
            params.put("sanjidanweiId",request.getSession().getAttribute("userId"));
        else if("一级单位".equals(role))
            params.put("yijidanweiId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = yijidanweiService.queryPage(params);

        //字典表数据转换
        List<YijidanweiView> list =(List<YijidanweiView>)page.getList();
        for(YijidanweiView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        YijidanweiEntity yijidanwei = yijidanweiService.selectById(id);
        if(yijidanwei !=null){
            //entity转view
            YijidanweiView view = new YijidanweiView();
            BeanUtils.copyProperties( yijidanwei , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody YijidanweiEntity yijidanwei, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,yijidanwei:{}",this.getClass().getName(),yijidanwei.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<YijidanweiEntity> queryWrapper = new EntityWrapper<YijidanweiEntity>()
            .eq("username", yijidanwei.getUsername())
            .or()
            .eq("yijidanwei_phone", yijidanwei.getYijidanweiPhone())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        YijidanweiEntity yijidanweiEntity = yijidanweiService.selectOne(queryWrapper);
        if(yijidanweiEntity==null){
            yijidanwei.setCreateTime(new Date());
            yijidanwei.setPassword("123456");
            yijidanweiService.insert(yijidanwei);
            return R.ok();
        }else {
            return R.error(511,"账户或者一级单位联系方式已经被使用");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody YijidanweiEntity yijidanwei, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,yijidanwei:{}",this.getClass().getName(),yijidanwei.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<YijidanweiEntity> queryWrapper = new EntityWrapper<YijidanweiEntity>()
            .notIn("id",yijidanwei.getId())
            .andNew()
            .eq("username", yijidanwei.getUsername())
            .or()
            .eq("yijidanwei_phone", yijidanwei.getYijidanweiPhone())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        YijidanweiEntity yijidanweiEntity = yijidanweiService.selectOne(queryWrapper);
        if(yijidanweiEntity==null){
            yijidanweiService.updateById(yijidanwei);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户或者一级单位联系方式已经被使用");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        yijidanweiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<YijidanweiEntity> yijidanweiList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("../../upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            YijidanweiEntity yijidanweiEntity = new YijidanweiEntity();
//                            yijidanweiEntity.setUsername(data.get(0));                    //账户 要改的
//                            //yijidanweiEntity.setPassword("123456");//密码
//                            yijidanweiEntity.setYijidanweiName(data.get(0));                    //一级单位名称 要改的
//                            yijidanweiEntity.setYijidanweiPhone(data.get(0));                    //一级单位联系方式 要改的
//                            yijidanweiEntity.setYijidanweiEmail(data.get(0));                    //一级单位邮箱 要改的
//                            yijidanweiEntity.setYijidanweiContent("");//详情和图片
//                            yijidanweiEntity.setCreateTime(date);//时间
                            yijidanweiList.add(yijidanweiEntity);


                            //把要查询是否重复的字段放入map中
                                //账户
                                if(seachFields.containsKey("username")){
                                    List<String> username = seachFields.get("username");
                                    username.add(data.get(0));//要改的
                                }else{
                                    List<String> username = new ArrayList<>();
                                    username.add(data.get(0));//要改的
                                    seachFields.put("username",username);
                                }
                                //一级单位联系方式
                                if(seachFields.containsKey("yijidanweiPhone")){
                                    List<String> yijidanweiPhone = seachFields.get("yijidanweiPhone");
                                    yijidanweiPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> yijidanweiPhone = new ArrayList<>();
                                    yijidanweiPhone.add(data.get(0));//要改的
                                    seachFields.put("yijidanweiPhone",yijidanweiPhone);
                                }
                        }

                        //查询是否重复
                         //账户
                        List<YijidanweiEntity> yijidanweiEntities_username = yijidanweiService.selectList(new EntityWrapper<YijidanweiEntity>().in("username", seachFields.get("username")));
                        if(yijidanweiEntities_username.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(YijidanweiEntity s:yijidanweiEntities_username){
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511,"数据库的该表中的 [账户] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //一级单位联系方式
                        List<YijidanweiEntity> yijidanweiEntities_yijidanweiPhone = yijidanweiService.selectList(new EntityWrapper<YijidanweiEntity>().in("yijidanwei_phone", seachFields.get("yijidanweiPhone")));
                        if(yijidanweiEntities_yijidanweiPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(YijidanweiEntity s:yijidanweiEntities_yijidanweiPhone){
                                repeatFields.add(s.getYijidanweiPhone());
                            }
                            return R.error(511,"数据库的该表中的 [一级单位联系方式] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        yijidanweiService.insertBatch(yijidanweiList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }


    /**
    * 登录
    */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        YijidanweiEntity yijidanwei = yijidanweiService.selectOne(new EntityWrapper<YijidanweiEntity>().eq("username", username));
        if(yijidanwei==null || !yijidanwei.getPassword().equals(password))
            return R.error("账号或密码不正确");
        //  // 获取监听器中的字典表
        // ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        // Map<String, Map<Integer, String>> dictionaryMap= (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        // Map<Integer, String> role_types = dictionaryMap.get("role_types");
        // role_types.get(.getRoleTypes());
        String token = tokenService.generateToken(yijidanwei.getId(),username, "yijidanwei", "一级单位");
        R r = R.ok();
        r.put("token", token);
        r.put("role","一级单位");
        r.put("username",yijidanwei.getYijidanweiName());
        r.put("tableName","yijidanwei");
        r.put("userId",yijidanwei.getId());
        return r;
    }

    /**
    * 注册
    */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody YijidanweiEntity yijidanwei){
//    	ValidatorUtils.validateEntity(user);
        Wrapper<YijidanweiEntity> queryWrapper = new EntityWrapper<YijidanweiEntity>()
            .eq("username", yijidanwei.getUsername())
            .or()
            .eq("yijidanwei_phone", yijidanwei.getYijidanweiPhone())
            ;
        YijidanweiEntity yijidanweiEntity = yijidanweiService.selectOne(queryWrapper);
        if(yijidanweiEntity != null)
            return R.error("账户或者一级单位联系方式已经被使用");
        yijidanwei.setCreateTime(new Date());
        yijidanweiService.insert(yijidanwei);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        YijidanweiEntity yijidanwei = new YijidanweiEntity();
        yijidanwei.setPassword("123456");
        yijidanwei.setId(id);
        yijidanweiService.updateById(yijidanwei);
        return R.ok();
    }


    /**
     * 忘记密码
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        YijidanweiEntity yijidanwei = yijidanweiService.selectOne(new EntityWrapper<YijidanweiEntity>().eq("username", username));
        if(yijidanwei!=null){
            yijidanwei.setPassword("123456");
            boolean b = yijidanweiService.updateById(yijidanwei);
            if(!b){
               return R.error();
            }
        }else{
           return R.error("账号不存在");
        }
        return R.ok();
    }


    /**
    * 获取用户的session用户信息
    */
    @RequestMapping("/session")
    public R getCurrYijidanwei(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        YijidanweiEntity yijidanwei = yijidanweiService.selectById(id);
        if(yijidanwei !=null){
            //entity转view
            YijidanweiView view = new YijidanweiView();
            BeanUtils.copyProperties( yijidanwei , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
    * 退出
    */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }





}
