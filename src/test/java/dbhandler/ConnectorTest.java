package dbhandler;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;



public class ConnectorTest {

    @Test
    public void testConnection(){
        Connector connector = Connector.getInstance();
        Assert.assertNotNull(connector);
        Assert.assertTrue(connector.checkConnection());
    }

    @Test
    public void testSelectQuery(){
        Connector connector = Connector.getInstance();
        try {
            Connection conn = connector.establishConnection();
            conn.close();
            Assert.assertNull(connector.selectQuery(null,"querry"));
            Assert.assertNull(connector.selectQuery(conn,"querry"));
            conn = connector.establishConnection();
            ResultSet set = connector.selectQuery(conn,"Select stadiumID from stadium");
            Assert.assertNotNull(set);
            Assert.assertTrue(connector.closeConnection(conn));
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
