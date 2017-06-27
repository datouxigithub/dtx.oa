/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.process.impl;

import java.io.Serializable;
import org.activiti.engine.repository.ProcessDefinition;

/**
 *
 * @author datouxi
 */
public interface IProcess extends Serializable{
    
    ProcessDefinition getProcessDefinition();
    
    boolean isPublic();
}
