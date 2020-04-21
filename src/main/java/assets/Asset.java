package assets;

public abstract class Asset {
    String assetName;
    String description;
    float price;

    public Asset(String assetName, float price) throws Exception {
        this.assetName = assetName;
        if(price == 0){
            throw new Exception("Asset must have a price");
        }
        this.price = price;
    }

    public Asset(){
        this.assetName="Stadium";
        this.description="Stadium";
        price=100;
    }
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float isPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
