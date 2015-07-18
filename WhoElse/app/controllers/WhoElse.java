package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.TypedQuery;
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
        List<RoutePattern> pat_list = RoutePattern.getOnlyRoutePatternByUserId(userId);

        return ok(views.html.userProfile.render(u, notif_list, pat_list));
    }

    @Transactional
    public static Result request(Integer userId, Integer patternId1, Integer patternId2) {

        //send notification to driver of pattern
        User u = User.findById(Integer.parseInt(session().get("whoelse_user_id")));
        String user = u.firstName + " " + u.lastName;
        RoutePattern r1 = RoutePattern.getRoutePatternById(patternId1);
        String msg = user + " has requested to share the ride from " + r1.startAddress + " to " + r1.endAddress;

        Notification n = new Notification(userId, Integer.parseInt(session().get("whoelse_user_id")), "Request", msg, patternId1, patternId2);
        n.save();

        flash("info", "Request has been sent successfully");

        return redirect(controllers.routes.WhoElse.search());
    }

    @Transactional
    public static Result acceptNotification(Integer notificationId, Integer patternId1, Integer patternId2) {
        //send notification to driver of pattern
        Notification n = Notification.getNotificationsById(notificationId);
        Notification.updateNotificationAsAnswered(notificationId);

        if (!n.nType.equals("Info") && !n.nType.equals("Message")) {
            User fromUser = User.findById(n.from_userId);
            User toUser = User.findById(n.to_userId);
            String user = toUser.firstName + " " + toUser.lastName;
            RoutePattern r1 = RoutePattern.getRoutePatternById(patternId1);
            String msg = user + " has accepted to share the ride from " + r1.startAddress + " to " + r1.endAddress;

            Notification answer = new Notification(fromUser.userId, toUser.userId, "Info", msg, patternId1, patternId2);
            answer.save();

            // Store matching as active
            Matching match = Matching.getMatchingByRouteIds(patternId1, patternId2);
            match.active = 1;
            match.update();


            flash("info", "Rideshare acceptance was sent successfully");
        }
        return redirect(controllers.routes.WhoElse.userProfile(n.to_userId));
    }

    @Transactional
    public static Result declineNotification(Integer notificationId, Integer patternId1, Integer patternId2) {
        //send notification to driver of pattern
        Notification n = Notification.getNotificationsById(notificationId);

        Notification.updateNotificationAsAnswered(notificationId);
        User fromUser = User.findById(n.from_userId);
        User toUser = User.findById(n.to_userId);
        String user = toUser.firstName + " " + toUser.lastName;
        RoutePattern r1 = RoutePattern.getRoutePatternById(patternId1);
        String msg = user + " has declined to share the ride from " + r1.startAddress + " to " + r1.endAddress;

        Notification answer = new Notification(fromUser.userId, toUser.userId, "Info", msg, patternId1, patternId2);
        answer.save();

        flash("info", "Rideshare declining was sent successfully");
        return redirect(controllers.routes.WhoElse.userProfile(n.to_userId));
    }

    @Transactional
    public static Result message(Integer userId, Integer sourceNotificationId) {

        //send message to driver of pattern
        DynamicForm form = Form.form().bindFromRequest();
        String msg = form.get("message");

        Notification n = new Notification(userId, Integer.parseInt(session().get("whoelse_user_id")), "Message", msg, 0, 0);
        n.save();

        if (sourceNotificationId != -1) {
            Notification n2 = Notification.getNotificationsById(sourceNotificationId);
            n2.answered = 1;
            n2.update();
        }

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
