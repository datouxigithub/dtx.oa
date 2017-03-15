/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class TestApp {
    public static void main(String[] args) {
        ApplicationContext ac=ApplicationContextUtil.getApplicationContext();
        SessionFactory sf=(SessionFactory) ac.getBean("sessionFactory");
        Session session=sf.getCurrentSession();
        System.out.println(session.toString());
        
    }
}
