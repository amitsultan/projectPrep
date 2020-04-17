package Team;

public class Stadium {
    private int id;
    private Team owner;

    public Stadium(int id, Team owner) {
        this.id = id;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public Team getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Stadium))
            return false;
        Stadium stadium = (Stadium) object;
        return this.id == stadium.id;
    }
}
