package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

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
    public String company;
    public Double balance;

    public static User findByUsername(String username)
    {
        User user;

        String query = "SELECT u FROM User u WHERE username = '" + username + "')";
        TypedQuery<User> query_result = JPA.em().createQuery(query, User.class);
        user = query_result.getSingleResult();

        return user;
    }

    public static User findById(Integer id)
    {
        User user;

        String query = "SELECT u FROM User u WHERE userId = " + id  ;
        TypedQuery<User> query_result = JPA.em().createQuery(query, User.class);
        user = query_result.getSingleResult();

        return user;
    }

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }
}