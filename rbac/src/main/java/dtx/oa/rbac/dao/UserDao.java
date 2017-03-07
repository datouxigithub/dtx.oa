/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.idao.IUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.User;
import dtx.oa.util.HibernateUtil;
import dtx.oa.util.StringUtil;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author datouxi
 */
public class UserDao implements IUserDao{

    @Override
    public User getUserById(String userId) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        User user=(User) session.get(User.class, userId);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return user;
    }

    @Override
    public User getUserByAccount(String account) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM User user WHERE user.account=:account");
        query.setString("account", account);
        User user=(User) query.uniqueResult();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return user;
    }

    @Override
    public List<User> getUsersByStatus(boolean status) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM User user WHERE user.status=:status");
        query.setBoolean("status", status);
        List<User> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public String addUser(User user) {
        user.setPassword(StringUtil.getMD5String(user.getPassword()));
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        String result=(String) session.save(user);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public boolean deleteUser(String userId) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("DELETE FROM User user WHERE user.uuid=:id");
        query.setString("id", userId);
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        if(result<=0)return false;
        return IDaoFactory.iRoleUserDao().deleteByUserId(userId);
    }

    @Override
    public boolean deleteUser(User user) {
        return deleteUser(user.getUuid());
    }

    @Override
    public boolean updateUser(User user) {
        user.setPassword(StringUtil.getMD5String(user.getPassword()));
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE User user SET user.password=:pwd,user.status=:status,user.remark=:remark,user.loginTime=:login_time,user.loginIp=:login_ip WHERE user.uuid=:id");
        query.setString("pwd", user.getPassword());
        query.setBoolean("status", user.getStatus());
        query.setString("remark", user.getRemark());
        query.setString("id", user.getUuid());
        query.setString("login_ip", user.getLoginIp());
        query.setTimestamp("login_time", user.getLoginTime());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean updateLoginMessage(User user) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE User user SET user.loginTime=:login_time,user.loginIp=:login_ip WHERE user.uuid=:id");
        query.setTimestamp("login_time", new Timestamp(System.currentTimeMillis()));
        query.setString("login_ip", user.getLoginIp());
        query.setString("id", user.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean isAdmin(String account) {
        final String ADMINNAME="admin";
        return ADMINNAME.equals(account);
    }

    @Override
    public boolean isAdmin(User user) {
        return isAdmin(user.getAccount());
    }
    
}
