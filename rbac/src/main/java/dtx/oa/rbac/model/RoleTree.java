/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.model;

import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.util.LogUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author datouxi
 */
public class RoleTree {
   private List<RoleTreeLeaf> rootLeaves;
   private int treeDepth=Integer.MIN_VALUE;
   
   public RoleTree(User user){
       init(user);
   }
   
   public RoleTree(User user,boolean status){
       init(user,status);
   }
   
   public RoleTree(List<Role> roles){
       init(roles);
   }
   
   public RoleTree(List<Role> roles,boolean status){
       init(roles,status);
   }
   
   private void init(User user){
       List<Role> roles=null;
       if(IDaoFactory.iUserDao().isAdmin(user))
           roles=IDaoFactory.iRoleDao().getChilds(IRoleDao.ROOTID);
       else
           roles=IDaoFactory.iRoleUserDao().getRoleByUser(user);
       init(roles);
   }
   
   private void init(User user,boolean status){
       List<Role> roles=null;
       if(IDaoFactory.iUserDao().isAdmin(user))
           roles=IDaoFactory.iRoleDao().getChilds(IRoleDao.ROOTID);
       else
           roles=IDaoFactory.iRoleUserDao().getRoleByUser(user);
       init(roles,status);
   }
   
   private void init(List<Role> roles){
        rootLeaves=new ArrayList<>();
        for(Role role:roles){
            rootLeaves.add(new RoleTreeLeaf(role));
        }
        checkRepeat();
    }
    
    private void init(List<Role> roles,boolean status){
        rootLeaves=new ArrayList<>();
        for(Role role:roles){
//            /if(role.getStatus()!=status)continue;
            rootLeaves.add(new RoleTreeLeaf(role,status));
        }
        checkRepeat();
    }
   
   private void checkRepeat(){
        List<RoleTreeLeaf> newLeaves=new ArrayList<>();
        for(int i=0,len=rootLeaves.size();i<len;i++){
            List<RoleTreeLeaf> leaves=new ArrayList<>();
            for(int j=0;j<i;j++)
                leaves.add(rootLeaves.get(j));
            for(int j=i+1;j<len;j++)
                leaves.add(rootLeaves.get(j));
            if(!isExists(rootLeaves.get(i), leaves))
                newLeaves.add(rootLeaves.get(i));
        }
        rootLeaves=newLeaves;
    }
   
   private boolean isExists(RoleTreeLeaf targetLeaf,List<RoleTreeLeaf> leaves){
        Iterator<RoleTreeLeaf> leafIter=leaves.iterator();
        while(leafIter.hasNext()){
            RoleTreeLeaf leaf=leafIter.next();
            if(targetLeaf.equals(leaf)||isExists(targetLeaf, leaf.getLeaves()))return true;
        }
        return false;
    }
   
   private RoleTreeLeaf find(String roleId,List<RoleTreeLeaf> leaves){
        Iterator<RoleTreeLeaf> leafIter=leaves.iterator();
        while(leafIter.hasNext()){
            RoleTreeLeaf leaf=leafIter.next();
            try{
                if(roleId.equals(leaf.getEntity().getUuid()))return leaf;
            }catch(NullPointerException e){
                continue;
            }
            RoleTreeLeaf result=find(roleId, leaf.getLeaves());
            if(result!=null)
                return result;
        }
        return null;
    }
    
    public RoleTreeLeaf find(String roleId){
        return find(roleId, rootLeaves);
    }
   
   private boolean delete(String roleId,List<RoleTreeLeaf> leaves){
        for(RoleTreeLeaf leaf:leaves){
            if(leaf.getEntity().getUuid().equals(roleId))
                return true;
            if(delete(roleId,leaf.getLeaves()))
                return true;
        }
        return false;
    }
   
   public List<RoleTreeLeaf> getRoots(){
        return rootLeaves;
    }
   
   private int getDepth(RoleTreeLeaf leaf){
        if(leaf==null||leaf.getEntity()==null)return 0;
        int maxDepth=0;
        Iterator<RoleTreeLeaf> leafIter=leaf.leafIterator();
        while(leafIter.hasNext()){
            RoleTreeLeaf childLeaf=leafIter.next();
            int depth=getDepth(childLeaf);
            maxDepth=maxDepth<depth ? depth:maxDepth;
        }
        return ++maxDepth;
    }
    
    public int getDepth(){
        if(treeDepth!=Integer.MIN_VALUE)return treeDepth;
        int maxDepth=0;
        for(RoleTreeLeaf leaf:rootLeaves){
            int depth=getDepth(leaf);
            maxDepth=maxDepth<depth ? depth:maxDepth;
        }
        treeDepth=maxDepth!=0 ? maxDepth:treeDepth;
        return maxDepth;
    }
    
    private List<RoleTreeLeaf> toList(List<RoleTreeLeaf> leaves){
        List<RoleTreeLeaf> leafList=new ArrayList<>();
        for(RoleTreeLeaf leaf:leaves){
            if(leaf.isEmptyLeaf())continue;
            leafList.add(leaf);
            leafList.addAll(toList(leaf.getLeaves()));
        }
        return leafList;
    }
    
    public List<RoleTreeLeaf> toList(){
        return toList(rootLeaves);
    }
}
