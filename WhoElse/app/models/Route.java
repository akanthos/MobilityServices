package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue
    public Integer routeId;

    public Integer matchingId;
    public Integer routePatternId;
    public String date;
    public String time;
    public String status;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }
    public void update() { JPA.em().merge(this); }

    public Route () {
        this.matchingId = 0;
        this.routePatternId = 0;
        this.date = "";
        this.time = "";
        this.status = "wait";
    }

    public Route(Integer matchingId, Integer routePatternId, String date, String time) {
        this.matchingId = matchingId;
        this.routePatternId = routePatternId;
        this.date = date;
        this.time = time;
        this.status = "wait";
    }

    public static ArrayList<Route> getRoutesByPatternId(Integer routePatternId) {
        String queryStr = "SELECT r FROM Route r WHERE routePatternId = " + routePatternId ;
        TypedQuery<Route> query = JPA.em().createQuery(queryStr, Route.class);
        ArrayList<Route> ret = new ArrayList<>();
        for (Route r: query.getResultList()) {
            ret.add(r);
        }
        return ret;
    }

    public static Route getRouteById(Integer routeId) {
        String queryStr = "SELECT r FROM Route r WHERE routeId = " + routeId ;
        TypedQuery<Route> query = JPA.em().createQuery(queryStr, Route.class);
        return query.getSingleResult();
    }

}