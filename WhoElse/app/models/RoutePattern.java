package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoutePattern {

    @Id
    @GeneratedValue
    public Integer routePatternId;

    public Integer userId;
    public String startAddress;
    public String endAddress;
    public Double startLat;
    public Double startLong;
    public Double endLat;
    public Double endLong;
    public String time;
    public String date;
    public Double punctuality;
    public String periodicity;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }
}