/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author datouxi
 */
public class LogUtil {

    private static Log logger;
    
    public static Log getLogger(){
        if(logger==null)
            logger=LogFactory.getLog(LogUtil.class);
        return logger;
    }
    
}
