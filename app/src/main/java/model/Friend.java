package model;

/**
 * Created by Vu Khac Hoi on 9/25/2017.
 */

public class Friend {
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    String Avatar;
    String Name;



    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastCommenet() {
        return LastCommenet;
    }

    public void setLastCommenet(String lastCommenet) {
        LastCommenet = lastCommenet;
    }

    String LastCommenet;
String Email;

    public Friend(String avatar, String name, String lastCommenet, String email, String status) {
        Avatar = avatar;
        Name = name;
        LastCommenet = lastCommenet;
        Email = email;
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String Status;
}
