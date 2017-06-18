/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.advice;

import dtx.oa.workflow.model.DefaultUserForm;
import dtx.oa.workflow.util.EntityUtil;
import java.io.IOException;
import javassist.CannotCompileException;
import org.aspectj.lang.JoinPoint;

public class LoadUserFormAdvice {
    
    public void doBefore(JoinPoint joinPoint) throws IOException, CannotCompileException, ReflectiveOperationException{
        if(joinPoint.getArgs().length<=0)
            return;
        Object arg=joinPoint.getArgs()[0];
        Class clazz=arg.getClass();
        if(arg instanceof String)
            clazz=EntityUtil.getCustomFormClassHelper().loadClass((String)arg);
        if(!DefaultUserForm.class.isAssignableFrom(clazz)){
            throw new RuntimeException("操作的对象必须为DefaultUserForm类型");
        }
        EntityUtil.getDynamicSessionFactory().createNewSessionFactory(clazz);
    }
}
