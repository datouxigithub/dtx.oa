package dtx.oa.workflow.filter;

import dtx.oa.workflow.util.EntityUtil;
import dtx.oa.workflow.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{
    
    //在DispatcherServlet1011行，即调用render方法显示页面
    //spring:scope=session,根据ID把变量放到session中,不过要显式调用变量的时候才放到session中
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().endsWith("/login")||request.getRequestURI().endsWith("/login_out"))
            return true;
        EntityUtil.getRequestHeader().setReferer(RequestUtil.getWholeRequestURI());
        if(!EntityUtil.getRBAC().isLogin()){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        return true;
    }

    //在DispatcherServlet950行处被调用(在HandlerExecutionChain的applyPostHandle方法处被调用),未显示页面
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(request.getRequestURI().endsWith("/login_out")){
            response.sendRedirect(request.getContextPath()+"/login");
        }
        if(request.getRequestURI().endsWith("/login")){
            if(EntityUtil.getRBAC().isLogin()){
                if(EntityUtil.getRequestHeader().getReferer()==null)
                    response.sendRedirect(request.getContextPath()+"/test/util");
                else
                    response.sendRedirect(EntityUtil.getRequestHeader().getReferer());
            }else{
                if(request.getMethod().toUpperCase().equals("POST")){
                    response.sendRedirect(request.getContextPath()+"/login");
                }
            }
        }
    }

    //在HandlerExecutionChain的triggerAfterCompletion方法处被调用,调用前已显示页面
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
