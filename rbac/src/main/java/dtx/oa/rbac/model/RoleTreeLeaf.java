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

/**
 *
 * @author datouxi
 */
public class RoleTreeLeaf {
    private Role entityRole;
    private List<RoleTreeLeaf> leaves;
    
    public RoleTreeLeaf(Role role,boolean isDepth){
        this.entityRole=role;
        leaves=new ArrayList<>();
        if(isDepth){
            List<Role> childs=IDaoFactory.iRoleDao().getChilds(role);
            for(Role child:childs){
                RoleTreeLeaf leaf=new RoleTreeLeaf(child, isDepth);
                if(!leaves.contains(leaf))
                    leaves.add(leaf);
            }
        }
    }
    
    public RoleTreeLeaf(Role role){
        this(role,true);
    }
    
    public RoleTreeLeaf(Role role,boolean status,boolean isDepth){
        if(role.getStatus()==status){
            this.entityRole=role;
            leaves=new ArrayList<>();
            if(isDepth){
                List<Role> childs=IDaoFactory.iRoleDao().getChilds(role);
                for(Role child:childs){
                    RoleTreeLeaf leaf=new RoleTreeLeaf(child, status,isDepth);
                    if(!leaves.contains(leaf))
                        leaves.add(leaf);
                }
            }
        }
    }
    
    public RoleTreeLeaf(boolean status,Role role){
        this(role,status,true);
    }
    
    public boolean isEmptyLeaf(){
        return entityRole == null;
    }
    
    public Role getEntity(){
        return entityRole;
    }
    
    public List<RoleTreeLeaf> getLeaves(){
        return leaves;
    }
    
    public Iterator<RoleTreeLeaf> leafIterator(){
        return leaves.iterator();
    }
    
    public boolean hasLeaf(){
        return leaves.size()>0;
    }
}
