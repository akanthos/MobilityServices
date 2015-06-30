package models;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import play.db.jpa.JPA;
import scala.Tuple2;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thanasis on 23/6/2015.
 */
public class MatchResponse {

    public Map<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>> routePatterns;

//    public List<RoutePattern> routePatterns;
//    public List<Matching> matchings;

    public MatchResponse() {
        routePatterns = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
    }
    public MatchResponse(Integer userId) {
        routePatterns = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();

        String queryStr = "SELECT rp FROM RoutePattern rp WHERE (userId = " + userId + ")";
        TypedQuery<RoutePattern> routePatternsQuery = JPA.em().createQuery(queryStr, RoutePattern.class);

        for (RoutePattern p: routePatternsQuery.getResultList()) {
            queryStr = "SELECT m FROM Matching m WHERE (routePatternId1 = " + p.routePatternId + " OR routePatternId2 = " + p.routePatternId + ")";
            TypedQuery<Matching> matchingsQuery = JPA.em().createQuery(queryStr, Matching.class);
            List<Matching> results = matchingsQuery.getResultList();
            if (!results.isEmpty()) {
                routePatterns.put(p, new ArrayList<Tuple2<RoutePattern, Double>>());
                for (Matching m: results) {
                    if (p.routePatternId == m.routePatternId1) {
                        queryStr = "SELECT rp FROM RoutePattern rp WHERE ( routePatternId = " + m.routePatternId2 + ")";
                    }
                    else {
                        queryStr = "SELECT rp FROM RoutePattern rp WHERE ( routePatternId = " + m.routePatternId1 + ")";
                    }
                    TypedQuery<RoutePattern> routePatternsQuery2 = JPA.em().createQuery(queryStr, RoutePattern.class);
                    List<RoutePattern> results2 = routePatternsQuery2.getResultList();
                    for (RoutePattern rp: results2) {
                        routePatterns.get(p).add(new Tuple2<RoutePattern, Double>(rp, m.value));
                    }
                }
            }

        }

//        matchings = new ArrayList<>();
//        routePatterns = new ArrayList<>();
//        String queryStr = "SELECT m FROM Matching m WHERE (userId1 = " + userId + " OR userId2 = " + userId + ")";
//        TypedQuery<Matching> matchingsQuery = JPA.em().createQuery(queryStr, Matching.class);
//        for (Matching m: matchingsQuery.getResultList()) {
//            matchings.add(m);
//            if (userId == m.userId1) {
//                queryStr = "SELECT rp FROM RoutePattern rp WHERE (routePatternId = " + m.routePatternId2 + ")";
//            }
//            else {
//                queryStr = "SELECT rp FROM RoutePattern rp WHERE (routePatternId = " + m.routePatternId1 + ")";
//            }
//
//            TypedQuery<RoutePattern> routePatternsQuery = JPA.em().createQuery(queryStr, RoutePattern.class);
//
//            for (RoutePattern p: routePatternsQuery.getResultList()) {
//                routePatterns.add(p);
//            }
//        }
//        if (matchings.size() != routePatterns.size()) {
//            System.out.println("Matchings do not match routes!!!!");
//            matchings = new ArrayList<>();
//            routePatterns = new ArrayList<>();
//        }

    }


}
