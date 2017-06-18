package dtx.oa.workflow.dao;

import dtx.oa.workflow.app.DynamicSessionFactory;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import java.io.Serializable;
import javassist.CannotCompileException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserFormDao implements IUserFormDao{

    private DynamicSessionFactory SessionFactory;

    public void setSessionFactory(DynamicSessionFactory SessionFactory) {
        this.SessionFactory = SessionFactory;
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public DefaultUserForm getById(String formClassName, int id) {
        Class clazz=null;
        try {
            clazz=EntityUtil.getCustomFormClassHelper().loadClass(formClassName);
        } catch (IOException | CannotCompileException ex) {
            throw new RuntimeException(ex);
        }
        Session session=SessionFactoryUtils.getNewSession(SessionFactory);
        session.beginTransaction();
        DefaultUserForm result=(DefaultUserForm) session.get(clazz, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Serializable add(DefaultUserForm userForm) {
        Session session=SessionFactoryUtils.getNewSession(SessionFactory);
        session.beginTransaction();
        Serializable result=session.save(userForm);
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public boolean delete(Object obj) {
//        DefaultUserForm userForm=(DefaultUserForm)obj;
//        IApproverOptionDao dao=(IApproverOptionDao) EntityUtil.getContext().getBean("approverOptionDao");
//        if(!dao.deleteByUserFormId(String.format("%s.%d", userForm.getClass().getSimpleName(),userForm.getId())))
//            return false;
//        return super.delete(userForm);
        return false;
    }
}
