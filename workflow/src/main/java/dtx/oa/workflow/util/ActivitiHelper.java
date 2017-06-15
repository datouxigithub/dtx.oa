/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 *
 * @author datouxi
 */
public class ActivitiHelper {
    
    public static Task getTaskById(String taskId){
        return EntityUtil.getTaskService().createTaskQuery().taskId(taskId).singleResult();
    }
    
    public static List<Task> getTasksByAssinger(String assigner){
        return EntityUtil.getTaskService().createTaskQuery().taskAssignee(assigner).orderByDueDate().desc().list();
    }
    
    public static Task getTaskByAssinger(String assigner){
        List<Task> result=getTasksByAssinger(assigner);
        if(result.isEmpty())
            return null;
        return result.get(0);
    }
    
    public static void completeTask(String taskId){
        EntityUtil.getTaskService().complete(taskId);
    }
    
    public static boolean isTheStartTask(String processInstanceId){
        if(EntityUtil.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId)==null)
            return false;
        return EntityUtil.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list().isEmpty();
    }
    
    public static ProcessDefinition getProcessDefinitionById(String processDefinitionId){
        return EntityUtil.getRepositoryService().getProcessDefinition(processDefinitionId);
    }
    
    public static ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId){
        return EntityUtil.getRepositoryService().createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    }
    
    public static ProcessInstance getProcessInstanceById(String processInstanceId){
        return EntityUtil.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }
    
    public static List<String> getOutComesByTaskId(String taskId){
        List<String> result=new ArrayList<>();
        Task task=getTaskById(taskId);
        ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) getProcessDefinitionById(task.getProcessDefinitionId());
        ProcessInstance processInstance=getProcessInstanceById(task.getProcessInstanceId());
        ActivityImpl activityImpl=processDefinitionEntity.findActivity(processInstance.getActivityId());
        Iterator<PvmTransition> iter=activityImpl.getOutgoingTransitions().iterator();
        while(iter.hasNext()){
            String name=(String) iter.next().getProperty("name");
            if(name!=null)
                result.add(name);
        }
        return result;
    }
    
}
