/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleNode;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
import static junit.framework.Assert.*;

/**
 *
 * @author datouxi
 */
public class EntitiesHelper {
    public static void assertUser(User expected,User target){
        assertNotNull(expected);
        assertNotNull(target);
        assertEquals(expected.getUuid(), target.getUuid());
        assertEquals(expected.getAccount(), target.getAccount());
        assertEquals(expected.getPassword(), target.getPassword());
        assertEquals(expected.getCreateTime(), target.getCreateTime());
        assertEquals(expected.getLoginTime(), target.getLoginTime());
        assertEquals(expected.getLoginIp(), target.getLoginIp());
        assertEquals(expected.getStatus(), target.getStatus());
        assertEquals(expected.getRemark(), target.getRemark());
    }
    
    public static void assertUser2(User expected,User target){
        assertNotNull(expected);
        assertNotNull(target);
        assertEquals(expected.getAccount(), target.getAccount());
        assertEquals(expected.getPassword(), target.getPassword());
        assertEquals(expected.getStatus(), target.getStatus());
        assertEquals(expected.getRemark(), target.getRemark());
    }
    
    public static void assertRole(Role expected,Role target){
        assertNotNull(expected);
        assertNotNull(target);
        assertEquals(expected.getUuid(), target.getUuid());
        assertEquals(expected.getRoleName(), target.getRoleName());
        assertEquals(expected.getParentRole(), target.getParentRole());
        assertEquals(expected.getRemark(), target.getRemark());
        assertEquals(expected.getStatus(), target.getStatus());
    }
    
    public static void assertNode(Node expected,Node target){
        assertNotNull(expected);
        assertNotNull(target);
        assertEquals(expected.getUuid(), target.getUuid());
        assertEquals(expected.getTitle(), target.getTitle());
        assertEquals(expected.getAddress(), target.getAddress());
        assertEquals(expected.getNodeType(), target.getNodeType());
        assertEquals(expected.getParentNode().getUuid(), target.getParentNode().getUuid());
        assertEquals(expected.getRemark(), target.getRemark());
        assertEquals(expected.getStatus(), target.getStatus());
    }
    
    public static void assertRoleNode(RoleNode expected,RoleNode target){
        assertNotNull(expected);
        assertNotNull(target);
        assertEquals(expected.getUuid(), target.getUuid());
        assertEquals(expected.getRole().getUuid(), target.getRole().getUuid());
        assertEquals(expected.getNode().getUuid(), target.getNode().getUuid());
    }
    
    public static void assertRoleUser(RoleUser expected,RoleUser target){
        assertNotNull(expected);
        assertNotNull(target);
        assertEquals(expected.getUuid(), target.getUuid());
        assertEquals(expected.getRole().getUuid(), target.getRole().getUuid());
        assertEquals(expected.getUser().getUuid(), target.getUser().getUuid());
    }
}