/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa;

import dtx.oa.rbac.idao.IUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.User;
import dtx.oa.util.AbstractDBUnitTestCase;
import dtx.oa.util.EntitiesHelper;
import java.io.IOException;
import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author datouxi
 */
public class TestUserDao extends AbstractDBUnitTestCase{
  
    private IUserDao ud;
    private final String tableName="user";
    
    @Before
    public void setup() throws DataSetException, IOException{
        ud=IDaoFactory.iUserDao();
        backupOneTable(tableName);
    }
    
    @Test
    public void testAddUser() throws DataSetException, DatabaseUnitException, SQLException{
        dbUnitConn.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        IDataSet ds = createDataSet(tableName);
        DatabaseOperation.DELETE_ALL.execute(dbUnitConn, ds);
        User target=new User();
        target.setAccount("肥满");
        target.setPassword("5678");
        target.setStatus(true);
        target.setRemark("测试一下");
        String result=IDaoFactory.iUserDao().addUser(target);
        assertNotNull("插入用户测试失败", result);
        
        User user=IDaoFactory.iUserDao().getUserById(result);
        EntitiesHelper.assertUser(user, target);
    }
    /*
    @Test
    public void testGetUserById(){
        
    }
    
    @Test
    public void testGetUserByAccount(){
        
    }
    
    @Test
    public void testGetUserByStatus(){
        
    }
    
    @Test
    public void testDeleteUser(){
        
    }
    
    @Test
    public void testUpdateUser(){
        
    }
    
    @Test
    public void testUpdateLoginMessage(){
        
    }
    
    @Test
    public void testIsAdmin(){
        
    }
    */
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException{
        resumeTable();
    }
    
}
