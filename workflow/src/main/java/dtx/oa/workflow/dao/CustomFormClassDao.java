/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.workflow.idao.ICustomFormClassDao;
import dtx.oa.workflow.model.CustomFormClassModel;
import java.util.List;

/**
 *
 * @author gg
 */
public class CustomFormClassDao extends BasicDao implements ICustomFormClassDao{

    @Override
    public CustomFormClassModel getByClassName(String className) {
        return (CustomFormClassModel) executeQuery("FROM CustomFormClassModel customFormClassModel WHERE customFormClassModel.formClassName=?", new Object[]{className}).get(0);
    }

    @Override
    public List<CustomFormClassModel> getCustomFormClassModels() {
        return executeQuery("FROM CustomFormClassModel", null);
    }
    
}
