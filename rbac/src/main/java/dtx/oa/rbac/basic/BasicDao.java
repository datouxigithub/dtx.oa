/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.basic;

import dtx.oa.util.LogUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public abstract class BasicDao implements IBasicDao{

    protected SessionFactory sessionFactory;
    
    @Override
    public Object findById(Class clazz,Serializable id) {
        return SessionFactoryUtils.getSession(sessionFactory, false).get(clazz, id);
    }

    @Override
    public List executeQuery(String hql, Object[] args) {
        Query query=SessionFactoryUtils.getSession(sessionFactory, false).createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.list();
    }

    @Override
    public List executeQuery(String hql, Object[] args, int pageNow, int pageSize) {
        Query query=SessionFactoryUtils.getSession(sessionFactory, false).createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.setFirstResult((pageNow-1)*pageSize).setMaxResults(pageSize).list();
    }

    @Override
    public Serializable add(Object obj) {
        return add(obj, true);
    }
    
    @Override
    public Serializable add(Object obj,boolean isRollback) {
        Session session=SessionFactoryUtils.getSession(sessionFactory, false);
        try{
            return session.save(obj);
        }catch(HibernateException e){
            if(isRollback&&session.getTransaction()!=null)
                session.getTransaction().rollback();
            throw e;
        }
    }
    
    @Override 
    public void add(List objs){
        add(objs, true);
    }

    @Override
    public int update(String hql, Object[] args) {
        return update(hql, args, true);
    }

    @Override
    public Object uniqueQuery(String hql, Object[] args) {
        Query query=SessionFactoryUtils.getSession(sessionFactory, false).createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.uniqueResult();
    }

    @Override
    public boolean update(Object obj) {
        return update(obj, true);
    }
    
    @Override
    public boolean update(Object obj,boolean isRollback) {
        Session session=SessionFactoryUtils.getSession(sessionFactory, false);
        try{
            session.update(obj);
            return true;
        }catch(HibernateException e){
            if(isRollback&&session.getTransaction()!=null)
                session.getTransaction().rollback();
            LogUtil.getLogger().error(e);
            return false;
        }
    }
    
    @Override
    public boolean delete(Object obj){
        return delete(obj, true);
    }
    
    @Override
    public boolean delete(Object obj,boolean isRollback){
        Session session=SessionFactoryUtils.getSession(sessionFactory, false);
        try{
            session.delete(obj);
            return true;
        }catch(HibernateException ex){
            if(isRollback&&session.getTransaction()!=null)
                session.getTransaction().rollback();
            LogUtil.getLogger().error(ex);
            return false;
        }
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(List objs, boolean isRollback) {
        Session session=SessionFactoryUtils.getSession(sessionFactory, false);
        for(Object obj:objs){
            try{
                session.persist(obj);
            }catch(HibernateException e){
                if(isRollback&&session.getTransaction()!=null)
                    session.getTransaction().rollback();
                LogUtil.getLogger().error(e);
                return;
            }
        }
    }

    @Override
    public int update(String hql, Object[] args, boolean isRollback) {
        Session session=SessionFactoryUtils.getSession(sessionFactory, false);
        Query query=session.createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        try{
            return query.executeUpdate();
        }catch(HibernateException e){
            if(isRollback&&session.getTransaction()!=null)
                session.getTransaction().rollback();
            LogUtil.getLogger().error(e);
            return 0;
        }
        
    }
    
}
