/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.util;

import dtx.oa.rbac.dao.RBACDao;
import dtx.oa.workflow.app.CustomFormClassHelper;
import dtx.oa.workflow.app.DynamicSessionFactory;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.RequestHeaderModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author datouxi
 */
public class EntityUtil implements ApplicationContextAware{
    
    private static ApplicationContext context=new ClassPathXmlApplicationContext("testbeans.xml");
    
    public static void setContext(ApplicationContext ac){
        context=ac;
    }
    
    public static ApplicationContext getContext(){
        return context;
    }
    
    public static SessionFactory getSessionFactory(){
        return (SessionFactory) getContext().getBean("sessionFactory");
    }
    
    public static DynamicSessionFactory getDynamicSessionFactory(){
        return (DynamicSessionFactory) getContext().getBean("dynamicSessionFactory");
    }
    
    public static CustomFormClassHelper getCustomFormClassHelper(){
        return (CustomFormClassHelper) getContext().getBean("customFormClassHelper");
    }
    
    public static ProcessEngine getProcessEngine(){
        return (ProcessEngine) getContext().getBean("processEngine");
    }
    
    public static RepositoryService getRepositoryService(){
        return (RepositoryService) getContext().getBean("repositoryService");
    }
    
    public static RuntimeService getRuntimeService(){
        return (RuntimeService) getContext().getBean("runtimeService");
    }
    
    public static TaskService getTaskService(){
        return (TaskService) getContext().getBean("taskService");
    }
    
    public static FormService getFormService(){
        return (FormService) getContext().getBean("formService");
    }
    
    public static HistoryService getHistoryService(){
        return (HistoryService) getContext().getBean("historyService");
    }
    
    public static ManagementService getManagementService(){
        return (ManagementService) getContext().getBean("managementService");
    }
    
    public static IdentityService getIdentityService(){
        return (IdentityService) getContext().getBean("identityService");
    }
    
    public static RBACDao getRBAC(){
        return (RBACDao) getContext().getBean("rbac");
    }
    
    public static RequestHeaderModel getRequestHeader(){
        return (RequestHeaderModel) getContext().getBean("requestHeader");
    }
    
    public static IUserFormDao getUserFormDao(){
        return (IUserFormDao) getContext().getBean("userFormDao");
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        EntityUtil.setContext(ac);
    }
}
