/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.controller;

import dtx.oa.workflow.util.EntityUtil;
import dtx.oa.workflow.util.SessionUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author datouxi
 */
@Controller
public class LoginController {
    
    //有DispatcherServlet943行处被调用,未显示页面
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(HttpServletResponse response) throws IOException{
        return "login/login";
    }
    
    @RequestMapping(value = "check/login",method = RequestMethod.POST)
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException{
        EntityUtil.getRBAC().authenticate(request.getParameter("username"), request.getParameter("userpwd"));
    }
    
    @RequestMapping("login_out")
    public void loginOut(HttpServletResponse response) throws IOException{
        SessionUtil.destorySession();
    }
    
}
