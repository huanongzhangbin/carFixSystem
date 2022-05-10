package com.example.carfixsystem.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.carfixsystem.entity.Employee;
import com.example.carfixsystem.entity.User;
import com.example.carfixsystem.service.IEmployeeService;
import com.example.carfixsystem.service.IUserService;
import com.example.carfixsystem.util.JSONResult;
import com.example.carfixsystem.util.JWTUtils;
import com.example.carfixsystem.util.MessageUtils;
import com.example.carfixsystem.vo.EmployeeLoginVo;
import com.example.carfixsystem.vo.UserLoginVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Api("登录控制器")
@Controller
@Slf4j
//@RequestMapping("/employee")
public class LoginController {
    @Value("${app.appid}")
    private  String appid;
    @Value("${app.appkey}")
    private String appkey;

    @Autowired
    IEmployeeService iEmployeeService;
    @Autowired
    IUserService userService;
    @PostMapping("/employee/login")
    public JSONResult login( @RequestBody EmployeeLoginVo employeeLoginVo) {
        log.info(employeeLoginVo.toString());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("employee_name", employeeLoginVo.getEmployeeName());
        queryWrapper.eq("password", employeeLoginVo.getPassword());
        Employee employee = iEmployeeService.getOne(queryWrapper);

        if (employee != null) {
            Map<String, String> infoMap = new HashMap<>();
            infoMap.put("id", employee.getId().toString());
            String token = String.valueOf(JWTUtils.createToken(infoMap));
           // request.getSession().setAttribute("token", token);

            // return JSONResult.custom(200,"token",token);
            HashMap<String, String> result = new HashMap<>();
            result.put("msg", "登录成功");
            result.put("token",token);
            result.put("id", employee.getId().toString());
            result.put("roleName", employee.getRoleName());
            result.put("employeeName",employee.getEmployeeName());
            return JSONResult.success(result);
        }
        return JSONResult.failMsg("账号密码错误");

    }

    @GetMapping("/employee/initMenu/{roleName}")
    public ResponseEntity initMenu(@PathVariable String roleName) {
        String allAuth = "[{\"id\":1,\"authName\":\"员工管理\",\"children\":[{\"id\":11,\"authName\":\"员工列表\",\"path\":\"/employee\"}]},{\"id\":2,\"authName\":\"订单管理\",\"children\":[{\"id\":21,\"authName\":\"订单列表\",\"path\":\"/order\"}]},{\"id\":3,\"authName\":\"配件管理\",\"children\":[{\"id\":31,\"authName\":\"配件种类管理\",\"path\":\"/fittingType\"},{\"id\":32,\"authName\":\"配件出库管理\",\"path\":\"/fittingManeger\"},{\"id\":33,\"authName\":\"配件入库\",\"path\":\"/importFitting\"}]},{\"id\":5,\"authName\":\"修理工专用\",\"children\":[{\"id\":51,\"authName\":\"订单处理\",\"path\":\"/doneOrder\"}]}]";
        //{"id":4,"authName":"绩效管理","children":[{"id":41,"authName":"员工绩效","path":"/salary"}]}
        String qianTai ="[{\"id\":2,\"authName\":\"订单管理\",\"children\":[{\"id\":21,\"authName\":\"订单列表\",\"path\":\"/order\"}]}]";
        String kuGuang="[{\"id\":3,\"authName\":\"配件管理\",\"children\":[{\"id\":31,\"authName\":\"配件服务管理\",\"path\":\"/fittingType\"},{\"id\":32,\"authName\":\"配件出库管理\",\"path\":\"/fittingManeger\"},{\"id\":33,\"authName\":\"配件入库\",\"path\":\"/importFitting\"}]}]";
        String xiuLiGong="[{\"id\":5,\"authName\":\"修理工专用\",\"children\":[{\"id\":51,\"authName\":\"订单处理\",\"path\":\"/doneOrder\"}]}]";
        HashMap<String, String> res = new HashMap<>();

        if(roleName.equals("超级管理员")){
            res.put("menuList", allAuth);
        }else if(roleName.equals("前台")){
            res.put("menuList",qianTai);
        }else if(roleName.equals("库存管理员")){
            res.put("menuList",kuGuang);
        }else if(roleName.equals("修理工")){
            res.put("menuList",xiuLiGong);
        }
       // res.put("menuList",allAuth);
        return JSONResult.success(res);
    }

   @PostMapping("/user/login")
    public JSONResult userLogin(@RequestBody UserLoginVo userLoginVo){
       log.info(userLoginVo.toString());
       QueryWrapper queryWrapper = new QueryWrapper<User>();
       queryWrapper.eq("username", userLoginVo.getUsername());
       queryWrapper.eq("password", userLoginVo.getPassword());
       User user = userService.getOne(queryWrapper);

       if (user != null) {
           Map<String, String> infoMap = new HashMap<>();
           infoMap.put("id", user.getId().toString());
           String token = String.valueOf(JWTUtils.createToken(infoMap));
         //  request.getSession().setAttribute("token", token);

           // return JSONResult.custom(200,"token",token);
           HashMap<String, Object> result = new HashMap<>();
           result.put("msg", "登录成功");
           result.put("token", token);
           result.put("id", user.getId().toString());
           result.put("user",user);
           log.info(user.getCarNumber()+"xxxx");
           result.put("carNumber",user.getCarNumber());
           return JSONResult.success(result);
       }
       return JSONResult.failMsg("账号密码错误");

   }

    @PostMapping("/user/loginByPhone/{phone}/{phoneCode}")
    public JSONResult loginByPhone(HttpServletRequest request ,@PathVariable String phone,@PathVariable String phoneCode){
       // log.info(userLoginVo.toString());

        HttpSession session=request.getSession();
        String pCode= (String) session.getAttribute("phoneCode"+phone);
        User user=null;
        if(phoneCode!=null&&phoneCode.equals(pCode)){
            QueryWrapper queryWrapper = new QueryWrapper<User>();
            queryWrapper.eq("phone",phone);
            // queryWrapper.eq("password", userLoginVo.getPassword());
            user= userService.getOne(queryWrapper);
        }
        if (user != null) {
            Map<String, String> infoMap = new HashMap<>();
            infoMap.put("id", user.getId().toString());
            String token = String.valueOf(JWTUtils.createToken(infoMap));
           // request.getSession().setAttribute("token", token);

            // return JSONResult.custom(200,"token",token);
            HashMap<String, Object> result = new HashMap<>();
            result.put("msg", "登录成功");
            result.put("token", token);
            result.put("id", user.getId().toString());
            result.put("user",user);
            log.info(user.getCarNumber()+"xxxx");
            result.put("carNumber",user.getCarNumber());
            return JSONResult.success(result);
        }
        return JSONResult.failMsg("账号密码错误");

    }

    @PostMapping("/user/register")
    public JSONResult userRegister(HttpServletRequest request ,@RequestBody User user){
        log.info(user.toString());
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());

        User user1 = userService.getOne(queryWrapper);

        if (user1 == null) {
          boolean success= userService.save(user);
            if(success){
                HashMap<String, String> result = new HashMap<>();
                result.put("msg", "注册成功");
                return JSONResult.success(result);
            }
            return JSONResult.failMsg("注册失败");
        }
        return JSONResult.failMsg("注册失败,账号已经存在");

    }
    @PostMapping("/user/sendPhoneCode/{phone}")
    public JSONResult user_sendmessage(HttpServletRequest request ,@PathVariable  String phone)
    {
        //String message= MessageUtils.sendMessage(phone,appid,appkey);
        String message="988153";
        System.out.println("appid="+appkey);
        System.out.println("获得的手机号码"+phone);
        if(message!=null){
           // System.out.println("系统生成的验证码是："+message);
            request.getSession().setAttribute("phoneCode"+phone,message);
            HashMap<String,String> res=new HashMap<>();
            res.put("phoneCode",message);
            return JSONResult.success(res);
        }else{
            return JSONResult.failMsg("发送信息失败");
        }
    }
}
