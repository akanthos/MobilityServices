package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Search {

    @Id
    @GeneratedValue
    public Integer searchId;

    public String startAreaLoc;
    public String startAreaSubLoc;
    public String endAreaLoc;
    public String endAreaSubLoc;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}