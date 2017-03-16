/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.User;
import dtx.oa.util.AbstractDBUnitTestCase;
import dtx.oa.util.EntitiesHelper;
import dtx.oa.util.StringUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author datouxi
 */
public class TestUserDao extends AbstractDBUnitTestCase{
  
    private IUserDao ud;
    private final String tableName="user";
    private final User expectUser1,expectUser2;
    
    {
        expectUser1=new User();
        expectUser1.setAccount("肥满");
        expectUser1.setPassword("56789");
        expectUser1.setStatus(true);
        expectUser1.setRemark("测试用户");
        
        expectUser2=new User();
        expectUser2.setAccount("臭臭屎");
        expectUser2.setPassword("123698");
        expectUser2.setStatus(false);
    }
    
    @Before
    public void setup() throws DataSetException, IOException{
        dbUnitConn.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        ud=IDaoFactory.iUserDao();
        backupCustomTables(new String[]{tableName,"role_user"});
    }

    @Test
    public void testAddUser() throws DataSetException, DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        String result=ud.addUser(expectUser1);
        assertNotNull("插入用户测试失败", result);
        
        User user=ud.getUserById(result);
        EntitiesHelper.assertUser(user, expectUser1);
        assertNotNull("create_time is null", user.getCreateTime());
        assertNotEquals("", user.getCreateTime());
        assertNull(user.getLoginTime());
        assertNull(user.getLoginIp());
        assertNotNull(user.getRemark());
        
        result=ud.addUser(expectUser2);
        assertNotNull("插入用户测试失败", result);
        user=ud.getUserById(result);
        EntitiesHelper.assertUser(user, expectUser2);
        assertNull(user.getRemark());
    }
    
    
    @Test
    public void testGetUserByAccount() throws DataSetException, DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        User result = ud.getUserByAccount(expectUser1.getAccount());
        EntitiesHelper.assertUser2(expectUser1, result);
        result=ud.getUserByAccount(expectUser2.getAccount());
        EntitiesHelper.assertUser2(expectUser2, result);
    }
    
    @Test
    public void testGetUserByStatus() throws DataSetException, DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        List<User> users=ud.getUsersByStatus(true);
        assertTrue(users.size()==1);
        users=ud.getUsersByStatus(false);
        assertTrue(users.size()==1);
    }
    
    @Test
    public void testDeleteUser() throws DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        User target = ud.getUserByAccount(expectUser1.getAccount());
        ud.deleteUser(target.getUuid());
        target = ud.getUserByAccount(expectUser1.getAccount());
        assertNull(target);
    }
    
    @Test
    public void testUpdateUser() throws DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        User target = ud.getUserByAccount(expectUser1.getAccount());
        target.setPassword(StringUtil.getMD5String("9631"));
        target.setLoginIp("127.0.0.1");
        target.setLoginTime(Timestamp.valueOf("2017-02-28 20:45:50"));
        target.setStatus(false);
        target.setRemark("改变一下");
        ud.updateUser(target);
        User result = ud.getUserByAccount(expectUser1.getAccount());
        EntitiesHelper.assertUser(target, result);
        
        target.setRemark(null);
        ud.updateUser(target);
        result = ud.getUserByAccount(expectUser1.getAccount());
        EntitiesHelper.assertUser(target, result);
        
        target.setPassword("");
        ud.updateUser(target);
        result = ud.getUserByAccount(expectUser1.getAccount());
        EntitiesHelper.assertUser(target, result);
    }
    
    @Test
    public void testUpdateLoginMessage() throws DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        User target = ud.getUserByAccount(expectUser1.getAccount());
        target.setLoginIp("127.0.0.9");
        ud.updateLoginMessage(target);
        User result = ud.getUserById(target.getUuid());
        assertNotEquals(target.getLoginTime(), result.getLoginTime());
        assertNotNull(result.getLoginIp());
        assertEquals(target.getLoginIp(), result.getLoginIp());
    }
    
    @Test
    public void testIsAdmin() throws DatabaseUnitException, SQLException{
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, ds);
        assertFalse(ud.isAdmin(expectUser1.getAccount()));
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException{
        resumeTable();
    }
    
}
