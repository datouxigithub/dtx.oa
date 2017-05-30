/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleNode;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IRoleNodeDao extends IBasicDao{
    public List<RoleNode> queryByNode(Node node);

    public List<RoleNode> queryByRole(Role role);
    
    public List<Node> getNodesByRole(Role role);
    
    public List<Node> getNodesByRole(List<Role> roles);

    public RoleNode queryById(String id);

    public boolean deleteByNode(Node node);

    public boolean deleteByRole(Role role);

    public boolean deleteByNode(RoleNode rn);

    public boolean deleteByRole(RoleNode rn);

    public boolean deleteRoleNode(RoleNode rn);

    public RoleNode addRoleNode(Node node,Role role);

    public RoleNode addRoleNode(RoleNode rn);
}
