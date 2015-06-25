package controllers;

import models.MatchResponse;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class WhoElse extends Controller {

    public static Result main() {

        return ok(views.html.search.render());
    }

    @Transactional(readOnly = true)
    public static Result profile() {
        MatchResponse m = new MatchResponse(1);
        return ok(views.html.profile.render(m));
    }

    @Transactional(readOnly = true)
    public static Result search() {

        DynamicForm form = Form.form().bindFromRequest();

        //TODO
        //Store searches
        //Provide search results
        return RouteActions.search();
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
