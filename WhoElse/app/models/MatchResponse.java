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

//    public List<RoutePattern> routePatterns;
//    public List<List<Matching>> matchings;
    public List<RoutePattern> routePatterns;
    public List<Matching> matchings;

    public MatchResponse() {
        matchings = new ArrayList<>();
        routePatterns = new ArrayList<>();
    }
    public MatchResponse(Integer userId) {
        matchings = new ArrayList<>();
        routePatterns = new ArrayList<>();
        String queryStr = "SELECT m FROM Matching m WHERE (userId1 = " + userId + " OR userId2 = " + userId + ")";
        TypedQuery<Matching> matchingsQuery = JPA.em().createQuery(queryStr, Matching.class);
        for (Matching m: matchingsQuery.getResultList()) {
            matchings.add(m);
            if (userId == m.userId1) {
                queryStr = "SELECT rp FROM RoutePattern rp WHERE (routePatternId = " + m.routePatternId2 + ")";
            }
            else {
                queryStr = "SELECT rp FROM RoutePattern rp WHERE (routePatternId = " + m.routePatternId1 + ")";
            }

            TypedQuery<RoutePattern> routePatternsQuery = JPA.em().createQuery(queryStr, RoutePattern.class);

            for (RoutePattern p: routePatternsQuery.getResultList()) {
                routePatterns.add(p);
            }
        }
        if (matchings.size() != routePatterns.size()) {
            System.out.println("Matchings do not match routes!!!!");
            matchings = new ArrayList<>();
            routePatterns = new ArrayList<>();
        }
//        String queryStr = "SELECT rp FROM RoutePattern rp WHERE (userId = " + userId + ")";
//        TypedQuery<RoutePattern> query = JPA.em().createQuery(queryStr, RoutePattern.class);
//        for (RoutePattern p: query.getResultList()) {
//            routePatterns.add(p);
//            String queryStr2 = "SELECT m FROM Matching m WHERE (routePatternId1 = " + p.routePatternId + ")";
//            TypedQuery<Matching> query2 = JPA.em().createQuery(queryStr2, Matching.class);
//            List<Matching> l = query2.getResultList();
//            if (l != null) {
//                matchings.add(l);
//            }
//        }
    }


}
