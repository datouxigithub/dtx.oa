/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;

/**
 *
 * @author gg
 */
public class TestModelXML {
    public static void main(String[] args) throws IOException {
        RepositoryService service=EntityUtil.getRepositoryService();
        Model model=service.getModel("40003");
        ObjectNode nodeModel=(ObjectNode) new ObjectMapper().readTree(service.getModelEditorSource(model.getId()));
        BpmnModel bpmnModel=new BpmnJsonConverter().convertToBpmnModel(nodeModel);
        byte[] bytes=new BpmnXMLConverter().convertToXML(bpmnModel);
        String content=new String(bytes,"utf8");
        DeploymentBuilder builder=service.createDeployment().name(model.getName()).addString(model.getName()+".bpmn20.xml", content);
        Deployment deployment=builder.deploy();
    }
}
