package models;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import play.db.jpa.JPA;
import scala.Tuple2;
import scala.util.parsing.combinator.testing.Str;

import javax.persistence.TypedQuery;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Thanasis on 23/6/2015.
 */
public class MatchResponse {

    public Map<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>> routePatterns;
    public Map<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>> subscriptions;
    public ArrayList<RoutePattern> otherPatterns;

    public MatchResponse() {
        routePatterns = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
        subscriptions = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
        otherPatterns = new ArrayList<RoutePattern>();
    }

    public MatchResponse(RoutePattern searchPattern) {
        routePatterns = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
        subscriptions = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
        otherPatterns = new ArrayList<RoutePattern>();
        System.out.println("Search");
        String carQuery;
        if (searchPattern.car.equals("No")) {
            carQuery = " WHERE (car = 'Yes'";
            if (searchPattern.userId != -1) {
                carQuery = carQuery + " AND userId != " + searchPattern.userId;
            }
            carQuery = carQuery + ")";
        }
        else {
            carQuery = "";
        }
        String queryStr = "SELECT rp FROM RoutePattern rp" + carQuery;
        TypedQuery<RoutePattern> query = JPA.em().createQuery(queryStr, RoutePattern.class);
        List<RoutePattern> results = query.getResultList();
        for (RoutePattern p: results) {
            Double overhead = searchPattern.overhead(p);
            System.out.println("Overhead: " + overhead);
            if (searchPattern.isSimilarEnough(p, overhead)) {
                if (p.type.equals("pattern")) {
                    if (routePatterns.isEmpty()) {
                        routePatterns.put(searchPattern, new ArrayList<Tuple2<RoutePattern, Double>>());
                    }
                    routePatterns.get(searchPattern).add(new Tuple2<RoutePattern, Double>(p, overhead));
                }
                else {
                    if (subscriptions.isEmpty()) {
                        subscriptions.put(searchPattern, new ArrayList<Tuple2<RoutePattern, Double>>());
                    }
                    subscriptions.get(searchPattern).add(new Tuple2<RoutePattern, Double>(p, overhead));
                }

            }
        }
        if (!routePatterns.isEmpty()){
            Collections.sort(routePatterns.get(searchPattern), new Comparator<Tuple2<RoutePattern, Double>>() {
                @Override
                public int compare(Tuple2<RoutePattern, Double> t1, Tuple2<RoutePattern, Double> t2) {

                    return t2._2().compareTo(t1._2());
                }
            });
        }
        if (!subscriptions.isEmpty()) {
            Collections.sort(subscriptions.get(searchPattern), new Comparator<Tuple2<RoutePattern, Double>>() {
                @Override
                public int compare(Tuple2<RoutePattern, Double> t1, Tuple2<RoutePattern, Double> t2) {

                    return t2._2().compareTo(t1._2());
                }
            });
        }
    }

    public MatchResponse(Integer userId) {
        routePatterns = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
        subscriptions = new HashMap<RoutePattern, ArrayList<Tuple2<RoutePattern, Double>>>();
        otherPatterns = new ArrayList<RoutePattern>();

        String queryStr = "SELECT rp FROM RoutePattern rp WHERE (type = 'pattern' AND userId = " + userId + ")";
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
                Collections.sort(routePatterns.get(p), new Comparator<Tuple2<RoutePattern, Double>>() {
                    @Override
                    public int compare(Tuple2<RoutePattern, Double> t1, Tuple2<RoutePattern, Double> t2) {

                        return t2._2().compareTo(t1._2());
                    }
                });
            }
            else{
                otherPatterns.add(p);
            }
        }

    }


}
