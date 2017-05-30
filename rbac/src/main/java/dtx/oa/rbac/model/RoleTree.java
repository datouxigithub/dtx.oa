/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.model;

import dtx.oa.rbac.idao.IRoleDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
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
   
   public RoleTree(boolean isDepth,User user){
       init(user,isDepth);
   }
   
   public RoleTree(User user){
       this(true,user);
   }
   
   public RoleTree(User user,boolean status,boolean isDepth){
       init(user,status,isDepth);
   }
   
   public RoleTree(User user,boolean status){
       this(user,status,true);
   }
   
   public RoleTree(boolean isDepth,List<Role> roles){
       init(roles,isDepth);
   }
   
   public RoleTree(List<Role> roles){
       this(true,roles);
   }
   
   public RoleTree(List<Role> roles,boolean status,boolean isDepth){
       init(roles,status,isDepth);
   }
   
   public RoleTree(List<Role> roles,boolean status){
       this(roles,status,true);
   }
   
   private void init(User user,boolean isDepth){
       List<Role> roles=null;
       if(IDaoFactory.iUserDao().isAdmin(user))
           roles=IDaoFactory.iRoleDao().getRoots();
//           roles=IDaoFactory.iRoleDao().getChilds(IRoleDao.ROOTID);
       else
           roles=IDaoFactory.iRoleUserDao().getRoleByUser(user);
       init(roles,isDepth);
   }
   
   private void init(User user,boolean status,boolean isDepth){
       List<Role> roles=null;
       if(IDaoFactory.iUserDao().isAdmin(user))
           roles=IDaoFactory.iRoleDao().getRoots();
//           roles=IDaoFactory.iRoleDao().getChilds(IRoleDao.ROOTID);
       else
           roles=IDaoFactory.iRoleUserDao().getRoleByUser(user);
       init(roles,status,isDepth);
   }
   
   private void init(List<Role> roles,boolean isDepth){
       rootLeaves=new ArrayList<>();
        for(Role role:roles){
            RoleTreeLeaf leaf=new RoleTreeLeaf(role, isDepth);
            if(!rootLeaves.contains(leaf))
                rootLeaves.add(leaf);
        }
        checkRepeat();
   }
    
   private void init(List<Role> roles,boolean status,boolean isDepth){
        rootLeaves=new ArrayList<>();
        for(Role role:roles){
            RoleTreeLeaf leaf=new RoleTreeLeaf(role, status, isDepth);
            if(!rootLeaves.contains(leaf))
                rootLeaves.add(leaf);
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
        return find(roleId, getRoots());
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
        return treeDepth;
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
