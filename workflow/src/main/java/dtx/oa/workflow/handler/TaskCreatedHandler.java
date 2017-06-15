/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.handler;

import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.ActivitiHelper;
import dtx.oa.workflow.util.EntityUtil;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 *
 * @author datouxi
 */
public class TaskCreatedHandler implements WorkFlowEventHandler{

    @Override
    public void handle(ActivitiEvent event) {
        ActivitiEntityEvent entityEvent=(ActivitiEntityEvent) event;
        TaskEntity task=(TaskEntity) entityEvent.getEntity();
        if(ActivitiHelper.isTheStartTask(entityEvent.getProcessInstanceId())){
            String businessKey=task.getProcessInstance().getBusinessKey();
            String formClassName=businessKey.substring(0, businessKey.lastIndexOf("."));
            int formId=Integer.parseInt(businessKey.substring(businessKey.lastIndexOf(".")+1));
            System.out.println("======================>>>["+formClassName+","+formId+"]");
            {
//                检查bussinesskey是否合格
//                if(businessInfo.length<2)
//                    throw new RuntimeException("businesskey错误");
            }
            IUserFormDao dao=(IUserFormDao) EntityUtil.getContext().getBean("userFormDao");
            DefaultUserForm userForm=dao.getById(formClassName, formId);
            task.setAssignee(userForm.getStarter());
        }
    }
    
}
