package controllers;

import play.db.jpa.Transactional;
import play.mvc.Result;
import static play.mvc.Results.ok;

public class WhoElse {

    public static Result main() {

        return ok(views.html.search.render());
    }

    public static Result profile() {

        return ok(views.html.profile.render());
    }

    @Transactional(readOnly = true)
    public static Result search() {

        return RouteActions.search();
    }
}
