/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.listener;

import dtx.oa.workflow.handler.WorkFlowEventHandler;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 *
 * @author datouxi
 */
public class WorkFlowGlobalListener implements ActivitiEventListener{
    
    private Map<String,WorkFlowEventHandler> handleMap=new HashMap<>();

    public void setHandleMap(Map<String, WorkFlowEventHandler> handleMap) {
        this.handleMap = handleMap;
    }

    @Override
    public void onEvent(ActivitiEvent event) {
//        System.out.println("==================>>>"+event.getType().name());
        WorkFlowEventHandler handler=handleMap.get(event.getType().name());
        if(handler!=null)
            handler.handle(event);
    }

    @Override
    public boolean isFailOnException() {
        return true;
    }
    
}
