/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.xml.sax.InputSource;

/**
 *
 * @author datouxi
 */
public class AbstractDBUnitTestCase {
    public static IDatabaseConnection dbUnitConn;
    private File backupFile;
    
    @BeforeClass
    public static void init() throws DatabaseUnitException, SQLException{
        dbUnitConn=new DatabaseConnection(DBUtil.getConnection());
        dbUnitConn.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
    }
    
    protected IDataSet createDataSet(String tableName) throws DataSetException{
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(tableName+".xml");
        assertNotNull("dbunit的基本数据xml文件不存在", is);
//        return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
        ReplacementDataSet ds=new ReplacementDataSet(new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is))));
        ds.addReplacementObject("[null]", null);
        return ds;
    }
    
    protected void writeBackupFile(IDataSet ds) throws IOException, DataSetException{
        backupFile=File.createTempFile("backup", ".xml");
        FlatXmlDataSet.write(ds, new FileWriter(backupFile));
    }
    
    protected void backupAllTable() throws SQLException, IOException, DataSetException,NullPointerException{
        writeBackupFile(dbUnitConn.createDataSet());
    }
    
    protected void backupCustomTables(String tables[]) throws DataSetException, IOException{
        QueryDataSet ds=new QueryDataSet(dbUnitConn);
        for(String name:tables)
            ds.addTable(name);
        writeBackupFile(ds);
    }
    
    protected void backupOneTable(String tableName) throws DataSetException, IOException{
        backupCustomTables(new String[]{tableName});
    }
    
    protected void resumeTable() throws DatabaseUnitException, SQLException, IOException{
        dbUnitConn.getConnection().prepareStatement("set @@session.foreign_key_checks = 0").execute();
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConn, new FlatXmlDataSet(backupFile));
    }
    
    @AfterClass
    public static void destroy(){
        if(dbUnitConn==null)return;
        try {
            dbUnitConn.close();
        } catch (SQLException ex) {
            LogUtil.getLogger().error(ex);
        }
    }
}
