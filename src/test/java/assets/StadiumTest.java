package assets;

import assets.Stadium;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team.Team;

public class StadiumTest {

    private Stadium std1;


    @Before
    public void setUp() {
        try {
            std1 = new Stadium("stadium1", "noWhere", 2);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        try {
            new Stadium("std", "place", -1);
            Assert.fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void testUpdateChairs(){
        Assert.assertFalse(std1.updateChairs(-15));
        Assert.assertFalse(std1.updateChairs(0));
        Assert.assertTrue(std1.updateChairs(10000));
        Assert.assertEquals(10000, std1.getSize());
    }

    @Test
    public void testSetTeam(){
        try {
            Team team = new Team("Swans",std1);
            Assert.assertEquals(std1.getOwner().getName(),team.getName());
            std1.setTeam(null);
            Assert.assertEquals(std1.getOwner().getName(),team.getName());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testEquals(){
        Assert.assertNotEquals(std1, null);
    }
}
