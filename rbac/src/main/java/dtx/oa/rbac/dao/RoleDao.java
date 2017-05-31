/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.IRoleDao;
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
    public List<Role> getChilds(Role parentRole){
        return executeQuery("FROM Role role WHERE role.parentRole=?", new Object[]{parentRole});
    }
    
    @Override
    public List<Role> getChilds(Role parentRole,boolean status){
        return executeQuery("FROM Role role WHERE role.parentRole=? and role.status=?", new Object[]{parentRole,status});
    }
    
    @Override
    public RoleTree getAllChilds(Role parentRole){
        return new RoleTree(getChilds(parentRole));
    }

    @Override
    public RoleTree getAllRoles() {
        return new RoleTree(getRoots());
    }
    
    @Override
    public RoleTree getAllChilds(Role parentRole, boolean status) {
        return new RoleTree(getChilds(parentRole, status), status);
    }

    @Override
    public RoleTree getAllRoles(boolean status) {
        return new RoleTree(getRoots(status),status);
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
        return update("UPDATE Role role SET role.parentRole=? WHERE role.uuid=?", new Object[]{role.getParentRole(),role.getUuid()})>0;
    }

    @Override
    public boolean updateStatus(Role role) {
        return update("UPDATE Role role SET role.status=? WHERE role.uuid=?", new Object[]{role.getStatus(),role.getUuid()})>0;
    }

    @Override
    public boolean deleteRole(Role role) {
        return deleteRole(role, true);
    }
    
    @Override
    public boolean deleteRole(Role role,boolean isDeleteCascade){
        if(!isDeleteCascade&&(role.getRoleUsers().size()>0||role.getRoleNodes().size()>0))
            return false;
        else
            return delete(role);
    }

    @Override
    public String addRole(Role role) {
        return (String) add(role);
    }

    @Override
    public List<Role> getRoots() {
        return executeQuery("FROM Role role WHERE role.parentRole IS NULL", null);
    }

    @Override
    public List<Role> getRoots(boolean status) {
        return executeQuery("FROM Role role WHERE role.parentRole IS NULL AND role.status=?", new Object[]{status});
    }
    
}
