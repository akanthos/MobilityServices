package models;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    public Integer id;

    public String firstname;
    public String lastname;

    @Constraints.Required
    public String email;
    public String date_of_birth;

    @Constraints.Required
    public String password_hash;

    public static User findByEmail(String email)
    {
        return JPA.em().find(User.class, email);
    }

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}