package tech.zxuuu.hotel24h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.zxuuu.hotel24h.entity.Emp;
import tech.zxuuu.hotel24h.service.LoginService;
import tech.zxuuu.hotel24h.util.JSONUtils;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping(path = "login")
    public String turnToLoginPage() {return "emp/login";}

    @GetMapping(path = "adminPage")
    public String turnToAdminPage() {return "emp/admin";}

    @GetMapping(path = "indexPage")
    public String turnToIndexPage() {return "index";}

    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String empId, @RequestParam String empPassword) {
        Emp emp = this.loginService.empLogin(empId, empPassword);
        Map map = new HashMap<String, String>();
        if (emp == null) {
            map.put("empId", "");
            map.put("empName", "");
        } else {
            map.put("empId", emp.getId());
            map.put("empName", emp.getName());

        }
        return JSONUtils.buildJSON(map);
    }

    @PostMapping(path = "/changePwd")
    public @ResponseBody String changePwd(@RequestParam String empId, @RequestParam String empPwd, @RequestParam String newEmpPwd) {
        Map map = new HashMap<String, Boolean>();
        if (this.loginService.empLogin(empId, empPwd) == null) {
            map.put("isOk", false);
        } else {
            this.loginService.changePwd(empId, newEmpPwd);
            map.put("isOk", true);
        }
        return JSONUtils.buildJSON(map);
    }

    @GetMapping(path = "/changePwdPage")
    public String turnToChangePwdPage() {return "emp/changePwd";}
}
