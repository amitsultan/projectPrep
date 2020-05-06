package assets;

public abstract class Asset {
    protected String teamName;
    protected String name;
    protected float price;

    public Asset(String name, float price, String teamName) throws Exception {
        if(name == null && teamName ==null)
            throw new Exception("Asset name can't be null");
        this.name = name;
        this.teamName=teamName;
        if(price <= 0){
            throw new Exception("Asset must have a price");
        }
        this.price = price;
    }

    public Asset() throws Exception {
        this("Stadium", 100, "maccabi");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name == null)
            throw new Exception("Asset name can't be null");
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) throws Exception {
        if(price <= 0)
            throw new Exception("Asset must have a price");
        this.price = price;
    }
}
