package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    public Integer ratingId;

    public Integer userId;
    public Integer userIdPost;
    public Integer rating;
    public String comment;
    public String ratingDate;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}