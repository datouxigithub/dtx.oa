/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleTree;
import dtx.oa.util.HibernateUtil;
import dtx.oa.util.LogUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author datouxi
 */
public class RoleDao implements IRoleDao {

    @Override
    public Role getRoleById(String roleId) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Role role=(Role) session.get(Role.class, roleId);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return role;
    }

    @Override
    public List<Role> getByStatus(boolean status) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Role role WHERE role.status=:status");
        query.setBoolean("status", status);
        List<Role> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Role> getChilds(String parentId) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Role role WHERE role.parentId=:parent_id");
        query.setString("parent_id", parentId);
        List<Role> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Role> getChilds(String parentId, boolean status) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Role role WHERE role.parentId=:parent_id and role.status=:status");
        query.setString("parent_id", parentId);
        query.setBoolean("status", status);
        List<Role> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public RoleTree getAllChilds(String parentId) {
        return new RoleTree(getChilds(parentId));
    }

    @Override
    public RoleTree getAllRoles() {
        return getAllChilds(IRoleDao.ROOTID);
    }

    @Override
    public RoleTree getAllChilds(String parentid, boolean status) {
        return new RoleTree(getChilds(parentid, status), status);
    }

    @Override
    public RoleTree getAllRoles(boolean status) {
        return getAllChilds(IRoleDao.ROOTID, status);
    }

    @Override
    public boolean updateRole(Role role) {
        boolean result=true;
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        try{
            session.update(role);
        }catch(HibernateException e){
            LogUtil.getLogger().error(e);
            result=false;
        }
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public boolean updateRoleMessage(Role role) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE Role role SET role.roleName=:role_name,role.remark=:remark WHERE role.uuid=:id");
        query.setString("role_name", role.getRoleName());
        query.setString("remark", role.getRemark());
        query.setString("id", role.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean updateParent(Role role) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE Role role SET role.parentId=:parent_id WHERE role.uuid=:id");
        query.setString("parent_id", role.getParentId());
        query.setString("id", role.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean updateStatus(Role role) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE Role role SET role.status=:status WHERE role.uuid=:id");
        query.setBoolean("status", role.getStatus());
        query.setString("id", role.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean deleteRole(Role role) {
        return deleteRole(role.getUuid());
    }

    @Override
    public boolean deleteRole(String id) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("DELETE FROM Role role WHERE role.uuid=:id");
        query.setString("id", id);
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        if(result<=0)return false;
        return IDaoFactory.iRoleNodeDao().deleteByRoleId(id)&&IDaoFactory.iRoleUserDao().deleteByRoleId(id);
    }

    @Override
    public String addRole(Role role) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        String result=(String) session.save(role);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }
    
}
