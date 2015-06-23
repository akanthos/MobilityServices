package controllers;

import models.MatchResponse;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;


import static play.mvc.Results.ok;

public class WhoElse {

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

        return RouteActions.search();
    }

}
