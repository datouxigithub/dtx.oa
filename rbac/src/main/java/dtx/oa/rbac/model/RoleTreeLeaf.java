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
    
    public RoleTreeLeaf(Role role){
        this.entityRole=role;
        leaves=new ArrayList<>();
        List<Role> childs=IDaoFactory.iRoleDao().getChilds(role.getUuid());
        for(Role child:childs){
            leaves.add(new RoleTreeLeaf(child));
        }
    }
    
    public RoleTreeLeaf(Role role,boolean status){
        if(role.getStatus()==status){
            this.entityRole=role;
            leaves=new ArrayList<>();
            List<Role> childs=IDaoFactory.iRoleDao().getChilds(role.getUuid());
            for(Role child:childs){
                leaves.add(new RoleTreeLeaf(child,status));
            }
        }
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
