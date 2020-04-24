package assets;

public abstract class Asset {
    protected String name;
    protected float price;

    public Asset(String name, float price) throws Exception {
        if(name == null)
            throw new Exception("Asset name can't be null");
        this.name = name;
        if(price <= 0){
            throw new Exception("Asset must have a price");
        }
        this.price = price;
    }

    public Asset(){
        this.name="Stadium";
        price=100;
    }
    public String getname() {
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
