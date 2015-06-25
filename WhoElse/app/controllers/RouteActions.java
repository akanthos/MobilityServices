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
import java.util.ArrayList;
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
        pattern.userId = Integer.parseInt(form.get("userId"));

        pattern.startAddress = form.get("startAddress");
        ArrayList<Double> latlng = getLatLong(form.get("startAddress"));
        pattern.startLat = latlng.get(0);
        pattern.startLong = latlng.get(1);

        pattern.endAddress = form.get("endAddress");
        latlng = getLatLong(form.get("endAddress"));
        pattern.endLat = latlng.get(0);
        pattern.endLong = latlng.get(1);


        pattern.time = form.get("time");
        pattern.date = "";
        pattern.punctuality = 0.0;
        pattern.periodicity = form.get("periodicity");

        pattern.save();
        // TODO: Update Matches
        // select all patterns and compare start with each other,
        // suppose all patterns are daily
        // routePatterdId1, routePatternId2 unique in DB, with try catch
        return redirect(controllers.routes.WhoElse.profile());
    }

    private static ArrayList<Double> getLatLong(String address) {
        ArrayList<Double> ret = new ArrayList<>();

        try {
            String encodedUrl = null;
            try {
                encodedUrl = URLEncoder.encode(address, "UTF-8");
            } catch (UnsupportedEncodingException ignored) {
                // Can be safely ignored because UTF-8 is always supported
            }
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+encodedUrl);
            //+ URIUtil.encodeQuery("Sayaji Hotel, Near balewadi stadium, pune") + "&sensor=true");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            ret = new ArrayList<Double>();
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
//            conn.disconnect();
            return ret;
        }
    }
}