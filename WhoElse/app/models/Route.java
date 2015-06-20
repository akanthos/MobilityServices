package models;

import play.db.jpa.JPA;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Route {

    @Id
    @GeneratedValue
    public Integer routeId;

    public Integer userId;
    public Integer routePatternId;
    public String startAddress;
    public String endAddress;
    public Double startLat;
    public Double startLong;
    public Double endLat;
    public Double endLong;
    public String dateTime;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}