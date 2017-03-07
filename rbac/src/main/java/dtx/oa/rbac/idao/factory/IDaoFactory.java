/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao.factory;

import dtx.oa.rbac.dao.NodeDao;
import dtx.oa.rbac.dao.RoleDao;
import dtx.oa.rbac.dao.RoleNodeDao;
import dtx.oa.rbac.dao.RoleUserDao;
import dtx.oa.rbac.dao.UserDao;
import dtx.oa.rbac.idao.INodeDao;
import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.IRoleNodeDao;
import dtx.oa.rbac.idao.IRoleUserDao;
import dtx.oa.rbac.idao.IUserDao;

/**
 *
 * @author datouxi
 */
public class IDaoFactory {
    
    public static IUserDao iUserDao(){
        return new UserDao();
    }
    
    public static IRoleDao iRoleDao(){
        return new RoleDao();
    }
    
    public static INodeDao iNodeDao(){
        return new NodeDao();
    }
    
    public static IRoleNodeDao iRoleNodeDao(){
        return new RoleNodeDao();
    }
    
    public static IRoleUserDao iRoleUserDao(){
        return new RoleUserDao();
    }
    
}
