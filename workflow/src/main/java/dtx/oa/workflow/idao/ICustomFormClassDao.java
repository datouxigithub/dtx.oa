/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.workflow.model.CustomFormClassModel;
import java.util.List;

/**
 *
 * @author gg
 */
public interface ICustomFormClassDao extends IBasicDao{
    CustomFormClassModel getByClassName(String className);
    List<CustomFormClassModel> getCustomFormClassModels();
}
