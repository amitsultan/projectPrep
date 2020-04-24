package users;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void setPassword() {
        User user = null;
        try {
            user = new User("Mr", "Mr", "Mr", "Pass");
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            user.setPassword(null);
            Assert.fail();
        } catch (Exception ignored) {

        }
        try {
            user.setPassword("pass");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void setFirstName() {
        User user = null;
        try {
            user = new User("Mr", "Mr", "Mr", "Pass");
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            user.setFirstName(null);
            Assert.fail();
        } catch (Exception ignored) {

        }
        try {
            user.setFirstName("name");
        } catch (Exception ignored) {
            Assert.fail();
        }
    }

    @Test
    public void setLastName() {
        User user = null;
        try {
            user = new User("Mr", "Mr", "Mr", "Pass");
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            user.setLastName(null);
            Assert.fail();
        } catch (Exception ignored) {

        }
        try {
            user.setLastName("name");
        } catch (Exception ignored) {
            Assert.fail();
        }
    }

    @Test
    public void testEquals() {
        User u1 = null;
        User u2 = null;
        try {
            u1 = new User("Mr", "Mr", "Mr", "Pass");
            u2 = new User("Mrs", "Mrs", "Mrs", "Pass");
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(u1, u2);
        Assert.assertEquals(u1, u1);
        Assert.assertNotEquals(u1, null);
    }
}
