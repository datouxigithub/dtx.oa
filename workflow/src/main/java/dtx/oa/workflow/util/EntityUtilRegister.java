/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author gg
 */
public class EntityUtilRegister implements ApplicationContextAware{

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        EntityUtil.setContext(ac);
    }
    
}
