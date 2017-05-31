/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Node;
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

/**
 *
 * @author datouxi
 */
public class IRoleNodeDaoTest extends AbstractDBUnitTestCase{
    
    private final String tableName = "rbac_role_node";
    private IRoleNodeDao rnd;
    private RoleNode rn1,rn2,rn3,rn4,rn5,rn6,rn7,rn8;
    private IDataSet dsRole,dsNode,dsRN;
    
    {
//        rn1 = new RoleNode("ff80808159da31850159db0b9e86000b", new Role("ff808081598818400159881aae8b0005", null, null, null, true), new Node("ff80808159ac6d4b0159ac7688580006", null, null, null, null, true, 0));
//        rn2 = new RoleNode("ff80808159da31850159db0b9e87000c", new Role("ff808081598818400159881aae8b0005", null, null, null, true), new Node("ff80808159ac6d4b0159ac74e8980001", null, null, null, null, true, 0));
//        rn3 = new RoleNode("ff80808159da31850159db0b9e88000d", new Role("ff808081598818400159881aae8b0005", null, null, null, true), new Node("ff80808159ac6d4b0159ac755ebf0002", null, null, null, null, true, 0));
//        rn4 = new RoleNode("ff8081815a87f38d015a87f537fe0003", new Role("ff808081598818400159881a149a0001", null, null, null, true), new Node("ff80808159ac6d4b0159ac7645970005", null, null, null, null, true, 0));
//        rn5 = new RoleNode("ff8081815a87f38d015a87f537fd0002", new Role("ff808081598818400159881a149a0001", null, null, null, true), new Node("ff80808159ac6d4b0159ac75f2460004", null, null, null, null, true, 0));
//        rn6 = new RoleNode("ff8081815a87f38d015a87f537f10001", new Role("ff808081598818400159881a149a0001", null, null, null, true), new Node("ff80808159ac6d4b0159ac74e8980001", null, null, null, null, true, 0));
//        rn7 = new RoleNode("ff8081815a87f38d015a87f556640005", new Role("ff808081598818400159881a49100002", null, null, null, true), new Node("ff80808159ac6d4b0159ac7688580006", null, null, null, null, true, 0));
//        rn8 = new RoleNode("ff8081815a87f38d015a87f538000004", new Role("ff808081598818400159881a149a0001", null, null, null, true), new Node("ff80808159ac6d4b0159ac7688580006", null, null, null, null, true, 0));
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        rnd = IDaoFactory.iRoleNodeDao();
//        backupOneTable(tableName);
        backupCustomTables(new String[]{"rbac_node","rbac_role",tableName});
        dsRole=createDataSet("rbac_role");
        dsNode=createDataSet("rbac_node");
        dsRN=createDataSet(tableName);
    }
    
    private void initRoleNodes(){
        Role roleE=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881aae8b0005");
        Role roleA=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881a149a0001");
        Role roleB=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881a49100002");
        
        Node node6=IDaoFactory.iNodeDao().getNodeById("ff80808159ac6d4b0159ac7688580006");
        Node node1=IDaoFactory.iNodeDao().getNodeById("ff80808159ac6d4b0159ac74e8980001");
        Node node2=IDaoFactory.iNodeDao().getNodeById("ff80808159ac6d4b0159ac755ebf0002");
        Node node4=IDaoFactory.iNodeDao().getNodeById("ff80808159ac6d4b0159ac75f2460004");
        Node node5=IDaoFactory.iNodeDao().getNodeById("ff80808159ac6d4b0159ac7645970005");
        
        rn1 = new RoleNode("ff80808159da31850159db0b9e86000b", roleE, node6);
        rn2 = new RoleNode("ff80808159da31850159db0b9e87000c", roleE, node1);
        rn3 = new RoleNode("ff80808159da31850159db0b9e88000d", roleE, node2);
        rn4 = new RoleNode("ff8081815a87f38d015a87f537fe0003", roleA, node5);
        rn5 = new RoleNode("ff8081815a87f38d015a87f537fd0002", roleA, node4);
        rn6 = new RoleNode("ff8081815a87f38d015a87f537f10001", roleA, node1);
        rn7 = new RoleNode("ff8081815a87f38d015a87f556640005", roleB, node6);
        rn8 = new RoleNode("ff8081815a87f38d015a87f538000004", roleA, node6);
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }

    @Test
    public void testQueryByNodeId() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        assertTrue(rnd.queryByNode(expect.getNode()).size()==3);
    }

    @Test
    public void testQueryByRoleId() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        assertTrue(rnd.queryByRole(expect.getRole()).size()==3);
    }

    @Test
    public void testGetNodesByRole_Role() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        Role role = IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881aae8b0005");
        assertTrue(rnd.getNodesByRole(role).size()==3);
    }

    @Test
    public void testGetNodesByRole_List() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        List<Role> roles = new ArrayList();
        roles.add(IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881aae8b0005"));
        roles.add(IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881a149a0001"));
        assertTrue(rnd.getNodesByRole(roles).size()==5);
    }

    @Test
    public void testQueryById() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        EntitiesHelper.assertRoleNode(expect, rnd.queryById(expect.getUuid()));
    }

    @Test
    public void testDeleteByNodeId_RoleNode() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        rnd.deleteByNode(expect);
        assertTrue(rnd.queryByNode(expect.getNode()).isEmpty());
        assertNotNull(IDaoFactory.iRoleDao().getRoleById(expect.getRole().getUuid()));
        assertNotNull(IDaoFactory.iNodeDao().getNodeById(expect.getNode().getUuid()));
    }

    @Test
    public void testDeleteByRoleId_RoleNode() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        rnd.deleteByRole(expect);
        assertTrue(rnd.queryByRole(expect.getRole()).isEmpty());
    }

    @Test
    public void testDelete_RoleNode() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        rnd.delete(expect);
        assertNull(rnd.queryById(expect.getUuid()));
    }

    @Test
    public void testAddRoleNode_RoleNode() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsNode);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, dsRN);
        initRoleNodes();
        RoleNode expect = rn1;
        String result = rnd.addRoleNode(expect).getUuid();
        assertNotNull(result);
    }
}
