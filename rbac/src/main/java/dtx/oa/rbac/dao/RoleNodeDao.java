/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.IRoleNodeDao;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class RoleNodeDao extends BasicDao implements IRoleNodeDao {

    @Override
    public List<RoleNode> queryByNode(Node node) {
        return executeQuery("FROM RoleNode role_node WHERE role_node.node=?", new Object[]{node});
    }

    @Override
    public List<RoleNode> queryByRole(Role role) {
        return executeQuery("FROM RoleNode role_node WHERE role_node.role=?", new Object[]{role});
    }

    @Override
    public List<Node> getNodesByRole(Role role) {
        Iterator<RoleNode> iter=role.getRoleNodes().iterator();
        List<Node> nodes=new ArrayList<>();
        while(iter.hasNext())
            nodes.add(iter.next().getNode());
        return nodes;
    }

    @Override
    public List<Node> getNodesByRole(List<Role> roles) {
        Set<Node> nodeSet=new HashSet<Node>();
        for(Role role:roles)
            nodeSet.addAll(getNodesByRole(role));
        return new ArrayList<>(nodeSet);
    }

    @Override
    public RoleNode queryById(String id) {
        return (RoleNode) findById(RoleNode.class, id);
    }

    @Override
    public boolean deleteByNode(Node node) {
        return update("DELETE FROM RoleNode role_node WHERE role_node.node=?", new Object[]{node})>0;
    }

    @Override
    public boolean deleteByRole(Role role) {
        return update("DELETE FROM RoleNode role_node WHERE role_node.role=?", new Object[]{role})>0;
    }

    @Override
    public boolean deleteByNode(RoleNode rn) {
        return deleteByNode(rn.getNode());
    }

    @Override
    public boolean deleteByRole(RoleNode rn) {
        return deleteByRole(rn.getRole());
    }

    @Override
    public boolean deleteRoleNode(RoleNode rn) {
        return delete(rn);
    }

    @Override
    public RoleNode addRoleNode(Node node,Role role) {
        return addRoleNode(new RoleNode(null, role, node));
    }

    @Override
    public RoleNode addRoleNode(RoleNode rn) {
        return add(rn)==null ? null:rn;
    }
    
}
