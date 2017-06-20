/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.handler;

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
        if(EntityUtil.getRuntimeService().getVariables(entityEvent.getExecutionId()).containsKey(ProcessStartHandler.STARTER_KEY)){
            task.setAssignee((String) EntityUtil.getRuntimeService().getVariable(entityEvent.getExecutionId(), ProcessStartHandler.STARTER_KEY));
            EntityUtil.getRuntimeService().removeVariable(entityEvent.getExecutionId(), ProcessStartHandler.STARTER_KEY);
        }
    }
    
}
