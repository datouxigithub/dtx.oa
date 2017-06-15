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
public class TaskCompledHandler implements WorkFlowEventHandler{

    public static int count=1;
    
    @Override
    public void handle(ActivitiEvent event) {
        ActivitiEntityEvent entityEvent=(ActivitiEntityEvent) event;
        TaskEntity task=(TaskEntity) entityEvent.getEntity();
        if(task.getAssignee()==null)
            task.setAssignee("臭屎洋"+(TaskCompledHandler.count++));
    }
    
}
