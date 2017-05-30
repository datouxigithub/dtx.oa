/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;

/**
 *
 * @author datouxi
 */
public class TestApp {
    public static void main(String[] args) {
        new TestApp().test();
        
    }
    
    public void test(){
        IRoleDao dao=IDaoFactory.iRoleDao();
        Role role1=new Role("", "qweruqiwep", null, null, true);
        dao.addRole(role1);
        Role role=new Role("uwqpru", "ddsd", null, role1, true);
        String result=dao.addRole(role);
        System.out.println("---------------->>>"+result);
    }
}
