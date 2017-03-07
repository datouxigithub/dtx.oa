/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.idao.INodeDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.NodeTree;
import dtx.oa.util.HibernateUtil;
import dtx.oa.util.LogUtil;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author datouxi
 */
public class NodeDao implements INodeDao {

    @Override
    public Node getNodeById(String nodeId) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Node node=(Node) session.get(Node.class, nodeId);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return node;
    }

    @Override
    public List<Node> getByStatus(boolean status) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Node node WHERE node.status=:status");
        query.setBoolean("status", status);
        List<Node> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Node> getByType(int nodeType) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Node node WHERE node.nodeType=:type");
        query.setInteger("type", nodeType);
        List<Node> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Node> getChilds(String parentId) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Node node WHERE node.parentId=:parent_id");
        query.setString("parent_id", parentId);
        List<Node> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Node> getChilds(String parentId, boolean status) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Node node WHERE node.parentId=:parent_id AND status=:status");
        query.setString("parent_id", parentId);
        query.setBoolean("status", status);
        List<Node> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Node> getChildsByType(String parentId, int nodeType) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Node node WHERE node.parentId=:parent_id AND node_type=:node_type");
        query.setString("parent_id", parentId);
        query.setInteger("node_type", nodeType);
        List<Node> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public List<Node> getChildsByType(String parentId, int nodeType, boolean status) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Node node WHERE node.parentId=:parent_id AND node_type=:node_type AND status=:status");
        query.setString("parent_id", parentId);
        query.setInteger("node_type", nodeType);
        query.setBoolean("status", status);
        List<Node> result=query.list();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public NodeTree getAllChilds(String parentId) {
        List<Node> childs=getChilds(parentId);
        return new NodeTree(childs.toArray(new Node[childs.size()]));
    }

    @Override
    public NodeTree getAllChilds(String parentId, boolean status) {
        List<Node> nodes=getChilds(parentId, status);
        return new NodeTree(nodes.toArray(new Node[nodes.size()]), status);
    }

    @Override
    public NodeTree getAllChildsByType(String parentId, int nodeType) {
        List<Node> nodes=getChildsByType(parentId, nodeType);
        return new NodeTree(nodes.toArray(new Node[nodes.size()]), nodeType);
    }

    @Override
    public NodeTree getAllChildsByType(String parentId, int nodeType, boolean status) {
        List<Node> nodes=getChildsByType(parentId, nodeType, status);
        return new NodeTree(nodes.toArray(new Node[nodes.size()]), status, nodeType);
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
    public NodeTree getAllNodesByType(int nodeType) {
        return getAllChildsByType(INodeDao.ROOTID, nodeType);
    }

    @Override
    public NodeTree getAllNodesByType(int nodeType, boolean status) {
        return getAllChildsByType(INodeDao.ROOTID, nodeType, status);
    }

    @Override
    public LinkedHashMap<Integer, String> getNodeTypes() {
        LinkedHashMap<Integer,String> nodeTypes=new LinkedHashMap();
        nodeTypes.put(Node.NODETYPEGROUP, "节点组");
        nodeTypes.put(Node.NODETYPESIGLENODE, "独立节点");
        return nodeTypes;
    }

    @Override
    public String getParentId(String nodeId) {
        Node node=getNodeById(nodeId);
        return node==null ? null:node.getParentId();
    }

    @Override
    public boolean updateNode(Node node) {
        boolean result=true;
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        try{
            session.update(node);
        }catch(HibernateException e){
            LogUtil.getLogger().error(e);
            result=false;
        }
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }

    @Override
    public boolean updateNodeMessage(Node node) {
        if(node.getTitle()==null||"".equals(node.getTitle()))return false;
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE Node node SET node.title=:title,node.address=:address,node.remark=:remark WHERE node.uuid=:id");
        query.setString("title", node.getTitle());
        query.setString("address", node.getAddress());
        query.setString("remark", node.getRemark());
        query.setString("id", node.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean updateParent(Node node) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE Node node SET node.parentId=:parent_id WHERE node.uuid=:id");
        query.setString("parent_id", node.getParentId());
        query.setString("id", node.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean updateStatus(Node node) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("UPDATE Node node SET node.status=:status WHERE node.uuid=:id");
        query.setBoolean("status", node.getStatus());
        query.setString("id", node.getUuid());
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result>0;
    }

    @Override
    public boolean delete(String id) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query=session.createQuery("DELETE FROM Node node WHERE node.uuid=:id");
        query.setString("id", id);
        int result=query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        if(result<=0)return false;
        return IDaoFactory.iRoleNodeDao().deleteByNodeId(id);
    }

    @Override
    public boolean delete(Node node) {
        return delete(node.getUuid());
    }

    @Override
    public String addNode(Node node) {
        if(node.getTitle()==null||INodeDao.ROOTID.equals(node.getTitle()))return null;
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        String result=(String) session.save(node);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return result;
    }
    
}
