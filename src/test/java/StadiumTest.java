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
    }

    @Test
    public void testUpdateChairs(){
        Assert.assertTrue(!std1.updateChairs(-15));
        Assert.assertTrue(!std1.updateChairs(0));
        Assert.assertTrue(std1.updateChairs(10000));
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
}
