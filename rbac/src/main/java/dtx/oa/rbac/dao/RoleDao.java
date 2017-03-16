/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleTree;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class RoleDao extends BasicDao implements IRoleDao {

    @Override
    public Role getRoleById(String roleId) {
        return (Role) findById(Role.class, roleId);
    }

    @Override
    public List<Role> getByStatus(boolean status) {
        return executeQuery("FROM Role role WHERE role.status=?", new Object[]{status});
    }

    @Override
    public List<Role> getChilds(String parentId) {
        return executeQuery("FROM Role role WHERE role.parentId=?", new Object[]{parentId});
    }

    @Override
    public List<Role> getChilds(String parentId, boolean status) {
        return executeQuery("FROM Role role WHERE role.parentId=? and role.status=?", new Object[]{parentId,status});
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
        return update(role);
    }

    @Override
    public boolean updateRoleMessage(Role role) {
        return update("UPDATE Role role SET role.roleName=?,role.remark=? WHERE role.uuid=?", new Object[]{role.getRoleName(),role.getRemark(),role.getUuid()})>0;
    }

    @Override
    public boolean updateParent(Role role) {
        return update("UPDATE Role role SET role.parentId=? WHERE role.uuid=?", new Object[]{role.getParentId(),role.getUuid()})>0;
    }

    @Override
    public boolean updateStatus(Role role) {
        return update("UPDATE Role role SET role.status=? WHERE role.uuid=?", new Object[]{role.getStatus(),role.getUuid()})>0;
    }

    @Override
    public boolean deleteRole(Role role) {
        return deleteRole(role.getUuid());
    }

    @Override
    public boolean deleteRole(String id) {
        if(update("DELETE FROM Role role WHERE role.uuid=?", new Object[]{id})>0)return IDaoFactory.iRoleNodeDao().deleteByRoleId(id)&&IDaoFactory.iRoleUserDao().deleteByRoleId(id);
        return false;
    }

    @Override
    public String addRole(Role role) {
        return (String) add(role);
    }
    
}
