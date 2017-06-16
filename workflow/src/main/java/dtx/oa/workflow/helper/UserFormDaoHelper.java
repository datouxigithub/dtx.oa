/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.helper;

import dtx.oa.workflow.dao.UserFormDao;
import dtx.oa.workflow.idao.IUserFormDao;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import java.io.Serializable;
import javassist.CannotCompileException;

/**
 *
 * @author datouxi
 */
public class UserFormDaoHelper {
    
    private static Class loadClass(String formClassName) {
        try {
            Class clazz=EntityUtil.getCustomFormClassHelper().loadClass(formClassName);
            EntityUtil.getDynamicSessionFactory().createNewSessionFactory(clazz);
            return clazz;
        } catch (IOException | CannotCompileException | ReflectiveOperationException | IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } 
    }
    
    public static DefaultUserForm getById(String formClassName,int id){
        Class clazz=loadClass(formClassName);
        return EntityUtil.getUserFormDao().getById(clazz, id);
    }
    
    public static DefaultUserForm getById(Class userFormClass,int id){
        return getById(userFormClass.getName(), id);
    }
    
    public static Serializable add(Object obj){
        loadClass(obj.getClass().getName());
        return EntityUtil.getUserFormDao().add(obj);
    }
}
