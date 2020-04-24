package users;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ComplaintTest {

    @Test
    public void testConstructor() {
        try {
            new Complaint("body", new Date());
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            new Complaint(null, new Date());
            Assert.fail();
        } catch (Exception ignored) {

        }
        try {
            new Complaint("body", null);
            Assert.fail();
        } catch (Exception ignored) {

        }
    }
}
