/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.workflow.app.DynamicSessionFactory;
import dtx.oa.workflow.idao.ITestDao;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import javassist.CannotCompileException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 *
 * @author datouxi
 */
public class TestDao2  extends BasicDao implements ITestDao{
    
    private Class loadClass(String formClassName) {
        try {
            Class clazz=EntityUtil.getCustomFormClassHelper().loadClass(formClassName);
            ((DynamicSessionFactory)sessionFactory).createNewSessionFactory(clazz);
            return clazz;
        } catch (IOException | CannotCompileException | ReflectiveOperationException | IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } 
    }

    @Override
    public Session getById(String formClassName, int id) {
        Class clazz=loadClass(formClassName);
        Session session=SessionFactoryUtils.getSession(sessionFactory, false);
        session.get(clazz, id);
        return session;
    }
    
}
