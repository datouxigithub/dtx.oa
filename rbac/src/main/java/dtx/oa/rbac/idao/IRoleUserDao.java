/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IRoleUserDao {
    public List<RoleUser> queryByUserId(String userId);

    public List<RoleUser> queryByRoleId(String roleId);
    
    public List<Role> getRoleByUser(User user);

    public RoleUser queryById(String id);

    public boolean deleteByUserId(String userId);

    public boolean deleteByRoleId(String roleId);

    public boolean deleteByUserId(RoleUser ru);

    public boolean deleteByRoleId(RoleUser ru);

    public boolean delete(String id);

    public boolean delete(RoleUser ru);

    //返回新插入的uuid
    public String addRoleUser(String user_id,String role_id);

    public String addRoleUser(RoleUser ru);

    public void addRoleUsers(String user_id,String[] roles);
}
