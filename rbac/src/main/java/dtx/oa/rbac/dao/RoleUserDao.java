/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.IRoleUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class RoleUserDao extends BasicDao implements IRoleUserDao {

    @Override
    public List<RoleUser> queryByUser(User user) {
        return executeQuery("FROM RoleUser role_user WHERE role_user.user=?", new Object[]{user});
    }

    @Override
    public List<RoleUser> queryByRole(Role role) {
        return executeQuery("FROM RoleUser role_user WHERE role_user.role=?", new Object[]{role});
    }

    @Override
    public List<Role> getRoleByUser(User user) {
        if(IDaoFactory.iUserDao().isAdmin(user))
            return IDaoFactory.iRoleDao().getRoots();
        else{
            List<Role> roles=new ArrayList<>();
            Iterator<RoleUser> iter=user.getRoleUsers().iterator();
            while(iter.hasNext()){
                roles.add(iter.next().getRole());
            }
            return roles;
        }
    }

    @Override
    public RoleUser queryById(String id) {
        return (RoleUser) findById(RoleUser.class, id);
    }

    @Override
    public boolean deleteByUser(User user) {
        return update("DELETE FROM RoleUser role_user WHERE role_user.user=?", new Object[]{user})>0;
    }

    @Override
    public boolean deleteByRole(Role role) {
        return update("DELETE FROM RoleUser role_user WHERE role_user.role=?", new Object[]{role})>0;
    }

    @Override
    public boolean deleteByUser(RoleUser ru) {
        return deleteByUser(ru.getUser());
    }

    @Override
    public boolean deleteByRole(RoleUser ru) {
        return deleteByRole(ru.getRole());
    }

    @Override
    public boolean deleteRoleUser(RoleUser ru) {
        return delete(ru);
    }

    @Override
    public RoleUser addRoleUser(User user, Role role) {
        return addRoleUser(new RoleUser(null, user, role));
        
    }

    @Override
    public RoleUser addRoleUser(RoleUser ru) {
        return add(ru)==null ? null:ru;
    }

    @Override
    public void addRoleUsers(User user, List<Role> roles) {
        List<RoleUser> rus=new ArrayList<>();
        for(Role role:roles)
            rus.add(new RoleUser(null, user, role));
        add(rus);
    }
    
}
