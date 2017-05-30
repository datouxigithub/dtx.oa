/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Node;
import dtx.oa.util.AbstractDBUnitTestCase;
import dtx.oa.util.EntitiesHelper;
import java.io.IOException;
import java.sql.SQLException;
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
public class INodeDaoTest extends AbstractDBUnitTestCase{
    
    private final String tableName="node";
    String[] tables={tableName,"role_node"};
    private INodeDao nd;
    private final Node node1,node2,node3,node4,node5,node6;
    
    {
        node1=new Node("ff80808159ac6d4b0159ac74e8980001", "节点组1", "", "", new Node("", null, null, null, null, true, 0), true, 2);
        node2=new Node("ff80808159ac6d4b0159ac755ebf0002", "节点11", "", null, new Node("ff80808159ac6d4b0159ac74e8980001", null, null, null, null, true, 0), true, 1);
        node3=new Node("ff80808159ac6d4b0159ac75a5960003", "节点12", "cde", "", new Node("", null, null, null, null, true, 0), false, 1);
        node4=new Node("ff80808159ac6d4b0159ac75f2460004", "节点组11", "", "", new Node("ff80808159ac6d4b0159ac74e8980001", null, null, null, null, true, 0), false, 2);
        node5=new Node("ff80808159ac6d4b0159ac7645970005", "节点111", "ddd", "", new Node("ff80808159ac6d4b0159ac75f2460004", null, null, null, null, true, 0), true, 1);
        node6=new Node("ff80808159ac6d4b0159ac7688580006", "节点2", "eee", "", new Node("", null, null, null, null, true, 0), false, 1);
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        nd = IDaoFactory.iNodeDao();
        backupCustomTables(tables);
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }
    
    @Test
    public void testGetNodeById() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Node expect = node2;
        Node result = nd.getNodeById(expect.getUuid());
        EntitiesHelper.assertNode(expect, result);
    }

    @Test
    public void testGetByStatus() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getByStatus(true).size()==3);
    }

    @Test
    public void testGetByType() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getByType(2).size()==2);
    }

    @Test
    public void testGetChilds_String() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getChilds(node1.getUuid()).size()==2);
    }

    @Test
    public void testGetChilds_String_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getChilds(node1.getUuid(), true).size()==1);
    }

    @Test
    public void testGetChildsByType_String_int() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getChildsByType(node1.getUuid(), 1).size()==1);
    }

    @Test
    public void testGetChildsByType_3args() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getChildsByType(node1.getUuid(), 2, false).size()==1);
    }

    @Test
    public void testGetAllChilds_String() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllChilds(node1.getUuid()).toList().size()==3);
    }

    @Test
    public void testGetAllChilds_String_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllChilds(node1.getUuid(),true).toList().size()==1);
    }
/*
    @Test
    public void testGetAllChildsByType_String_int() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllChildsByType(node1.getUuid(), 1).toList().size()==1);
    }

    @Test
    public void testGetAllChildsByType_3args() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllChildsByType(node1.getUuid(), 1,false).toList().isEmpty());
    }
*/
    @Test
    public void testGetAllNodes_0args() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllNodes().toList().size()==6);
    }

    @Test
    public void testGetAllNodes_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllNodes(true).toList().size()==2);
    }
/*
    @Test
    public void testGetAllNodesByType_int() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllNodesByType(2).toList().size()==2);
    }

    @Test
    public void testGetAllNodesByType_int_boolean() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getAllNodesByType(2, false).toList().isEmpty());
        assertTrue(nd.getAllNodesByType(2, true).toList().size()==1);
    }
*/
    @Test
    public void testGetNodeTypes() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertTrue(nd.getNodeTypes().entrySet().size()==2);
    }

    @Test
    public void testGetParentId() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertEquals(node5.getParentNode().getUuid(), node4.getUuid());
    }

    @Test
    public void testUpdateNode() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Node expect = node2;
        expect.setTitle("更新后的标题");
        expect.setAddress("新地址");
        expect.setRemark("新注释");
        expect.setParentNode(new Node("56987", null, null, null, null, true, 0));
        expect.setNodeType(2);
        expect.setStatus(false);
        nd.updateNode(expect);
        EntitiesHelper.assertNode(expect, nd.getNodeById(expect.getUuid()));
        
        expect.setTitle("");
        expect.setRemark(null);
        expect.setParentNode(null);
        nd.updateNode(expect);
        EntitiesHelper.assertNode(expect, nd.getNodeById(expect.getUuid()));
    }

    @Test
    public void testUpdateNodeMessage() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Node expect = node2;
        expect.setTitle("更新后的标题");
        expect.setAddress("新地址");
        expect.setRemark("新注释");
        nd.updateNode(expect);
        EntitiesHelper.assertNode(expect, nd.getNodeById(expect.getUuid()));
    }

    @Test
    public void testUpdateParent() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Node expect = node2;
        expect.setParentNode(new Node("56987", null, null, null, null, true, 0));
        nd.updateNode(expect);
        EntitiesHelper.assertNode(expect, nd.getNodeById(expect.getUuid()));
    }

    @Test
    public void testUpdateStatus() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Node expect = node2;
        expect.setStatus(false);
        nd.updateNode(expect);
        EntitiesHelper.assertNode(expect, nd.getNodeById(expect.getUuid()));
    }

    @Test
    public void testDelete_Node() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        Node expect = node2;
        nd.delete(expect);
        assertNull(nd.getNodeById(expect.getUuid()));
        assertTrue(IDaoFactory.iRoleNodeDao().queryByNode(expect).isEmpty());
    }

    @Test
    public void testAddNode() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        Node expect = node2;
        nd.addNode(expect);
        Node result = nd.getNodeById(expect.getUuid());
        EntitiesHelper.assertNode(expect, result);
    }
}
