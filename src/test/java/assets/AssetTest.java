package assets;

import org.junit.Assert;
import org.junit.Test;

public class AssetTest {

    @Test
    public void testConstructor() {
        try {
            new Asset("name", 10,"maccabi") {};
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            new Asset(null, 10,"maccabi") {};
            Assert.fail();
        } catch (Exception ignored) {
        }
        try {
            new Asset("name", 0,"maccabi") {};
            Assert.fail();
        } catch (Exception ignored) {
        }
        try {
            new Asset("name", -1,"maccabi") {};
            Assert.fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testGetters(){
        try {
            Asset asset = new Asset("name", 10,"maccabi") {};
            Assert.assertEquals("name", asset.getName());
            Assert.assertEquals(10.0, asset.getPrice(), 0);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testSetAssetName() {
        Asset asset = null;
        try {
            asset = new Stadium("name", "place", 10);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            asset.setName(null);
            Assert.fail();
        } catch (Exception ignored) {
        }
        try {
            asset.setName("new name");
            Assert.assertEquals("new name", asset.getName());
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            new Stadium(null, "place", 10);
            Assert.fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testSetPrice() {
        Asset asset = null;
        try {
            asset = new Stadium("name", "place", 10);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            asset.setPrice(1000);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            asset.setPrice(0);
            Assert.fail();
        } catch (Exception ignored) {
        }
        try {
            asset.setPrice(-1);
            Assert.fail();
        } catch (Exception ignored) {
        }
    }
}
