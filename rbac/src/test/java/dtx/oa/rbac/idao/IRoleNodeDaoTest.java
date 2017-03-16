/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleNode;
import dtx.oa.util.AbstractDBUnitTestCase;
import dtx.oa.util.EntitiesHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author datouxi
 */
public class IRoleNodeDaoTest extends AbstractDBUnitTestCase{
    
    private final String tableName = "role_node";
    private IRoleNodeDao rnd;
    private final RoleNode rn1,rn2,rn3,rn4,rn5,rn6,rn7,rn8;
    
    {
        rn1 = new RoleNode("ff80808159da31850159db0b9e86000b", "ff808081598818400159881aae8b0005", "ff80808159ac6d4b0159ac7688580006");
        rn2 = new RoleNode("ff80808159da31850159db0b9e87000c", "ff808081598818400159881aae8b0005", "ff80808159ac6d4b0159ac74e8980001");
        rn3 = new RoleNode("ff80808159da31850159db0b9e88000d", "ff808081598818400159881aae8b0005", "ff80808159ac6d4b0159ac755ebf0002");
        rn4 = new RoleNode("ff8081815a87f38d015a87f537fe0003", "ff808081598818400159881a149a0001", "ff80808159ac6d4b0159ac7645970005");
        rn5 = new RoleNode("ff8081815a87f38d015a87f537fd0002", "ff808081598818400159881a149a0001", "ff80808159ac6d4b0159ac75f2460004");
        rn6 = new RoleNode("ff8081815a87f38d015a87f537f10001", "ff808081598818400159881a149a0001", "ff80808159ac6d4b0159ac74e8980001");
        rn7 = new RoleNode("ff8081815a87f38d015a87f556640005", "ff808081598818400159881a49100002", "ff80808159ac6d4b0159ac7688580006");
        rn8 = new RoleNode("ff8081815a87f38d015a87f538000004", "ff808081598818400159881a149a0001", "ff80808159ac6d4b0159ac7688580006");
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        rnd = IDaoFactory.iRoleNodeDao();
        backupOneTable(tableName);
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }

    @Test
    public void testQueryByNodeId() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        assertTrue(rnd.queryByNodeId(expect.getNodeId()).size()==3);
    }

    @Test
    public void testQueryByRoleId() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        assertTrue(rnd.queryByRoleId(expect.getRoleId()).size()==3);
    }

    @Test
    public void testGetNodesByRole_Role() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Role role = IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881aae8b0005");
        assertTrue(rnd.getNodesByRole(role).size()==3);
    }

    @Test
    public void testGetNodesByRole_List() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        List<Role> roles = new ArrayList();
        roles.add(new Role("ff808081598818400159881aae8b0005", null, null, null, true));
        roles.add(new Role("ff808081598818400159881a149a0001", null, null, null, true));
        assertTrue(rnd.getNodesByRole(roles).size()==5);
    }

    @Test
    public void testQueryById() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        EntitiesHelper.assertRoleNode(expect, rnd.queryById(expect.getUuid()));
    }

    @Test
    public void testDeleteByNodeId_RoleNode() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        rnd.deleteByNodeId(expect);
        assertTrue(rnd.queryByNodeId(expect.getNodeId()).isEmpty());
    }

    @Test
    public void testDeleteByRoleId_RoleNode() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        rnd.deleteByRoleId(expect);
        assertTrue(rnd.queryByRoleId(expect.getRoleId()).isEmpty());
    }

    @Test
    public void testDelete_RoleNode() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        rnd.delete(expect);
        assertNull(rnd.queryById(expect.getUuid()));
    }

    @Test
    public void testAddRoleNode_RoleNode() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        RoleNode expect = rn1;
        String result = rnd.addRoleNode(expect);
        assertNotNull(result);
    }
}
