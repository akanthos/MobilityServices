package controllers;

import models.MatchResponse;
import models.SearchResponse;
import models.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class WhoElse extends Controller {

    @Transactional
    public static Result main() {

        SearchResponse response = new SearchResponse();

        return ok(views.html.search.render(response));
    }

    @Transactional(readOnly = true)
    public static Result profile() {
        MatchResponse m = new MatchResponse();
        if (session().containsKey("whoelse_user_id")) {
            Integer userId = Integer.parseInt(session().get("whoelse_user_id"));
            m = new MatchResponse(userId);
        }
        return ok(views.html.profile.render(m));
    }

    @Transactional(readOnly = true)
    public static Result userProfile(Integer userId) {

        User u = User.findById(userId);

        return ok(views.html.userProfile.render(u));
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
}
