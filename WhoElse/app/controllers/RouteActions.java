package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RouteActions extends Controller {

    @Transactional
    public static Result search() {

        DynamicForm form = Form.form().bindFromRequest();
        Search search = new Search();
        String address1, address2;
        String message = "";

        try{
            ArrayList<String> latlngloc1 = null;
            address1 = form.get("startAddress");
            if (!address1.equals("")){
                latlngloc1 = RouteActions.getLatLongLocality(address1);
                search.startAreaSubLoc = latlngloc1.get(2);
                search.startAreaLoc = latlngloc1.get(3);
            }else{
                search.startAreaSubLoc = address1;
                search.startAreaLoc = address1;
            }

            ArrayList<String> latlngloc2 = null;
            address2 = form.get("endAddress");
            if (!address2.equals("")){
                latlngloc2 = RouteActions.getLatLongLocality(address2);
                search.endAreaSubLoc = latlngloc2.get(2);
                search.endAreaLoc = latlngloc2.get(3);
            }else{
                search.endAreaSubLoc = address2;
                search.endAreaLoc = address2;
            }

            SearchResponse searchResponse = new SearchResponse();
            searchResponse.GetSearchResults(search.startAreaSubLoc, search.startAreaLoc, search.endAreaSubLoc, search.endAreaLoc);

            if ( (!address1.equals("")) && (!address2.equals(""))){
                search.save();
            }

            // TODO: Bring matches and forward them to search view
            MatchResponse matchResponse = new MatchResponse();
            if (latlngloc1 != null ) {
                RoutePattern dummy = new RoutePattern();
                dummy.startAddress = address1;
                dummy.endAddress = address2;
                dummy.startLat = Double.parseDouble(latlngloc1.get(0));
                dummy.startLong = Double.parseDouble(latlngloc1.get(1));
                if (latlngloc2 != null) {
                    dummy.endLat = Double.parseDouble(latlngloc2.get(0));
                    dummy.endLong = Double.parseDouble(latlngloc2.get(1));
                }
                else {
                    dummy.endLat = 0.0;
                    dummy.endLong = 0.0;
                }
                dummy.periodicity = "Daily";
                dummy.time = form.get("time");
                dummy.flexibility = Integer.parseInt(form.get("flexibility"));
                dummy.car = form.get("car");
                matchResponse = new MatchResponse(dummy);
            }
            return ok(views.html.search.render(searchResponse, matchResponse, message));
        }
        catch(Exception e){
            return ok(views.html.search.render(new SearchResponse(), new MatchResponse(), message));
        }
    }

    @Transactional
    public static Result deletePattern() {

        DynamicForm form = Form.form().bindFromRequest();
        Integer routePatternId = Integer.parseInt(form.get("patternId"));
        // TODO: delete pattern from DB
        String queryStr = "DELETE FROM RoutePattern WHERE (routePatternId = " + routePatternId + ")";
        Query matchingsQuery = JPA.em().createQuery(queryStr);
        int results = matchingsQuery.executeUpdate();
        flash("info", "Deleted query with ID: " + routePatternId);
        System.out.println("Deleted query with ID: " + routePatternId);

        queryStr = "DELETE FROM Matching WHERE (routePatternId1 = " + routePatternId + " OR routePatternId2 = " + routePatternId + ")";
        matchingsQuery = JPA.em().createQuery(queryStr);
        results = matchingsQuery.executeUpdate();

        return redirect(controllers.routes.WhoElse.profile());
    }


    @Transactional
    public static Result addPattern() {

        // Compute lat and long
        DynamicForm form = Form.form().bindFromRequest();

        RoutePattern pattern = new RoutePattern();

        if (!session().containsKey("whoelse_user_id")) {
            pattern.userId = Integer.parseInt(form.get("userId"));
        }
        else {
            Integer id = Integer.parseInt(session().get("whoelse_user_id").toString());
            System.out.println(id);
            pattern.userId = id;
        }

        pattern.startAddress = form.get("startAddress");
        ArrayList<String> latlng = getLatLongLocality(form.get("startAddress"));
        if (!latlng.isEmpty()) {
            pattern.startLat = Double.valueOf(latlng.get(0));
            pattern.startLong = Double.valueOf(latlng.get(1));
        }
        else {
            System.out.println("Start Address geolocation failed");
            return redirect(controllers.routes.WhoElse.profile());
        }


        pattern.endAddress = form.get("endAddress");
        latlng = getLatLongLocality(form.get("endAddress"));
        if (!latlng.isEmpty()) {
            pattern.endLat = Double.valueOf(latlng.get(0));
            pattern.endLong = Double.valueOf(latlng.get(1));
        }
        else {
            System.out.println("End Address geolocation failed");
            return redirect(controllers.routes.WhoElse.profile());
        }

        pattern.time = form.get("time");
        if (!valid(pattern.time)) {
            System.out.println("Time validation failed");
            return redirect(controllers.routes.WhoElse.profile());
        }
        pattern.flexibility = Integer.parseInt(form.get("flexibility"));
        pattern.date = "";
        pattern.punctuality = 0.0;
        pattern.periodicity = form.get("periodicity");
        pattern.car = form.get("car");

        pattern.save();
        System.out.println("Saved pattern with ID: " + pattern.routePatternId);
        pattern.updateMatchings();

        return redirect(controllers.routes.WhoElse.profile());
    }

    public static boolean valid(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); //HH = 24h format
        dateFormat.setLenient(false); //this will not enable 25:67 for example
        try {
            dateFormat.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static ArrayList<String> getLatLongLocality(String address) {
        ArrayList<String> ret= new ArrayList<>();
        HttpURLConnection conn = null;
        try {
            String encodedUrl = null;
            try {
                encodedUrl = URLEncoder.encode(address, "UTF-8");
            } catch (UnsupportedEncodingException ignored) {
                // Can be safely ignored because UTF-8 is always supported
            }
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+encodedUrl);
            //+ URIUtil.encodeQuery("Sayaji Hotel, Near balewadi stadium, pune") + "&sensor=true");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "", full = "";
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                full += output;
            }

            JsonNode root = Json.parse(full);
            Iterator<JsonNode> results = root.path("results").elements();
            JsonNode first = results.next();
            //get latitude and longitude
            String lat = first.path("geometry").path("location").path("lat").asText();
            String lng = first.path("geometry").path("location").path("lng").asText();
            ret.add(lat);
            ret.add(lng);

            //get locality
            results = first.path("address_components").elements();
            JsonNode second;
            Iterator<JsonNode> types;
            String type;
            String locality = "";
            String sublocality = "";

            while (results.hasNext()) {
                second = results.next();
                types = second.path("types").elements();
                while (types.hasNext()) {
                    type = types.next().asText();
                    if (type.equals("locality")){
                        locality = second.path("long_name").asText();
                    }else if (type.equals("sublocality")){
                        sublocality = second.path("long_name").asText();
                    }
                }
            }

            ret.add(sublocality);
            ret.add(locality);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
            return ret;
        }
    }
}