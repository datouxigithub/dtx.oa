/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleTree;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IRoleDao extends IBasicDao{
    public final static String ROOTID="";
    
    public Role getRoleById(String roleId);
	
    public List<Role> getByStatus(boolean status);

    public List<Role> getChilds(Role parentRole);

    public List<Role> getChilds(Role parentRole,boolean status);

    public RoleTree getAllChilds(Role parentRole);

    public List<Role> getRoots();
    
    public List<Role> getRoots(boolean status);
    
    public RoleTree getAllRoles();

    public RoleTree getAllChilds(Role parentRole,boolean status);

    public RoleTree getAllRoles(boolean status);

    //全面更新role
    public boolean updateRole(Role role);

    //只更新role的基本信息
    public boolean updateRoleMessage(Role role);

    public boolean updateParent(Role role);

    public boolean updateStatus(Role role);

    public boolean deleteRole(Role role);
    
    public boolean deleteRole(Role role,boolean isDeleteCascade);
	
    public String addRole(Role role);
}
