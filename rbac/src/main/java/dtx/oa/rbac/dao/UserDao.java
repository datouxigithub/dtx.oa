/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.IUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class UserDao extends BasicDao implements IUserDao{

    @Override
    public User getUserById(String userId) {
        return (User) findById(User.class, userId);
    }

    @Override
    public User getUserByAccount(String account) {
        return (User) uniqueQuery("FROM User user WHERE user.account=?", new Object[]{account});
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("FROM User user WHERE user.account=:account");
//        query.setString("account", account);
//        User user=(User) query.uniqueResult();
//        session.getTransaction().commit();
//        return user;
        
        
    }

    @Override
    public List<User> getUsersByStatus(boolean status) {
        return executeQuery("FROM User user WHERE user.status=?", new Object[]{status});
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("FROM User user WHERE user.status=:status");
//        query.setBoolean("status", status);
//        List<User> result=query.list();
//        session.getTransaction().commit();
//        return result;
    }

    @Override
    public String addUser(User user) {
        return (String) add(user);
//        user.setPassword(StringUtil.getMD5String(user.getPassword()));
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        String result=(String) session.save(user);
//        session.getTransaction().commit();
//        return result;
    }

    @Override
    public boolean deleteUser(String userId) {
        if(update("FROM User user WHERE user.uuid=?", new Object[]{userId})>0)return IDaoFactory.iRoleUserDao().deleteByUserId(userId);
        return false;
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("DELETE FROM User user WHERE user.uuid=:id");
//        query.setString("id", userId);
//        int result=query.executeUpdate();
//        session.getTransaction().commit();
//        if(result<=0)return false;
//        return IDaoFactory.iRoleUserDao().deleteByUserId(userId);
    }

    @Override
    public boolean deleteUser(User user) {
        return deleteUser(user.getUuid());
    }

    @Override
    public boolean updateUser(User user) {
        return update("UPDATE User user SET user.password=?,user.status=?,user.remark=?,user.loginTime=?,user.loginIp=? WHERE user.uuid=?", 
                new Object[]{user.getPassword(),user.getStatus(),user.getRemark(),user.getLoginTime(),user.getLoginIp(),user.getUuid()})>0;
        
//        user.setPassword(StringUtil.getMD5String(user.getPassword()));
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("UPDATE User user SET user.password=:pwd,user.status=:status,user.remark=:remark,user.loginTime=:login_time,user.loginIp=:login_ip WHERE user.uuid=:id");
//        query.setString("pwd", user.getPassword());
//        query.setBoolean("status", user.getStatus());
//        query.setString("remark", user.getRemark());
//        query.setString("id", user.getUuid());
//        query.setString("login_ip", user.getLoginIp());
//        query.setTimestamp("login_time", user.getLoginTime());
//        int result=query.executeUpdate();
//        session.getTransaction().commit();
//        return result>0;
    }

    @Override
    public boolean updateLoginMessage(User user) {
        return update("UPDATE User user SET user.loginTime=?,user.loginIp=? WHERE user.uuid=?", 
                new Object[]{user.getLoginTime(),user.getLoginIp(),user.getUuid()})>0;
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("UPDATE User user SET user.loginTime=:login_time,user.loginIp=:login_ip WHERE user.uuid=:id");
//        query.setTimestamp("login_time", new Timestamp(System.currentTimeMillis()));
//        query.setString("login_ip", user.getLoginIp());
//        query.setString("id", user.getUuid());
//        int result=query.executeUpdate();
//        session.getTransaction().commit();
//        return result>0;
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
