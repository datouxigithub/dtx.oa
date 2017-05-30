/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.NodeTree;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface INodeDao extends IBasicDao{
    public final static String ROOTID="";
    
    public Node getNodeById(String nodeId);
	
    public List<Node> getByStatus(boolean status);

    public List<Node> getByType(int nodeType);

    public List<Node> getChilds(String parentId);
    public List<Node> getChilds(Node parentNode);

    public List<Node> getChilds(String parentId,boolean status);
    public List<Node> getChilds(Node parentNode,boolean status);

    public List<Node> getChildsByType(String parentId,int nodeType);
    public List<Node> getChildsByType(Node parentNode,int nodeType);

    public List<Node> getChildsByType(String parentId,int nodeType,boolean status);
    public List<Node> getChildsByType(Node parentNode,int nodeType,boolean status);

    public NodeTree getAllChilds(String parentId);
    public NodeTree getAllChilds(Node parentNode);

    public NodeTree getAllChilds(String parentId,boolean status);
    public NodeTree getAllChilds(Node parentNode,boolean status);

    public NodeTree getAllNodes();

    public NodeTree getAllNodes(boolean status);

    public LinkedHashMap<Integer,String> getNodeTypes();

//    public String getParentId(String nodeId);

    //全面更新node
    public boolean updateNode(Node node);

    //只更新node的基本信息
    public boolean updateNodeMessage(Node node);

    public boolean updateParent(Node node);

    public boolean updateStatus(Node node);

    public boolean deleteNode(Node node);
    
    public boolean deleteNode(Node node,boolean isDeleteCascade);
	
    public String addNode(Node node);
}
