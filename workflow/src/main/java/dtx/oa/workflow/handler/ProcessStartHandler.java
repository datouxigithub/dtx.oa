/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.handler;

import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiProcessStartedEvent;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

/**
 *
 * @author datouxi
 */
public class ProcessStartHandler implements WorkFlowEventHandler{
    
    public final static String STARTER_KEY="starter";

    @Override
    public void handle(ActivitiEvent event) {
        ActivitiProcessStartedEvent processStartedEvent=(ActivitiProcessStartedEvent) event;
        ExecutionEntity entity=(ExecutionEntity) processStartedEvent.getEntity();
        String businessKey=entity.getBusinessKey();
        try{
            String formClassName=businessKey.substring(0, businessKey.lastIndexOf("."));
            int formId=Integer.parseInt(businessKey.substring(businessKey.lastIndexOf(".")+1));
            DefaultUserForm userForm=EntityUtil.getUserFormDao().getById(formClassName, formId);
            EntityUtil.getRuntimeService().setVariable(processStartedEvent.getExecutionId(), ProcessStartHandler.STARTER_KEY, userForm.getStarter());
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
