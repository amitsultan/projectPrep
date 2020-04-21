import dbhandler.Connector;
import org.junit.Assert;
import org.junit.Test;

public class ConnectorTest {

    @Test
    public void testConnection(){
        Assert.assertTrue(Connector.getInstance().checkConnection());
    }

    @Test
    public void testSelectQuery(){
        // get stadium ID from DB
    }
}
