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

    public Integer userId;
    public Integer routePatternId;
    public String date;
    public String time;
    public Integer done;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
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

}