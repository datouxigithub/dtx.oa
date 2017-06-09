/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import dtx.oa.workflow.idao.ICustomFormClassDao;
import dtx.oa.workflow.model.CustomFormClassModel;
import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javassist.CannotCompileException;

/**
 *
 * @author datouxi
 */
public class CustomFormClassHelper {
    
    private CustomClassLoader customClassLoader;
    private DynamicSessionFactory dynamicSessionFactory;
    private final ThreadLocal<Boolean> isInitCustomFormClassHolder=new ThreadLocal<>();

    public void setDynamicSessionFactory(DynamicSessionFactory dynamicSessionFactory) {
        this.dynamicSessionFactory = dynamicSessionFactory;
    }

    public void setCustomClassLoader(CustomClassLoader customFormClassLoader) {
        this.customClassLoader = customFormClassLoader;
    }
    
    public Class loadClass(String name) throws IOException, CannotCompileException{
        ICustomFormClassDao formDao=(ICustomFormClassDao) EntityUtil.getContext().getBean("customFormClassDao");
        return loadClass(formDao.getByClassName(name));
    }
    
    public Class<?> loadClass(CustomFormClassModel formClass) throws IOException, CannotCompileException{
        return customClassLoader.loadClass(formClass.getFormClassName(), formClass.getClassSource());
    }
    
    public List<Class<?>> loadClass(List<CustomFormClassModel> formClasses) throws IOException, CannotCompileException{
        List<Class<?>> result=new ArrayList<>();
        for(CustomFormClassModel formClass:formClasses){
            result.add(loadClass(formClass));
        }
        return result;
    }
    
    public void initExistsClasses(){
        if(isInitCustomFormClassHolder.get()==null)
            isInitCustomFormClassHolder.set(false);
        if(isInitCustomFormClassHolder.get()==true)
            return;
        ICustomFormClassDao dao=(ICustomFormClassDao) EntityUtil.getContext().getBean("customFormClassDao");
        try {
            dynamicSessionFactory.createNewSessionFactory(loadClass(dao.getCustomFormClassModels()));
        } catch (IOException | CannotCompileException | ReflectiveOperationException | IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally{
            isInitCustomFormClassHolder.set(true);
        }
    }
    
    public DefaultUserForm newFormInstance(String formClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        initExistsClasses();
        Class clazz=Class.forName(formClassName);
        return (DefaultUserForm) clazz.newInstance();
    }
    
}
