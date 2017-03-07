/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleTree;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IRoleDao {
    public final static String ROOTID="";
    
    public Role getRoleById(String roleId);
	
    public List<Role> getByStatus(boolean status);

    public List<Role> getChilds(String parentId);

    public List<Role> getChilds(String parentId,boolean status);

    public RoleTree getAllChilds(String parentId);

    public RoleTree getAllRoles();

    public RoleTree getAllChilds(String parentid,boolean status);

    public RoleTree getAllRoles(boolean status);

    //全面更新role
    public boolean updateRole(Role role);

    //只更新role的基本信息
    public boolean updateRoleMessage(Role role);

    public boolean updateParent(Role role);

    public boolean updateStatus(Role role);

    public boolean deleteRole(Role role);

    public boolean deleteRole(String id);
	
    public String addRole(Role role);
}
