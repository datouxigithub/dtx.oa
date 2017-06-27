/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.model;

import dtx.oa.workflow.process.impl.ISpecialProcess;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.activiti.engine.repository.ProcessDefinition;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author datouxi
 */
@Entity
@Table(name = "special_process")
public class SpecialProcessModel implements ISpecialProcess{
    
    private String uuid;
    private ProcessDefinition processDefinition;
    private CustomFormInfoModel customFormInfoModel;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public void setCustomFormInfoModel(CustomFormInfoModel customFormInfoModel) {
        this.customFormInfoModel = customFormInfoModel;
    }

    @Override
    @OneToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "customFormInfoModelId",insertable = true,unique = true)
    public CustomFormInfoModel getCustomFormInfoModel() {
        return customFormInfoModel;
    }

    @Override
    @OneToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "processDefinitionId",unique = true)
    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    @Override
    public boolean isPublic() {
        return false;
    }
    
}
