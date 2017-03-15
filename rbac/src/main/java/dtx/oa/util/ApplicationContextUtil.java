/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author datouxi
 */
public class ApplicationContextUtil {
    private final static ApplicationContext appContext=new ClassPathXmlApplicationContext("beans.xml");
    
    public static ApplicationContext getApplicationContext(){
        return appContext;
    }
    
}
