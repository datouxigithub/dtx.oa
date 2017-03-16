/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao.factory;

import dtx.oa.rbac.idao.INodeDao;
import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.IRoleNodeDao;
import dtx.oa.rbac.idao.IRoleUserDao;
import dtx.oa.rbac.idao.IUserDao;
import dtx.oa.util.ApplicationContextUtil;

/**
 *
 * @author datouxi
 */
public class IDaoFactory {
    
    public static IUserDao iUserDao(){
        return (IUserDao) ApplicationContextUtil.getApplicationContext().getBean("userDao");
    }
    
    public static IRoleDao iRoleDao(){
        return (IRoleDao) ApplicationContextUtil.getApplicationContext().getBean("roleDao");
    }
    
    public static INodeDao iNodeDao(){
        return (INodeDao) ApplicationContextUtil.getApplicationContext().getBean("nodeDao");
    }
    
    public static IRoleNodeDao iRoleNodeDao(){
        return (IRoleNodeDao) ApplicationContextUtil.getApplicationContext().getBean("roleNodeDao");
    }
    
    public static IRoleUserDao iRoleUserDao(){
        return (IRoleUserDao) ApplicationContextUtil.getApplicationContext().getBean("roleUserDao");
    }
    
}
