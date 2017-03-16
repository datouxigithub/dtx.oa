/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.INodeDao;
import dtx.oa.rbac.idao.IRoleNodeDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author datouxi
 */
@Transactional
public class RoleNodeDao extends BasicDao implements IRoleNodeDao {

    @Override
    public List<RoleNode> queryByNodeId(String nodeId) {
        return executeQuery("FROM RoleNode role_node WHERE role_node.nodeId=?", new Object[]{nodeId});
    }

    @Override
    public List<RoleNode> queryByRoleId(String roleId) {
        return executeQuery("FROM RoleNode role_node WHERE role_node.roleId=?", new Object[]{roleId});
    }

    @Override
    public List<Node> getNodesByRole(String roleId) {
        List<Node> nodes=new ArrayList<>();
        INodeDao nc=IDaoFactory.iNodeDao();
        Iterator<RoleNode> iter=queryByRoleId(roleId).iterator();
        while(iter.hasNext()){
            nodes.add(nc.getNodeById(((RoleNode)iter.next()).getNodeId()));
        }
        return nodes;
    }

    @Override
    public List<Node> getNodesByRole(Role role) {
        if(role==null)return new ArrayList<Node>();
        return getNodesByRole(role.getUuid());
    }

    @Override
    public List<Node> getNodesByRole(List<Role> roles) {
        List<Node> nodes=new ArrayList<>();
        for(Role role:roles){
            Iterator<Node> nodeIter=getNodesByRole(role).iterator();
            while(nodeIter.hasNext()){
                Node current=nodeIter.next();
                if(!nodes.contains(current))
                    nodes.add(current);
            }
        }
        return nodes;
    }

    @Override
    public RoleNode queryById(String id) {
        return (RoleNode) findById(RoleNode.class, id);
    }

    @Override
    public boolean deleteByNodeId(String nodeId) {
        return update("DELETE FROM RoleNode role_node WHERE role_node.nodeId=?", new Object[]{nodeId})>0;
    }

    @Override
    public boolean deleteByRoleId(String roleId) {
        return update("DELETE FROM RoleNode role_node WHERE role_node.roleId=?", new Object[]{roleId})>0;
    }

    @Override
    public boolean deleteByNodeId(RoleNode rn) {
        return deleteByNodeId(rn.getNodeId());
    }

    @Override
    public boolean deleteByRoleId(RoleNode rn) {
        return deleteByRoleId(rn.getRoleId());
    }

    @Override
    public boolean delete(String id) {
        return update("DELETE FROM RoleNode role_node WHERE role_node.uuid=?", new Object[]{id})>0;
    }

    @Override
    public boolean delete(RoleNode rn) {
        return delete(rn.getUuid());
    }

    @Override
    public String addRoleNode(String node_id, String role_id) {
        return addRoleNode(new RoleNode(null, role_id, node_id));
    }

    @Override
    public String addRoleNode(RoleNode rn) {
        return (String) add(rn);
    }
    
}
