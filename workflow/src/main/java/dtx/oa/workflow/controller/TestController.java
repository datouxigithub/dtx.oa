/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtx.oa.rbac.dao.RBACDao;
import dtx.oa.workflow.app.ManageTaskListener;
import dtx.oa.workflow.dao.TestDao;
import dtx.oa.workflow.idao.IApproverOptionDao;
import dtx.oa.workflow.idao.ICustomFormInfoDao;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.ApproverOptionModel;
import dtx.oa.workflow.model.CustomFormInfoModel;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import dtx.oa.workflow.util.RequestUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.DispatcherServlet;

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
//        TestDao dao=(TestDao) EntityUtil.getContext().getBean("testDao");
        IUserFormDao iUserDao=(IUserFormDao) EntityUtil.getContext().getBean("userFormDao");
        Serializable result=iUserDao.add(userForm);
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
    
    @RequestMapping(value = "run/{modelId}")
    public void run(@PathVariable("modelId")String modelId,HttpServletRequest request,HttpServletResponse response) throws IOException{
        RepositoryService repositoryService=EntityUtil.getRepositoryService();
        org.activiti.engine.repository.Model model=repositoryService.getModel(modelId);
        ObjectNode nodeModel=(ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
        BpmnModel bpmnModel=new BpmnJsonConverter().convertToBpmnModel(nodeModel);
        Deployment deployment=repositoryService.createDeployment().name(model.getName()).addString(model.getName()+".bpmn20.xml", new String(new BpmnXMLConverter().convertToXML(bpmnModel))).deploy();
        System.out.println(deployment.toString());
        
        
        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey(processDefinition.getKey());
        while(!ManageTaskListener.sampleUsers.isEmpty()){
            String assigner=ManageTaskListener.sampleUsers.pop();
            Task task=(Task) EntityUtil.getTaskService().createTaskQuery().taskAssignee(assigner).singleResult();
            System.out.println(assigner+"------------------->>>"+task.toString());
            EntityUtil.getTaskService().complete(task.getId());
        }
        
        response.sendRedirect(String.format("%s/workflow/list", request.getContextPath()));
    }
}
