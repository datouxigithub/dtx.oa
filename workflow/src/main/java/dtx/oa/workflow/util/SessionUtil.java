package dtx.oa.workflow.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    
    public static HttpSession getSession(){
        return getSession(true);
    }
    
    public static HttpSession getExistsSession(){
        return getSession(false);
    }
    
    private static HttpSession getSession(boolean create){
        HttpServletRequest request=RequestUtil.getRequest();
        if(request!=null)
            return request.getSession(create);
        return null;
    }
    
}
