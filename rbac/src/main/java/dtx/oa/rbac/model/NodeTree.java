/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.model;

import dtx.oa.rbac.idao.INodeDao;
import dtx.oa.rbac.idao.IRoleNodeDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author datouxi
 */
public class NodeTree {
    private List<NodeTreeLeaf> rootLeaves=new ArrayList<>();
    private int treeDepth=Integer.MIN_VALUE;
    
    public NodeTree(boolean isDepth,User user){
        init(user,isDepth);
    }
    
    public NodeTree(User user){
        this(true,user);
    }
    
    public NodeTree(User user,boolean status,boolean isDepth){
        init(user, status,isDepth);
    }
    
    public NodeTree(User user,boolean status){
        this(user, status,true);
    }
    
    public NodeTree(boolean isDepth,List<Role> roles){
        init(roles,isDepth);
    }
    
    public NodeTree(List<Role> roles){
        this(true,roles);
    }
    
    public NodeTree(List<Role> roles,boolean status,boolean isDepth){
        init(roles, status,isDepth);
    }
    
    public NodeTree(List<Role> roles,boolean status){
        this(roles, status,true);
    }
    
    public NodeTree(boolean isDepth,Node[] nodes){
        init(nodes,isDepth);
    }
    
    public NodeTree(Node[] nodes){
        this(true,nodes);
    }
    
    public NodeTree(Node[] nodes,int nodeType,boolean isDepth){
        init(nodes, nodeType,isDepth);
    }
    
    public NodeTree(Node[] nodes,int nodeType){
        this(nodes, nodeType,true);
    }
    
    public NodeTree(Node[] nodes,boolean status,boolean isDepth){
        init(nodes, status,isDepth);
    }
    
    public NodeTree(Node[] nodes,boolean status){
        this(nodes, status,true);
    }
    
    public NodeTree(Node[] nodes,boolean status,int nodeType,boolean isDepth){
        init(nodes, status, nodeType,isDepth);
    }
    
    public NodeTree(Node[] nodes,boolean status,int nodeType){
        this(nodes, status, nodeType,true);
    }
    
    private void init(User user,boolean isDepth){
        INodeDao inDao=IDaoFactory.iNodeDao();
        if(IDaoFactory.iUserDao().isAdmin(user)){
            Iterator<Node> iter=inDao.getChilds(INodeDao.ROOTID).iterator();
            while(iter.hasNext()){
                rootLeaves.add(new NodeTreeLeaf((Node)iter.next()));
            }
        }else
            init(IDaoFactory.iRoleUserDao().getRoleByUser(user),isDepth);
    }
    
    private void init(User user,boolean status,boolean isDepth){
        INodeDao inDao=IDaoFactory.iNodeDao();
        if(IDaoFactory.iUserDao().isAdmin(user)){
            Iterator<Node> iter=inDao.getChilds(INodeDao.ROOTID).iterator();
            while(iter.hasNext()){
                rootLeaves.add(new NodeTreeLeaf((Node)iter.next()));
            }
        }else
            init(IDaoFactory.iRoleUserDao().getRoleByUser(user),status,isDepth);
    }
    
    private void init(List<Role> roles,boolean isDepth){
        List<Node> nodes=new ArrayList<>();
        IRoleNodeDao irnDao=IDaoFactory.iRoleNodeDao();
        for(Role role:roles){
            nodes.addAll(irnDao.getNodesByRole(role));
        }
        init(nodes.toArray(new Node[nodes.size()]),isDepth);
        List<NodeTreeLeaf> forDelete=new ArrayList<>();
        for(NodeTreeLeaf leaf:toList()){
            boolean exists=false;
            for(Node node:nodes){
                if(node.equals(leaf.getEntity())){
                    exists=true;
                    break;
                }
            }
            if(!exists)
                forDelete.add(leaf);
        }
        for(NodeTreeLeaf leaf:forDelete)
            delete(leaf.getEntity().getUuid(), rootLeaves);
    }
    
    private void init(List<Role> roles,boolean status,boolean isDepth){
        List<Node> nodes=new ArrayList<>();
        IRoleNodeDao irnDao=IDaoFactory.iRoleNodeDao();
        for(Role role:roles){
            if(role.getStatus()!=status)continue;
            nodes.addAll(irnDao.getNodesByRole(role));
        }
        init(nodes.toArray(new Node[nodes.size()]), status,isDepth);
        List<NodeTreeLeaf> forDelete=new ArrayList<>();
        for(NodeTreeLeaf leaf:toList()){
            boolean exists=false;
            for(Node node:nodes){
                if(node.getStatus()!=status)
                    continue;
                if(node.equals(leaf.getEntity())){
                    exists=true;
                    break;
                }
            }
            if(!exists)
                forDelete.add(leaf);
        }
        for(NodeTreeLeaf leaf:forDelete)
            delete(leaf.getEntity().getUuid(), rootLeaves);
    }
    
    private void init(Node[] nodes,boolean isDepth){
        rootLeaves=new ArrayList<>();
        for(Node node:nodes){
            rootLeaves.add(new NodeTreeLeaf(node,isDepth));
        }
        checkRepeat();
    }

    private void init(Node[] nodes,int nodeType,boolean isDepth){
        rootLeaves=new ArrayList<>();
        for(Node node:nodes){
            if(node.getNodeType()!=nodeType)continue;
            rootLeaves.add(new NodeTreeLeaf(node, nodeType,isDepth));
        }
        checkRepeat();
    }
    
    private void init(Node[] nodes,boolean status,boolean isDepth){
        rootLeaves=new ArrayList<>();
        for(Node node:nodes){
            if(node.getStatus()!=status)continue;
            rootLeaves.add(new NodeTreeLeaf(node,status,isDepth));
        }
        checkRepeat();
    }
    
    private void init(Node[] nodes,boolean status,int nodeType,boolean isDepth){
        rootLeaves=new ArrayList<>();
        for(Node node:nodes){
            if(node.getStatus()!=status||node.getNodeType()!=nodeType)continue;
            rootLeaves.add(new NodeTreeLeaf(node, status, nodeType,isDepth));
        }
        checkRepeat();
    }
    
    private void checkRepeat(){
        List<NodeTreeLeaf> newLeaves=new ArrayList<>();
        for(int i=0,len=rootLeaves.size();i<len;i++){
            List<NodeTreeLeaf> leaves=new ArrayList<>();
            for(int j=0;j<i;j++)
                leaves.add(rootLeaves.get(j));
            for(int j=i+1;j<len;j++)
                leaves.add(rootLeaves.get(j));
            if(!isExists(rootLeaves.get(i), leaves))
                newLeaves.add(rootLeaves.get(i));
        }
        rootLeaves=newLeaves;
    }
    
    private boolean isExists(NodeTreeLeaf targetLeaf,List<NodeTreeLeaf> leaves){
        Iterator<NodeTreeLeaf> leafIter=leaves.iterator();
        while(leafIter.hasNext()){
            NodeTreeLeaf leaf=leafIter.next();
            if(targetLeaf.getEntity().equals(leaf.getEntity())||isExists(targetLeaf, leaf.getLeaves()))return true;
        }
        return false;
    }
    
    private NodeTreeLeaf find(String nodeId,List<NodeTreeLeaf> leaves){
        Iterator<NodeTreeLeaf> leafIter=leaves.iterator();
        while(leafIter.hasNext()){
            NodeTreeLeaf leaf=leafIter.next();
            try{
                if(nodeId.equals(leaf.getEntity().getUuid()))return leaf;
            }catch(NullPointerException e){
                continue;
            }
            NodeTreeLeaf result=find(nodeId, leaf.getLeaves());
            if(result!=null)
                return result;
        }
        return null;
    }
    
    public NodeTreeLeaf find(String nodeId){
        return find(nodeId, rootLeaves);
    }
    
    private boolean delete(String nodeId,List<NodeTreeLeaf> leaves){
        for(NodeTreeLeaf leaf:leaves){
            if(leaf.getEntity().getUuid().equals(nodeId)){
                leaves.remove(leaf);
                return true;
            }
            if(delete(nodeId,leaf.getLeaves()))
                return true;
        }
        return false;
    }
    
    public List<NodeTreeLeaf> getRoots(){
        return rootLeaves;
    }
    
    private int getDepth(NodeTreeLeaf leaf){
        if(leaf==null||leaf.getEntity()==null)return 0;
        int maxDepth=0;
        Iterator<NodeTreeLeaf> leafIter=leaf.leafIterator();
        while(leafIter.hasNext()){
            NodeTreeLeaf childLeaf=leafIter.next();
            int depth=getDepth(childLeaf);
            maxDepth=maxDepth<depth ? depth:maxDepth;
        }
        return ++maxDepth;
    }
    
    public int getDepth(){
        if(treeDepth!=Integer.MIN_VALUE)return treeDepth;
        int maxDepth=0;
        for(NodeTreeLeaf leaf:rootLeaves){
            int depth=getDepth(leaf);
            maxDepth=maxDepth<depth ? depth:maxDepth;
        }
        treeDepth=maxDepth!=0 ? maxDepth:treeDepth;
        return maxDepth;
    }
    
    private List<NodeTreeLeaf> toList(List<NodeTreeLeaf> leaves){
        List<NodeTreeLeaf> leafList=new ArrayList<>();
        for(NodeTreeLeaf leaf:leaves){
            if(leaf.isEmptyLeaf())continue;
            leafList.add(leaf);
            leafList.addAll(toList(leaf.getLeaves()));
        }
        return leafList;
    }
    
    public List<NodeTreeLeaf> toList(){
        return toList(rootLeaves);
    }
    
    public JSONArray toJSON(){
        JSONArray arr=new JSONArray();
        for(NodeTreeLeaf leaf:rootLeaves){
            try {
                arr.put(leaf.toJSON());
            } catch (JSONException ex) {
            }
        }
        return arr;
    }
}
