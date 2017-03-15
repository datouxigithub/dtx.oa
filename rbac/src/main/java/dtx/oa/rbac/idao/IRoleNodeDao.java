/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.basic.BasicDaoInter;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleNode;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IRoleNodeDao extends BasicDaoInter{
    public List<RoleNode> queryByNodeId(String nodeId);

    public List<RoleNode> queryByRoleId(String roleId);
    
    public List<Node> getNodesByRole(String roleId);
    
    public List<Node> getNodesByRole(Role role);
    
    public List<Node> getNodesByRole(List<Role> roles);

    public RoleNode queryById(String id);

    public boolean deleteByNodeId(String nodeId);

    public boolean deleteByRoleId(String roleId);

    public boolean deleteByNodeId(RoleNode rn);

    public boolean deleteByRoleId(RoleNode rn);

    public boolean delete(String id);

    public boolean delete(RoleNode rn);

    public String addRoleNode(String node_id,String role_id);

    public String addRoleNode(RoleNode rn);
}
