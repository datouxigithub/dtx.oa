/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import dtx.oa.workflow.idao.ITestDao;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;

/**
 *
 * @author datouxi
 */
public class TestSessionFactory {
    public static void main(String[] args) {
//        IUserFormDao dao=(IUserFormDao) EntityUtil.getContext().getBean("userFormDao");
//        DefaultUserForm userForm=dao.getById("dtx.oa.workflow.model.UserForm22c95ca9810f43288b97666592dbe9d4", 1);
//        System.out.println(userForm.getStarter());
        ITestDao dao=(ITestDao) EntityUtil.getContext().getBean("testDao2");
        SessionImpl session=(SessionImpl) dao.getById("dtx.oa.workflow.model.UserForm22c95ca9810f43288b97666592dbe9d4", 1);
        System.out.println("==================>>>"+session.isClosed());
    }
 
}
