package controllers;

import models.MatchResponse;
import models.RoutePattern;
import models.Search;
import models.SearchResponse;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;

public class WhoElse extends Controller {

    @Transactional
    public static Result main() {

        SearchResponse searchResponse = new SearchResponse();
        MatchResponse matchResponse = new MatchResponse();

        return ok(views.html.search.render(searchResponse, matchResponse));
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
