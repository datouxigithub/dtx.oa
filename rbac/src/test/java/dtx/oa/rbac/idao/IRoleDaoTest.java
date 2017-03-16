/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleTree;
import dtx.oa.util.AbstractDBUnitTestCase;
import dtx.oa.util.EntitiesHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author datouxi
 */
public class IRoleDaoTest extends AbstractDBUnitTestCase{
    private final String tableName="role";
    String[] tables={tableName,"role_user","role_node"};
    private IRoleDao rd;
    private final Role role1,role2,role3,role4,role5;
    
    {
        role1=new Role("ff808081598818400159881a149a0001", "A", "", "", true);
        role2=new Role("ff808081598818400159881a49100002", "B", null, "ff808081598818400159881a149a0001", true);
        role3=new Role("ff808081598818400159881aae8b0005", "E", "", "ff808081598818400159881a49100002", true);
        role4=new Role("ff808081598818400159881ad1980006", "F", "", "ff808081598818400159881a49100002", false);
        role5=new Role("ff808081598818400159881a696b0003", "C", "", "ff808081598818400159881a149a0001", false);
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        rd=IDaoFactory.iRoleDao();
        backupCustomTables(tables);
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }
    
    @Test
    public void testGetRoleById() throws DataSetException, DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role expResult = role2;
        Role result = rd.getRoleById(expResult.getUuid());
        EntitiesHelper.assertRole(expResult, result);
        
        expResult = role1;
        result = rd.getRoleById(expResult.getUuid());
        EntitiesHelper.assertRole(expResult, result);
    }
    
    @Test
    public void testGetByStatus() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        List<Role> roles=rd.getByStatus(false);
        assertTrue(roles.size()==2);
    }
    
    @Test
    public void testGetChilds_String() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        List<Role> roles=rd.getChilds(role1.getUuid());
        assertTrue(roles.size()==2);
    }
    
    @Test
    public void testGetChilds_String_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        List<Role> roles=rd.getChilds(role1.getUuid(),true);
        assertTrue(roles.size()==1);
    }
    
    @Test
    public void testGetAllChilds_String() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleTree roleTree = rd.getAllChilds(role1.getUuid());
        assertTrue(roleTree.toList().size()==4);
    }
    
    @Test
    public void testGetAllRoles_0args() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleTree roleTree = rd.getAllRoles();
        assertTrue(roleTree.toList().size()==5);
    }
    
    @Test
    public void testGetAllChilds_String_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleTree roleTree = rd.getAllChilds(role1.getUuid(),true);
        assertTrue(roleTree.toList().size()==2);
    }
    
    @Test
    public void testGetAllRoles_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleTree roleTree = rd.getAllRoles(true);
        assertTrue(roleTree.toList().size()==3);
    }

    @Test
    public void testUpdateRole() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role expect=role3;
        expect.setRoleName("更新标题");
        expect.setParentId("2369");
        expect.setStatus(false);
        expect.setRemark("注释");
        rd.updateRole(expect);
        EntitiesHelper.assertRole(expect, rd.getRoleById(expect.getUuid()));
    }

    @Test
    public void testUpdateRoleMessage() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role expect=role3;
        expect.setRoleName("更新标题");
        expect.setRemark("注释");
        rd.updateRoleMessage(expect);
        EntitiesHelper.assertRole(expect, rd.getRoleById(expect.getUuid()));
    }

    @Test
    public void testUpdateParent() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role expect=role3;
        expect.setParentId("2369");
        rd.updateParent(expect);
        EntitiesHelper.assertRole(expect, rd.getRoleById(expect.getUuid()));
    }

    @Test
    public void testUpdateStatus() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role expect=role3;
        expect.setStatus(false);
        rd.updateStatus(expect);
        EntitiesHelper.assertRole(expect, rd.getRoleById(expect.getUuid()));
    }

    @Test
    public void testDeleteRole() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role expect=role3;
        rd.deleteRole(expect);
        assertNull(rd.getRoleById(expect.getUuid()));
        assertTrue(IDaoFactory.iRoleUserDao().queryByRoleId(expect.getUuid()).isEmpty());
        assertTrue(IDaoFactory.iRoleNodeDao().queryByRoleId(expect.getUuid()).isEmpty());
    }

    @Test
    public void testAddRole() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        Role expect = role3;
        rd.addRole(expect);
        Role result = rd.getRoleById(expect.getUuid());
        EntitiesHelper.assertRole(expect, result);
    } 
}
