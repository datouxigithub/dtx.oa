/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.IRoleUserDao;
import dtx.oa.rbac.idao.IUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;

/**
 *
 * @author datouxi
 */
public class TestRBACData {
    
    private User userDTX,userFM,userCSY;
    private Role roleD,roleM,roleS;
    private RoleUser ru1,ru2,ru3;
    
    public void initUsers(){
        userDTX=new User(null, "大头希", "123");
        userFM=new User(null, "肥满", "456");
        userCSY=new User(null, "臭屎洋", "789");
        IUserDao dao=IDaoFactory.iUserDao();
        dao.addUser(userDTX);
        dao.addUser(userFM);
        dao.addUser(userCSY);
    }
    
    public void initRoles(){
        roleD=new Role(null, "爸爸", null, null, true);
        roleM=new Role(null, "妈妈", null, roleD, true);
        roleS=new Role(null, "儿子", null, roleM, true);
        IRoleDao dao=IDaoFactory.iRoleDao();
        dao.add(roleD);
        dao.add(roleM);
        dao.add(roleS);
    }
    
    public void initRoleUsers(){
        ru1=new RoleUser(null, userDTX, roleD);
        ru2=new RoleUser(null, userFM, roleM);
        ru3=new RoleUser(null, userCSY, roleS);
        IRoleUserDao dao=IDaoFactory.iRoleUserDao();
        dao.add(ru1);
        dao.add(ru2);
        dao.add(ru3);
    }
    
    public static void main(String[] args) {
        TestRBACData rbac=new TestRBACData();
        rbac.initUsers();
        rbac.initRoles();
        rbac.initRoleUsers();
    }
}
