package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Matching {

    @Id
    @GeneratedValue
    public Integer matchingId;

    public Integer userId;
    public Integer routePatternId1;
    public Integer routePatternId2;
    public Double value;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}