package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    public Integer userId;

    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public String token;
    public Double balance;

    public static User findByUsername(String username)
    {
        return JPA.em().find(User.class, username);
    }

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}