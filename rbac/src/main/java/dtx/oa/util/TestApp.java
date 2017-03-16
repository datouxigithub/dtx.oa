/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import dtx.oa.rbac.idao.IUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.User;

/**
 *
 * @author datouxi
 */
public class TestApp {
    public static void main(String[] args) {
        new TestApp().test();
        
    }
    
    public void test(){
        IUserDao iud=IDaoFactory.iUserDao();
        User user=iud.getUserByAccount("大头希");
        if(user!=null)
            System.out.println(user.getUuid());
    }
}
