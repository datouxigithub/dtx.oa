/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleUser;
import dtx.oa.rbac.model.User;
import dtx.oa.util.AbstractDBUnitTestCase;
import dtx.oa.util.EntitiesHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class IRoleUserDaoTest extends AbstractDBUnitTestCase{
    
    private final String tableName = "rbac_role_user";
    private IRoleUserDao rud;
    private final RoleUser ru1,ru2,ru3,ru4,ru5,ru6;
    
    {
        ru1 = new RoleUser("ff80808159a23bb70159a23c34910002", new User("ff80808159a0bfb40159a0c035f40001"), new Role("ff808081598818400159881a89a90004", null, null, null, true));
        ru2 = new RoleUser("ff80808159922a2c01599237ab640002", new User("ff80808159922a2c01599237ab430001"), new Role("ff808081598818400159881a149a0001",null,null,null,true));
        ru3 = new RoleUser("ff80808159922a2c01599237ab810003", new User("ff80808159922a2c01599237ab430001"), new Role("ff808081598818400159881aae8b0005", null, null, null, true));
        ru4 = new RoleUser("ff80808159922a2c01599237ab820004", new User("ff80808159922a2c01599237ab430001"), new Role("ff808081598818400159881b48530009",null,null,null,true));
        ru5 = new RoleUser("ff80808159922a2c01599237ab840005", new User("ff80808159922a2c01599237ab430001"), new Role("ff808081598818400159881a89a90004", null, null, null, true));
        ru6 = new RoleUser("ff80808159a23bb70159a23c34890001", new User("ff80808159a0bfb40159a0c035f40001"), new Role("ff808081598818400159881aae8b0005", null, null, null, true));
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        rud = IDaoFactory.iRoleUserDao();
//        backupOneTable(tableName);
        backupCustomTables(new String[]{tableName,"rbac_user","rbac_role"});
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
        assertTrue(rud.queryByUser(expect.getUser()).size()==4);
    }

    @Test
    public void testQueryByRoleId() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        assertTrue(rud.queryByRole(expect.getRole()).size()==2);
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
        rud.deleteByUser(expect);
        assertTrue(rud.queryByUser(expect.getUser()).isEmpty());
    }

    @Test
    public void testDeleteByRoleId_RoleUser() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        RoleUser expect = ru1;
        rud.deleteByRole(expect);
        assertTrue(rud.queryByRole(expect.getRole()).isEmpty());
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
        String result = rud.addRoleUser(expect).getUuid();
        assertNotNull(result);
    }

    @Test
    public void testAddRoleUsers() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        rud.addRoleUsers(ru2.getUser(), Arrays.asList(new Role[]{ru2.getRole(),ru3.getRole(),ru4.getRole()}));
        assertTrue(rud.queryByUser(ru2.getUser()).size()==3);
    }
}
