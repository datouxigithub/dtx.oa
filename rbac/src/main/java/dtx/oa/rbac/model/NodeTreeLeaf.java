/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.model;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author datouxi
 */
public class NodeTreeLeaf {
    
    private Node entityNode;
    private List<NodeTreeLeaf> leaves;
    public final static String NODEID="node-id";
    public final static String PARENTID="parent-id";
    public final static String TITLE="title";
    public final static String SELECTED="selected";
    public final static String CHILDS="childs";
    
    public NodeTreeLeaf(Node node,boolean isDepth){
        this.entityNode=node;
        leaves=new ArrayList<>();
        if(isDepth){
            List<Node> childs=IDaoFactory.iNodeDao().getChilds(node);
            for(Node child:childs){
                leaves.add(new NodeTreeLeaf(child));
            }
        }
    }
    
    public NodeTreeLeaf(Node node){
        this(node, true);
    }
    
    public NodeTreeLeaf(Node node,int nodeType,boolean isDepth){
        if(node.getNodeType()!=nodeType)return;
        this.entityNode=node;
        leaves=new ArrayList<>();
        if(isDepth){
            List<Node> childs=IDaoFactory.iNodeDao().getChildsByType(node, nodeType);
            for(Node child:childs){
                leaves.add(new NodeTreeLeaf(child, nodeType));
            }
        }
    }
    
    public NodeTreeLeaf(Node node,int nodeType){
        this(node, nodeType, true);
    }
    
    public NodeTreeLeaf(Node node,boolean status,boolean isDepth){
        if(node.getStatus()!=status)return;
        this.entityNode=node;
        leaves=new ArrayList<>();
        if(isDepth){
            List<Node> childs=IDaoFactory.iNodeDao().getChilds(node, status);
            for(Node child:childs){
                leaves.add(new NodeTreeLeaf(child,status));
            }
        }
    }
    
    public NodeTreeLeaf(boolean status,Node node){
        this(node,status,true);
    }
    
    public NodeTreeLeaf(Node node,boolean status,int nodeType,boolean isDepth){
        if(node.getStatus()!=status||node.getNodeType()!=nodeType)return;
        this.entityNode=node;
        leaves=new ArrayList<>();
        if(isDepth){
            List<Node> childs=IDaoFactory.iNodeDao().getChilds(node, status);
            for(Node child:childs){
                if(child.getNodeType()==nodeType)
                    leaves.add(new NodeTreeLeaf(child, status, nodeType));
            }
        }
    }
    
    public NodeTreeLeaf(Node node,boolean status,int nodeType){
        this(node,status,nodeType,true);
    }
    
    public boolean isEmptyLeaf(){
        return entityNode == null;
    }
    
    public Node getEntity(){
        return entityNode;
    }
    
    public List<NodeTreeLeaf> getLeaves(){
        return leaves;
    }
    
    public Iterator<NodeTreeLeaf> leafIterator(){
        return leaves.iterator();
    }
    
    public boolean hasLeaf(){
        return leaves.size()>0;
    }
    
    public JSONObject toJSON() throws JSONException{
        JSONObject obj=new JSONObject();
        obj.put(NODEID, entityNode.getUuid());
        obj.put(TITLE, entityNode.getTitle());
        obj.put(PARENTID, entityNode.getParentNode().getUuid());
        JSONArray childs=new JSONArray();
        for(NodeTreeLeaf leaf:leaves)
            childs.put(leaf.toJSON());
        obj.put(CHILDS, childs);
        return obj;
    }
    
}
