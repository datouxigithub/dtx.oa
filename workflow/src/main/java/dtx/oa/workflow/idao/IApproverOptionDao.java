/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.workflow.model.ApproverOptionModel;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IApproverOptionDao extends IBasicDao{
    
    List<ApproverOptionModel> queryByUserFormId(String userFormId);
    
    boolean deleteByUserFormId(String userFormId);
}
