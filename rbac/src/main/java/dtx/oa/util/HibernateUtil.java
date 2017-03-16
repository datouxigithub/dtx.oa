/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
public class HibernateUtil {
    private SessionFactory factory;
    
    public void setSessionFactory(SessionFactory sf){
        factory=sf;
    }
    
    public SessionFactory getSessionFactory(){
        return factory;
    }
    
    @Transactional
    public Session getSession(){
        return factory.getCurrentSession();
    }
    
    public void closeSession(Session session){
        if(session!=null)session.close();
    }
    
    public static HibernateUtil getInstance(){
        return (HibernateUtil) ApplicationContextUtil.getApplicationContext().getBean("hibernateUtil");
    }
}
