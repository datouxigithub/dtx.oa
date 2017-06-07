/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import dtx.oa.workflow.idao.ICustomFormInfoDao;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.CustomFormInfoModel;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;

/**
 *
 * @author datouxi
 */
public class TestDao {
    public static void main(String[] args) {
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
        CustomFormInfoModel formInfo=dao.getById(2);
        IUserFormDao iUserDao=(IUserFormDao) EntityUtil.getContext().getBean("userFormDao");
        DefaultUserForm userForm=iUserDao.getById(formInfo.getCustomFormClass().getFormClassName(), 1);
    }
}
