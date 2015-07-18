package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import java.util.List;

@Entity
public class Matching {

    @Id
    @GeneratedValue
    public Integer matchingId;

    public Integer userId1;
    public Integer userId2;
    public Integer routePatternId1;
    public Integer routePatternId2;
    public Integer active;
    public Double value;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public void update() { JPA.em().merge(this); }

    public static Matching getMatchingById(Integer matchingId) {

        String string_query = "SELECT m FROM Matching m WHERE matchingId = " + matchingId;
        TypedQuery<Matching> query = JPA.em().createQuery(string_query, Matching.class);
        return query.getSingleResult();
    }

    public static Matching getMatchingByRouteIds(Integer routePatternId1, Integer routePatternId2) {

        String queryStr = "SELECT m FROM Matching m WHERE ((routePatternId1 = " + routePatternId1 + " AND routePatternId2 = " + routePatternId2 + ") OR (routePatternId1 = " + routePatternId2 + " AND routePatternId2 = " + routePatternId1 +") )";
        TypedQuery<Matching> query = JPA.em().createQuery(queryStr, Matching.class);
        return query.getSingleResult();
    }

    public static List<Matching> getActiveMatchingsByUserId(Integer userId) {
        String queryStr = "SELECT m FROM Matching m WHERE ((userId1 = " + userId + " OR userId2 = " + userId + ") AND (active = 1) )";
        TypedQuery<Matching> query = JPA.em().createQuery(queryStr, Matching.class);
        return query.getResultList();
    }

}