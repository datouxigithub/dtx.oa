/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.entity.impl;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

/**
 *
 * @author datouxi
 */
public class DTXGroupEntityManagerFactory implements SessionFactory{

    private DTXGroupEntityManager groupEntityManager;
    
    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return groupEntityManager;
    }

    /**
     * @param groupEntityManager the groupEntityManager to set
     */
    public void setGroupEntityManager(DTXGroupEntityManager groupEntityManager) {
        this.groupEntityManager = groupEntityManager;
    }
    
}
