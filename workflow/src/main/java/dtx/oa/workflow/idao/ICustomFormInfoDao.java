/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.workflow.model.CustomFormInfoModel;

/**
 *
 * @author datouxi
 */
public interface ICustomFormInfoDao extends IBasicDao{

    public CustomFormInfoModel getById(int id);
    
}
