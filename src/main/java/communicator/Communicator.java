package communicator;

import java.util.ArrayList;

public class Communicator {

    private ArrayList<User> users = new ArrayList<>();

    // Singleton Design Pattern
    private static Communicator instance = new Communicator();
    private Communicator(){}

    public static Communicator getInstance(){
        return instance;
    }
    //

    public User FindUser(String userID){
        for(User user : users){
            if(user.getId() == userID){
                return user;
            }
        }
        return null;
    }

    public boolean addUser(String userID){
        return users.add(new User(userID));
    }

    public void UpdateUserLocation(String userID, String beaconID, int distance){
        for(User user : users){
            if(user.getId() == userID){
                user.setLocation(new Location(beaconID,distance));
            }
        }
    }
}
