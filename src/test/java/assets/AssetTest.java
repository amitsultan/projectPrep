package assets;

import org.junit.Assert;
import org.junit.Test;

public class AssetTest {

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
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            asset = new Stadium(null, "place", 10);
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
