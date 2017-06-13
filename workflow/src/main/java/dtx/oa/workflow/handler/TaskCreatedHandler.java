/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.handler;

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
        task.setAssignee("大头希");
    }
    
}
