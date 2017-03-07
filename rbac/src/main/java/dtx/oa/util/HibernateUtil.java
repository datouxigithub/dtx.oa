/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author datouxi
 */
public class HibernateUtil {
    private static SessionFactory factory;
    static{
        try{
            factory=new Configuration().configure().buildSessionFactory();
        }catch(HibernateException e){
            LogUtil.getLogger().error(e);
        }
        
    }
    
    public static SessionFactory getSessionFactory(){
        return factory;
    }
    
    public static Session getSession(){
        return factory.getCurrentSession();
    }
    
    public static void closeSession(Session session){
        if(session!=null)session.close();
    }
}
