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
import dtx.oa.util.StringUtil;
import java.sql.Timestamp;
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
    }

    @Override
    public List<User> getUsersByStatus(boolean status) {
        return executeQuery("FROM User user WHERE user.status=?", new Object[]{status});
    }

    @Override
    public String addUser(User user) {
        user.setPassword(StringUtil.getMD5String(user.getPassword()));
        return (String) add(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        if(update("DELETE FROM User user WHERE user.uuid=?", new Object[]{userId})>0)return IDaoFactory.iRoleUserDao().deleteByUserId(userId);
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return deleteUser(user.getUuid());
    }

    @Override
    public boolean updateUser(User user) {
        user.setPassword(StringUtil.getMD5String(user.getPassword()));
        return update("UPDATE User user SET user.password=?,user.status=?,user.remark=?,user.loginTime=?,user.loginIp=? WHERE user.uuid=?", 
                new Object[]{user.getPassword(),user.getStatus(),user.getRemark(),user.getLoginTime(),user.getLoginIp(),user.getUuid()})>0;
    }

    @Override
    public boolean updateLoginMessage(User user) {
        return update("UPDATE User user SET user.loginTime=?,user.loginIp=? WHERE user.uuid=?", 
                new Object[]{new Timestamp(System.currentTimeMillis()),user.getLoginIp(),user.getUuid()})>0;
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
