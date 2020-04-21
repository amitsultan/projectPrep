import assets.Stadium;
import org.junit.Test;

public class StadiumTest {

    @Test
    public void testConstructor(){
        Stadium stadium = new Stadium("Lakers");
        System.out.println(stadium.getId());
    }
}
