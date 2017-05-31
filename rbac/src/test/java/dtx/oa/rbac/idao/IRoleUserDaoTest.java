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
    private RoleUser ru1,ru2,ru3,ru4,ru5,ru6;
    private IDataSet dsUser,dsRole,dsRU;
    
    {
//        ru1 = new RoleUser("ff80808159a23bb70159a23c34910002", new User("ff80808159a0bfb40159a0c035f40001","臭屎洋", "123698"), new Role("ff808081598818400159881a696b0003", "C", null, null, true));
//        ru2 = new RoleUser("ff80808159922a2c01599237ab640002", new User("ff80808159922a2c01599237ab430001","肥满","56789"), new Role("ff808081598818400159881a149a0001","A",null,null,true));
//        ru3 = new RoleUser("ff80808159922a2c01599237ab810003", new User("ff80808159922a2c01599237ab430001","肥满","56789"), new Role("ff808081598818400159881aae8b0005", "E", null, null, true));
//        ru4 = new RoleUser("ff80808159922a2c01599237ab820004", new User("ff80808159922a2c01599237ab430001","肥满","56789"), new Role("ff808081598818400159881ad1980006","F",null,null,true));
//        ru5 = new RoleUser("ff80808159922a2c01599237ab840005", new User("ff80808159922a2c01599237ab430001","肥满","56789"), new Role("ff808081598818400159881a696b0003", "C", null, null, true));
//        ru6 = new RoleUser("ff80808159a23bb70159a23c34890001", new User("ff80808159a0bfb40159a0c035f40001","臭屎洋", "123698"), new Role("ff808081598818400159881aae8b0005", "E", null, null, true));
    }
    
    @Before
    public void setUp() throws DataSetException, IOException {
        rud = IDaoFactory.iRoleUserDao();
        backupCustomTables(new String[]{"rbac_user","rbac_role",tableName});
        dsUser=createDataSet("rbac_user");
        dsRole=createDataSet("rbac_role");
        dsRU=createDataSet("rbac_role_user");
    }
    
    private void initRoleUsers(){
        User user1=IDaoFactory.iUserDao().getUserById("ff80808159a0bfb40159a0c035f40001");
        User user2=IDaoFactory.iUserDao().getUserById("ff80808159922a2c01599237ab430001");
        Role roleC=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881a696b0003");
        Role roleA=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881a149a0001");
        Role roleE=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881aae8b0005");
        Role roleF=IDaoFactory.iRoleDao().getRoleById("ff808081598818400159881ad1980006");
        
        ru1 = new RoleUser("ff80808159a23bb70159a23c34910002", user1, roleC);
        ru2 = new RoleUser("ff80808159922a2c01599237ab640002", user2, roleA);
        ru3 = new RoleUser("ff80808159922a2c01599237ab810003", user2, roleE);
        ru4 = new RoleUser("ff80808159922a2c01599237ab820004", user2, roleF);
        ru5 = new RoleUser("ff80808159922a2c01599237ab840005", user2, roleC);
        ru6 = new RoleUser("ff80808159a23bb70159a23c34890001", user1, roleE);
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }
    
    @Test
    public void testQueryByUserId() throws DatabaseUnitException, SQLException {
//        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru2;
        assertTrue(rud.queryByUser(expect.getUser()).size()==4);
    }

    @Test
    public void testQueryByRoleId() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru1;
        assertTrue(rud.queryByRole(expect.getRole()).size()==2);
    }

    @Test
    public void testGetRoleByUser() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        User user = IDaoFactory.iUserDao().getUserById("ff80808159a0bfb40159a0c035f40001");
        assertTrue(rud.getRoleByUser(user).size()==2);
    }

    @Test
    public void testQueryById() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru1;
        EntitiesHelper.assertRoleUser(expect, rud.queryById(expect.getUuid()));
    }

    @Test
    public void testDeleteByUserId_RoleUser() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru1;
        rud.deleteByUser(expect);
        assertTrue(rud.queryByUser(expect.getUser()).isEmpty());
    }

    @Test
    public void testDeleteByRoleId_RoleUser() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru1;
        rud.deleteByRole(expect);
        assertTrue(rud.queryByRole(expect.getRole()).isEmpty());
    }

    @Test
    public void testDelete_RoleUser() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru1;
        rud.deleteRoleUser(expect);
        assertNull(rud.queryById(expect.getUuid()));
    }

    @Test
    public void testAddRoleUser_RoleUser() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, dsRU);
        initRoleUsers();
        RoleUser expect = ru1;
        assertNotNull(expect);
        String result = rud.addRoleUser(expect).getUuid();
        assertNotNull(result);
    }

    @Test
    public void testAddRoleUsers() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsUser);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, dsRole);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, dsRU);
        initRoleUsers();
        rud.addRoleUsers(ru2.getUser(), Arrays.asList(new Role[]{ru2.getRole(),ru3.getRole(),ru4.getRole()}));
        assertTrue(rud.queryByUser(ru2.getUser()).size()==3);
    }
}
