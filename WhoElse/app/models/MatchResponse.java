package models;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanasis on 23/6/2015.
 */
public class MatchResponse {

    public List<RoutePattern> routePatterns;
    public List<List<Matching>> matchings;

    public MatchResponse(Integer userId) {
        matchings = new ArrayList<>();
        routePatterns = new ArrayList<>();
        String queryStr = "SELECT rp FROM RoutePattern rp WHERE (userId = " + userId + ")";
        TypedQuery<RoutePattern> query = JPA.em().createQuery(queryStr, RoutePattern.class);
        for (RoutePattern p: query.getResultList()) {
            routePatterns.add(p);
            String queryStr2 = "SELECT m FROM Matching m WHERE (routePatternId1 = " + p.routePatternId + ")";
            TypedQuery<Matching> query2 = JPA.em().createQuery(queryStr2, Matching.class);
            List<Matching> l = query2.getResultList();
            if (l != null) {
                matchings.add(l);
            }
        }
    }


}
