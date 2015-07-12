package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class WhoElse extends Controller {

    @Transactional
    public static Result main() {

        return ok(views.html.index.render());
    }

    @Transactional (readOnly = true)
    public static Result search() {

        DynamicForm form = Form.form().bindFromRequest();

        checkForNotifications();
        String message = "";
        SearchResponse searchResponse = new SearchResponse();
        MatchResponse matchResponse = new MatchResponse();

        //TODO: run a search with current data and give a meaningful result

        return ok(views.html.search.render(searchResponse, matchResponse, message, form));
    }

    @Transactional(readOnly = true)
    public static Result profile() {

        checkForNotifications();
        MatchResponse m = new MatchResponse();
        String message = "";

        if (session().containsKey("whoelse_user_id")) {
            Integer userId = Integer.parseInt(session().get("whoelse_user_id"));
            m = new MatchResponse(userId);
        }
        return ok(views.html.profile.render(m, message));
    }

    @Transactional
    public static Result userProfile(Integer userId) {

        User u = User.findById(userId);
        Notification.updateNotificationsAsSeen(userId);
        session("whoelse_notifications", "0");

        List<Notification> notif_list = Notification.getNotificationsByUserId(userId);
        List<RoutePattern> pat_list = RoutePattern.getRoutePatternsByUserId(userId);

        return ok(views.html.userProfile.render(u, notif_list, pat_list));
    }

    @Transactional
    public static Result request(Integer userId, Integer patternId) {

        //send notification to driver of pattern
        User u = User.findById(Integer.parseInt(session().get("whoelse_user_id")));
        String user = u.firstName + " " + u.lastName;
        RoutePattern r = RoutePattern.getRoutePatternById(patternId);
        String msg = user + " has requested to share the ride from " + r.startAddress + " to " + r.endAddress;

        Notification n = new Notification(userId, Integer.parseInt(session().get("whoelse_user_id")), "Request", msg);
        n.save();

        flash("info", "Request has been sent successfully");

        return redirect(controllers.routes.WhoElse.search());
    }

    @Transactional
    public static Result message(Integer userId) {

        //send message to driver of pattern
        DynamicForm form = Form.form().bindFromRequest();
        String msg = form.get("message");

        Notification n = new Notification(userId, Integer.parseInt(session().get("whoelse_user_id")), "Message", msg);
        n.save();

        flash("info", "Message has been sent successfully");

        return redirect(controllers.routes.WhoElse.search());
    }

    public static Result logout() {

        session().clear();

        return redirect(controllers.routes.WhoElse.main());
    }

    public static Result getSignUpPage() {

        return ok(views.html.signup.render(""));

    }

    public static Result getLoginPage() {

        return ok(views.html.login.render(""));

    }

    public static void checkForNotifications(){
        if (session().containsKey("whoelse_user_id")){
            Integer notif = Notification.getNotificationsNotSeen(Integer.valueOf(session().get("whoelse_user_id")));

            session("whoelse_notifications", notif.toString());
            if (notif > 0){
                flash("notification", "You have " + notif + " new notification(s)");
            }
        }
    }
}
