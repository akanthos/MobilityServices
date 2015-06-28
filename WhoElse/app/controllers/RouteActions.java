package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.RoutePattern;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RouteActions extends Controller {

    @Transactional(readOnly = true)
    public static Result search() {

        return ok(views.html.search.render());
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
        ArrayList<Double> latlng = getLatLong(form.get("startAddress"));
        if (!latlng.isEmpty()) {
            pattern.startLat = latlng.get(0);
            pattern.startLong = latlng.get(1);
        }
        else {
            System.out.println("Start Address geolocation failed");
            return redirect(controllers.routes.WhoElse.profile());
        }


        pattern.endAddress = form.get("endAddress");
        latlng = getLatLong(form.get("endAddress"));
        if (!latlng.isEmpty()) {
            pattern.endLat = latlng.get(0);
            pattern.endLong = latlng.get(1);
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

        pattern.date = "";
        pattern.punctuality = 0.0;
        pattern.periodicity = form.get("periodicity");

        pattern.save();
        System.out.println("Saved pattern with ID: " + pattern.routePatternId);
        pattern.updateMatchings();

        // TODO: Update Matches
        // select all patterns and compare start with each other,
        // suppose all patterns are daily
        // routePatterdId1, routePatternId2 unique in DB, with try catch
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

    private static ArrayList<Double> getLatLong(String address) {
        ArrayList<Double> ret= new ArrayList<>();
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
            String lat = first.path("geometry").path("location").path("lat").asText();
            String lng = first.path("geometry").path("location").path("lng").asText();
            ret.add(Double.valueOf(lat));
            ret.add(Double.valueOf(lng));

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