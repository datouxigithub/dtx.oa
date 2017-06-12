/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author gg
 */
public class RequestUtil {
    
    public static HttpServletRequest getRequest(){
        RequestAttributes attrs=RequestContextHolder.currentRequestAttributes();
        if(attrs!=null)
            return ((ServletRequestAttributes)attrs).getRequest();
        return null;
    }
    
    public static String getWholeRequestURI(){
        HttpServletRequest request=getRequest();
        String uri=request.getRequestURI();
        if(request.getQueryString()!=null)
            uri+="?"+request.getQueryString();
        return uri;
    }
    
}
