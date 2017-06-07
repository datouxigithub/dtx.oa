package dtx.oa.workflow.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.workflow.app.DynamicSessionFactory;
import dtx.oa.workflow.idao.IApproverOptionDao;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import java.io.Serializable;
import javassist.CannotCompileException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UserFormDao extends BasicDao implements IUserFormDao{
    
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
    public DefaultUserForm getById(String formClassName, int id) {
        return (DefaultUserForm) findById(loadClass(formClassName), id);
    }
    
    @Override
    public Serializable add(Object obj){
        if(!(obj instanceof DefaultUserForm)){
            throw new RuntimeException("保存的对象必须为DefaultUserForm类型");
        }
        loadClass(obj.getClass().getName());
//        return super.add(obj);
        Session session=sessionFactory.getCurrentSession();
        Serializable result=session.save(obj);
        return result;
    }

    @Override
    public boolean delete(Object obj) {
        if(!(obj instanceof DefaultUserForm))
            return false;
        DefaultUserForm userForm=(DefaultUserForm)obj;
        IApproverOptionDao dao=(IApproverOptionDao) EntityUtil.getContext().getBean("approverOptionDao");
        if(!dao.deleteByUserFormId(String.format("%s.%d", userForm.getClass().getSimpleName(),userForm.getId())))
            return false;
        return super.delete(userForm);
    }
}
