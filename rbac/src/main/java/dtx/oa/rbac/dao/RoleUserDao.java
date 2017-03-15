/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.IRoleUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class RoleUserDao extends BasicDao implements IRoleUserDao {

    @Override
    public List<RoleUser> queryByUserId(String userId) {
        return executeQuery("FROM RoleUser role_user WHERE role_user.userId=?", new Object[]{userId});
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("FROM RoleUser role_user WHERE role_user.userId=:user_id");
//        query.setString("user_id", userId);
//        @SuppressWarnings("unchecked")
//        List<RoleUser> result=query.list();
//        session.getTransaction().commit();
//        return result;
    }

    @Override
    public List<RoleUser> queryByRoleId(String roleId) {
        return executeQuery("FROM RoleUser role_user WHERE role_user.roleId=?", new Object[]{roleId});
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("FROM RoleUser role_user WHERE role_user.roleId=:role_id");
//        query.setString("role_id", roleId);
//        List<RoleUser> result=query.list();
//        session.getTransaction().commit();
//        return result;
    }

    @Override
    public List<Role> getRoleByUser(User user) {
        List<Role> result=new ArrayList<>();
        if(IDaoFactory.iUserDao().isAdmin(user)){
            result=IDaoFactory.iRoleDao().getChilds(IRoleDao.ROOTID);
        }else{
            List<RoleUser> rus=queryByUserId(user.getUuid());
            IRoleDao irDao=IDaoFactory.iRoleDao();
            for(RoleUser ru:rus){
                result.add(irDao.getRoleById(ru.getRoleId()));
            }
        }
        return result;
    }

    @Override
    public RoleUser queryById(String id) {
        return (RoleUser) findById(RoleUser.class, id);
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        RoleUser rn=(RoleUser)session.get(RoleUser.class, id);
//        session.getTransaction().commit();
//        return rn;
    }

    @Override
    public boolean deleteByUserId(String userId) {
        return update("DELETE FROM RoleUser role_user WHERE role_user.userId=?", new Object[]{userId})>0;
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("DELETE FROM RoleUser role_user WHERE role_user.userId=:user_id");
//        query.setString("user_id", userId);
//        int result=query.executeUpdate();
//        session.getTransaction().commit();
//        return result>0;
    }

    @Override
    public boolean deleteByRoleId(String roleId) {
        return update("DELETE FROM RoleUser role_user WHERE role_user.roleId=?", new Object[]{roleId})>0;
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("DELETE FROM RoleUser role_user WHERE role_user.roleId=:role_id");
//        query.setString("role_id", roleId);
//        int result=query.executeUpdate();
//        session.getTransaction().commit();
//        return result>0;
    }

    @Override
    public boolean deleteByUserId(RoleUser ru) {
        return deleteByUserId(ru.getUserId());
    }

    @Override
    public boolean deleteByRoleId(RoleUser ru) {
        return deleteByRoleId(ru.getRoleId());
    }

    @Override
    public boolean delete(String id) {
        return update("DELETE FROM RoleUser role_user WHERE role_user.uuid=?", new Object[]{id})>0;
        
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        Query query=session.createQuery("DELETE FROM RoleUser role_user WHERE role_user.uuid=:id");
//        query.setString("id", id);
//        int result=query.executeUpdate();
//        session.getTransaction().commit();
//        return result>0;
    }

    @Override
    public boolean delete(RoleUser ru) {
        return delete(ru.getUuid());
    }

    @Override
    public String addRoleUser(String user_id, String role_id) {
        return addRoleUser(new RoleUser(null, user_id, role_id));
//        Session session=HibernateUtil.getSession();
//        session.beginTransaction();
//        RoleUser ru=new RoleUser();
//        ru.setUserId(user_id);
//        ru.setRoleId(role_id);
//        String result=(String) session.save(ru);
//        session.getTransaction().commit();
//        return result;
    }

    @Override
    public String addRoleUser(RoleUser ru) {
        return (String) add(ru);
//        return addRoleUser(ru.getUserId(), ru.getRoleId());
    }

    @Override
    public void addRoleUsers(String user_id, String[] roles) {
        for(String role:roles)
            if(addRoleUser(user_id, role)==null)return;
    }
    
}
