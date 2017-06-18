/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import javassist.CannotCompileException;

/**
 *
 * @author datouxi
 */
public class TestSessionFactory {
    public static void main(String[] args) throws ReflectiveOperationException, IOException, CannotCompileException {
        IUserFormDao dao=(IUserFormDao) EntityUtil.getContext().getBean("userFormDao");
//        EntityUtil.getDynamicSessionFactory().createNewSessionFactory(EntityUtil.getCustomFormClassHelper().loadClass("dtx.oa.workflow.model.UserForm22c95ca9810f43288b97666592dbe9d4"));
        DefaultUserForm userForm=dao.getById("dtx.oa.workflow.model.UserForm0175e8f3d36d421e8a36556082b1792d", 1);
//        System.out.println(userForm.getStarter());
//        DefaultUserForm userForm=EntityUtil.getIUserFormDaoHelper().getById("dtx.oa.workflow.model.UserForm0175e8f3d36d421e8a36556082b1792d", 1);
        System.out.println(userForm.getStarter());
    }
 
}
