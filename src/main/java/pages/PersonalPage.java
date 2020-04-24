package pages;


import java.util.Date;
import java.util.Observable;

/*
subscribers should take the last integer found on db
 */
abstract public class PersonalPage extends Observable  {
    protected int subscribers;
    protected Date creation;
    protected String description;

    public PersonalPage(Date createdAt,String description){
        this.creation = createdAt;
        this.description = description;
        this.subscribers = 0;
    }

    public void changeDescription(String description){
        if(description != null && !description.isEmpty()){
            this.description = description;
            setChanged();
            notifyObservers("New description: "+description);
        }
    }

    public String getDescription() {
        return description;
    }
}