/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.rbac.idao.INodeDao;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.NodeTree;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author datouxi
 */
public class NodeDao extends BasicDao implements INodeDao {

    @Override
    public Node getNodeById(String nodeId) {
        return (Node) findById(Node.class, nodeId);
    }

    @Override
    public List<Node> getByStatus(boolean status) {
        return executeQuery("FROM Node node WHERE node.status=?", new Object[]{status});
    }

    @Override
    public List<Node> getByType(int nodeType) {
        return executeQuery("FROM Node node WHERE node.nodeType=?", new Object[]{nodeType});
    }

    @Override
    public List<Node> getChilds(String parentId) {
        return executeQuery("FROM Node node WHERE node.parentId=?", new Object[]{parentId});
    }
    
    @Override
    public List<Node> getChilds(Node parentNode) {
        return getChilds(parentNode.getUuid());
    }

    @Override
    public List<Node> getChilds(String parentId, boolean status) {
        return executeQuery("FROM Node node WHERE node.parentId=? AND node.status=?", new Object[]{parentId,status});
    }
    
    @Override
    public List<Node> getChilds(Node parentNode,boolean status) {
        return getChilds(parentNode.getUuid(),status);
    }

    @Override
    public List<Node> getChildsByType(String parentId, int nodeType) {
        return executeQuery("FROM Node node WHERE node.parentId=? AND node.nodeType=?", new Object[]{parentId,nodeType});
    }
    
    @Override
    public List<Node> getChildsByType(Node parentNode,int nodeType) {
        return getChildsByType(parentNode.getUuid(),nodeType);
    }

    @Override
    public List<Node> getChildsByType(String parentId, int nodeType, boolean status) {
        return executeQuery("FROM Node node WHERE node.parentId=? AND node.nodeType=? AND node.status=?", new Object[]{parentId,nodeType,status});
    }
    
    @Override
    public List<Node> getChildsByType(Node parentNode, int nodeType, boolean status) {
        return getChildsByType(parentNode, nodeType, status);
    }

    @Override
    public NodeTree getAllChilds(String parentId) {
        List<Node> childs=getChilds(parentId);
        return new NodeTree(childs.toArray(new Node[childs.size()]));
    }
    
    @Override
    public NodeTree getAllChilds(Node parentNode) {
        return getAllChilds(parentNode.getUuid());
    }

    @Override
    public NodeTree getAllChilds(String parentId, boolean status) {
        List<Node> nodes=getChilds(parentId, status);
        return new NodeTree(nodes.toArray(new Node[nodes.size()]), status);
    }
    
    @Override
    public NodeTree getAllChilds(Node parentNode,boolean status) {
        return getAllChilds(parentNode, status);
    }

    @Override
    public NodeTree getAllNodes() {
        return getAllChilds(INodeDao.ROOTID);
    }

    @Override
    public NodeTree getAllNodes(boolean status) {
        return getAllChilds(INodeDao.ROOTID, status);
    }

    @Override
    public LinkedHashMap<Integer, String> getNodeTypes() {
        LinkedHashMap<Integer,String> nodeTypes=new LinkedHashMap();
        nodeTypes.put(Node.NODETYPEGROUP, "节点组");
        nodeTypes.put(Node.NODETYPESIGLENODE, "独立节点");
        return nodeTypes;
    }

//    @Override
//    public String getParentId(String nodeId) {
//        Node node=getNodeById(nodeId);
//        return node==null ? null:node.getParentNode().getUuid();
//    }

    @Override
    public boolean updateNode(Node node) {
        return update(node);
    }

    @Override
    public boolean updateNodeMessage(Node node) {
        return update("UPDATE Node node SET node.title=?,node.address=?,node.remark=? WHERE node.uuid=?", 
                new Object[]{node.getTitle(),node.getAddress(),node.getRemark(),node.getUuid()})>0;
    }

    @Override
    public boolean updateParent(Node node) {
        return update("UPDATE Node node SET node.parentId=? WHERE node.uuid=?",new Object[]{node.getParentNode().getUuid(),node.getUuid()})>0;
    }

    @Override
    public boolean updateStatus(Node node) {
        return update("UPDATE Node node SET node.status=? WHERE node.uuid=?", new Object[]{node.getStatus(),node.getUuid()})>0;
    }

    @Override
    public boolean deleteNode(Node node) {
        return deleteNode(node, true);
    }
    
    @Override
    public boolean deleteNode(Node node,boolean isDeleteCascade){
        if(!isDeleteCascade&&node.getRoles().size()>0)
            return false;
        else
            return delete(node);
    }

    @Override
    public String addNode(Node node) {
        return (String) add(node);
    }
    
}
