/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IRoleUserDao extends IBasicDao{
    public List<RoleUser> queryByUser(User user);

    public List<RoleUser> queryByRole(Role role);
    
    public List<Role> getRoleByUser(User user);

    public RoleUser queryById(String id);

    public boolean deleteByUser(User user);

    public boolean deleteByRole(Role role);

    public boolean deleteByUser(RoleUser ru);

    public boolean deleteByRole(RoleUser ru);

    public boolean deleteRoleUser(RoleUser ru);

    //返回新插入的uuid
    public RoleUser addRoleUser(User user,Role role);

    public RoleUser addRoleUser(RoleUser ru);

    public void addRoleUsers(User user,List<Role> roles);
}
