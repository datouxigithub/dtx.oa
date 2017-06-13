/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.handler;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 *
 * @author datouxi
 */
public interface WorkFlowEventHandler {
    void handle(ActivitiEvent event);
}
