/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.app;

import dtx.oa.workflow.util.EntityUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author gg
 */
public class TestHibernate {
    public static void main(String[] args) {
        ApplicationContext context=EntityUtil.getContext();
        SessionFactory sessionFactory=(SessionFactory) context.getBean("sessionFactory");
        System.out.println("---------->>>"+sessionFactory.toString());
    }
}
