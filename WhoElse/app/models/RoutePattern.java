package models;

import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RoutePattern {

    @Id
    @GeneratedValue
    public Integer routePatternId;
    public Integer userId;
    public String request_type;
    public String startAddress;
    public String endAddress;
    public Double startLat;
    public Double startLong;
    public Double endLat;
    public Double endLong;
    public String time;
    public Integer flexibility;
    public String date;
    public Double punctuality;
    public String periodicity;
    public String car;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public static List<RoutePattern> getRoutePatternsByUserId(Integer user_id){
        String string_query = "SELECT r FROM RoutePattern r WHERE userId    = " + user_id;
        TypedQuery<RoutePattern> query = JPA.em().createQuery(string_query, RoutePattern.class);
        return query.getResultList();
    }

    public static RoutePattern getRoutePatternById(Integer r_id){
        String string_query = "SELECT r FROM RoutePattern r WHERE routePatternId    = " + r_id;
        TypedQuery<RoutePattern> query = JPA.em().createQuery(string_query, RoutePattern.class);
        return query.getSingleResult();
    }

    public void updateMatchings() {
        List<Matching> matchings = new ArrayList<>();
        List<Matching> routePatterns = new ArrayList<>();

        String carQuery;
        if (car.equals("No")) {
            carQuery = " AND car = 'Yes'";
        }
        else {
            carQuery = "";
        }

        String queryStr = "SELECT rp FROM RoutePattern rp WHERE (request_type = 'pattern' AND userId != " + userId + carQuery + ")";
        TypedQuery<RoutePattern> query = JPA.em().createQuery(queryStr, RoutePattern.class);
        for (RoutePattern p: query.getResultList()) {
            Double overhead = overhead(p);
            System.out.println("overhead: " + overhead);
            if (isSimilarEnough(p, overhead)) {
                Matching m = new Matching();
                m.userId1 = userId;
                m.userId2 = p.userId;
                m.routePatternId1 = routePatternId;
                m.routePatternId2 = p.routePatternId;
                m.value = overhead;
                m.save();
                System.out.println("Saved matching: ");
                System.out.println("ID: " + m.matchingId);
                System.out.println("rpID1: " + m.routePatternId1);
                System.out.println("rpID2: " + m.routePatternId2);
                System.out.println("overhead: " + m.value);
            }
        }
    }

    public Double overhead(RoutePattern p) {

        Double startDistance = distance(this.startLat, p.startLat, this.startLong, p.startLong);
        Double endDistance = distance(this.endLat, p.endLat, this.endLong, p.endLong);
        Double aloneDistance = distance(startLat, endLat, startLong, endLong);
        Double rideshareDistance = distance(p.startLat, p.endLat, p.startLong, p.endLong);
        return aloneDistance / (startDistance + rideshareDistance + endDistance);
    }
    public boolean isSimilarEnough(RoutePattern p, Double overhead) {

        System.out.println("Dummy: " + periodicity + " and pattern: " + p.periodicity );
        if (p.request_type.equals("pattern")) {
            if (!periodicity.equals(p.periodicity)) return false;
        }
        Double startDistance = distance(this.startLat, p.startLat, this.startLong, p.startLong);
        Double endDistance = distance(this.endLat, p.endLat, this.endLong, p.endLong);
        if (this.endLat == 0.0 && this.endLong == 0.0) {
            if (startDistance > 5000) return false;
        }
        else {
            if (startDistance > 5000 || endDistance > 5000) return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {

            Date myDate = sdf.parse(time);
            Date pDate = sdf.parse(p.time);
            Long diff = Math.abs(myDate.getTime() - pDate.getTime()); // in Milliseconds
            Long minutes = diff / (60 * 1000);
            System.out.println("Minutes: " + minutes );
            System.out.println("Overhead: " + overhead);
            boolean ret = ( minutes <= 30 &&  overhead >= 0.5 );
            ret = ( minutes <= new Long(flexibility) &&  overhead >= 0.5 );
            System.out.println(ret);
            return ret;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; //( overhead >= 0.8 );

    }
    public static double distance(Double lat1, Double lat2, Double lon1,
                                  Double lon2/*, Double el1, Double el2*/) {
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c * 1000; // convert to meters

//        Double height = el1 - el2;

        distance = Math.pow(distance, 2) /*+ Math.pow(height, 2)*/;

        return Math.sqrt(distance); // Returns distance in Meters
    }
}