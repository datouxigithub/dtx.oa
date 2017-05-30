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
import org.hibernate.SessionFactory;
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
        return sessionFactory.getCurrentSession().get(clazz, id);
    }

    @Override
    public List executeQuery(String hql, Object[] args) {
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.list();
    }

    @Override
    public List executeQuery(String hql, Object[] args, int pageNow, int pageSize) {
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.setFirstResult((pageNow-1)*pageSize).setMaxResults(pageSize).list();
    }

    @Override
    public Serializable add(Object obj) {
        return sessionFactory.getCurrentSession().save(obj);
    }

    @Override
    public int update(String hql, Object[] args) {
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.executeUpdate();
    }

    @Override
    public Object uniqueQuery(String hql, Object[] args) {
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        if(args!=null&&args.length>0){
            for(int i=0,len=args.length;i<len;i++){
                query.setParameter(i, args[i]);
            }
        }
        return query.uniqueResult();
    }

    @Override
    public boolean update(Object obj) {
        try{
            sessionFactory.getCurrentSession().update(obj);
            return true;
        }catch(HibernateException e){
            LogUtil.getLogger().error(e);
            return false;
        }
    }
    
    @Override
    public boolean delete(Object obj){
        sessionFactory.getCurrentSession().delete(obj);
        return true;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
}
