/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

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
}
