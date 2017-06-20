/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtx.oa.rbac.dao.RBACDao;
import dtx.oa.workflow.idao.ICustomFormInfoDao;
import dtx.oa.workflow.model.CustomFormInfoModel;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.ActivitiHelper;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javassist.CannotCompileException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author datouxi
 */
@Controller
@RequestMapping(value = "/test")
@SessionAttributes(value = {"userForm"})
public class TestController {
    
    @RequestMapping(value = "util",method = RequestMethod.GET)
    public void util(HttpServletResponse response) throws IOException{
        RBACDao rbac=EntityUtil.getRBAC();
        response.getWriter().write("<h2>rbac</h2>");
        response.getWriter().write(String.valueOf(rbac.isLogin()));
    }
    
    @RequestMapping(value = "input",method = RequestMethod.GET)
    public String input(@RequestParam("id") int id,Model model) throws IOException, CannotCompileException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
        CustomFormInfoModel formInfo=dao.getById(id);
        model.addAttribute("formInfo", formInfo);
        model.addAttribute("userForm",(DefaultUserForm)EntityUtil.getCustomFormClassHelper().loadClass(formInfo.getCustomFormClass()).newInstance());
        return "input";
    }
    
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public void submit(@ModelAttribute("userForm") DefaultUserForm userForm,HttpServletRequest request,HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException, CannotCompileException, ReflectiveOperationException{
        EntityUtil.getDynamicSessionFactory().createNewSessionFactory(userForm.getClass());
        userForm.setStarter(EntityUtil.getRBAC().getLoginInfo().getUuid());
        Serializable result=EntityUtil.getUserFormDao().add(userForm);
        response.getWriter().write(result.toString());
//        ApproverOptionModel option=new ApproverOptionModel();
//        option.setAppvoer(ManageTaskListener.sampleUsers.pop());
//        option.setComment(request.getParameter("comment"));
//        option.setApprolDate(new Date());
//        option.setUserFormId(userForm.getClass().getSimpleName()+"."+userForm.getId());
//        IApproverOptionDao iApproverDao=(IApproverOptionDao) EntityUtil.getContext().getBean("approverOptionDao");
//        iApproverDao.add(option);
//        response.getWriter().write(option.toString());
    }
    
    @RequestMapping(value = "run2/{deploymentId}")
    public void run2(@PathVariable("deploymentId")String deploymentId,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
        RepositoryService repositoryService=EntityUtil.getRepositoryService();
        Deployment deployment=repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        ProcessDefinition processDefinition=ActivitiHelper.getProcessDefinitionByDeploymentId(deploymentId);
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey(processDefinition.getKey(), dao.getById(2).getCustomFormClass().getFormClassName()+".1");
        TaskQuery query=EntityUtil.getTaskService().createTaskQuery().processInstanceId(processInstance.getId());
        ActivitiHelper.completeTask(query.singleResult().getId());
        Task task2=query.singleResult();
        task2.setAssignee("ttt");
        ActivitiHelper.completeTask(task2.getId());
        List<Task> list=query.list();
        for(Task task:list){
            System.out.println("===============>>>"+new String(task.getName().getBytes(),"utf8"));
        }
    }
    
    @RequestMapping(value = "run/{modelId}")
    public void run(@PathVariable("modelId")String modelId,HttpServletRequest request,HttpServletResponse response) throws IOException, CannotCompileException, ReflectiveOperationException{
        RepositoryService repositoryService=EntityUtil.getRepositoryService();
//        org.activiti.engine.repository.Model model=repositoryService.getModel(modelId);
//        ObjectNode nodeModel=(ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
//        BpmnModel bpmnModel=new BpmnJsonConverter().convertToBpmnModel(nodeModel);
//        Deployment deployment=repositoryService.createDeployment().name(model.getName()).addString(model.getName()+".bpmn20.xml", new String(new BpmnXMLConverter().convertToXML(bpmnModel),"utf8")).deploy();
        
        Deployment deployment=repositoryService.createDeploymentQuery().deploymentId(modelId).singleResult();
        
        ProcessDefinition processDefinition=ActivitiHelper.getProcessDefinitionByDeploymentId(deployment.getId());
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
//        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey(processDefinition.getKey());
        CustomFormInfoModel customFormInfo=dao.getById(2);
        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey(processDefinition.getKey(), customFormInfo.getCustomFormClass().getFormClassName()+".1");
        TaskQuery query=EntityUtil.getTaskService().createTaskQuery().processInstanceId(processInstance.getId());
        while(query.count()>0){
            Task task=query.singleResult();
//            break;
            ActivitiHelper.completeTask(task.getId());
        }
//        while(!ManageTaskListener.sampleUsers.isEmpty()){
//            String assigner=ManageTaskListener.sampleUsers.pop();
//            Task task=(Task) EntityUtil.getTaskService().createTaskQuery().taskAssignee(assigner).singleResult();
//            System.out.println(task.getAssignee()+"------------------->>>"+task.toString());
//            EntityUtil.getTaskService().complete(task.getId());
//        }
//        Task task=ActivitiHelper.getTaskByAssinger("大头希");
//        if(task!=null)
//            ActivitiHelper.completeTask(task.getId());
        response.sendRedirect(String.format("%s/workflow/list", request.getContextPath()));
    }
}
