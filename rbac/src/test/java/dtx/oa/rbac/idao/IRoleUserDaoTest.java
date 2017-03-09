/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
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
import org.junit.Ignore;

/**
 *
 * @author datouxi
 */
@Ignore
public class IRoleUserDaoTest extends AbstractDBUnitTestCase{
    
    private final String tableName = "role_user";
    private IRoleUserDao rud;
    private final RoleUser ru1,ru2,ru3,ru4,ru5,ru6;
    
    {
        ru1 = new RoleUser("ff80808159a23bb70159a23c34910002", "ff80808159a0bfb40159a0c035f40001", "ff808081598818400159881a89a90004");
        ru2 = new RoleUser("ff80808159922a2c01599237ab640002", "ff80808159922a2c01599237ab430001", "ff808081598818400159881a149a0001");
        ru3 = new RoleUser("ff80808159922a2c01599237ab810003", "ff80808159922a2c01599237ab430001", "ff808081598818400159881aae8b0005");
        ru4 = new RoleUser("ff80808159922a2c01599237ab820004", "ff80808159922a2c01599237ab430001", "ff808081598818400159881b48530009");
        ru5 = new RoleUser("ff80808159922a2c01599237ab840005", "ff80808159922a2c01599237ab430001", "ff808081598818400159881a89a90004");
        ru6 = new RoleUser("ff80808159a23bb70159a23c34890001", "ff80808159a0bfb40159a0c035f40001", "ff808081598818400159881aae8b0005");
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        rud = IDaoFactory.iRoleUserDao();
        backupOneTable(tableName);
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }
    
    @Test
    public void testQueryByUserId() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru2;
        assertTrue(rud.queryByUserId(expect.getUserId()).size()==4);
    }

    @Test
    public void testQueryByRoleId() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        assertTrue(rud.queryByRoleId(expect.getRoleId()).size()==2);
    }

    @Test
    public void testGetRoleByUser() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        User user = new User();
        user.setUuid("ff80808159a0bfb40159a0c035f40001");
        assertTrue(rud.getRoleByUser(user).size()==2);
    }

    @Test
    public void testQueryById() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        EntitiesHelper.assertRoleUser(expect, rud.queryById(expect.getUuid()));
    }

    @Test
    public void testDeleteByUserId_RoleUser() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        rud.deleteByUserId(expect);
        assertTrue(rud.queryByUserId(expect.getUserId()).isEmpty());
    }

    @Test
    public void testDeleteByRoleId_RoleUser() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        rud.deleteByRoleId(expect);
        assertTrue(rud.queryByRoleId(expect.getRoleId()).isEmpty());
    }

    @Test
    public void testDelete_RoleUser() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        rud.delete(expect);
        assertNull(rud.queryById(expect.getUuid()));
    }

    @Test
    public void testAddRoleUser_RoleUser() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        assertNotNull(expect);
        String result = rud.addRoleUser(expect);
        assertNotNull(result);
    }

    @Test
    public void testAddRoleUsers() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        rud.addRoleUsers(ru2.getUserId(), new String[]{ru2.getRoleId(),ru3.getRoleId(),ru4.getRoleId()});
        assertTrue(rud.queryByUserId(ru2.getUserId()).size()==3);
    }
}
