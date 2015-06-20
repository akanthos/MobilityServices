package controllers;

import play.data.DynamicForm;
import play.data.Form;
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

        DynamicForm form = Form.form().bindFromRequest();

        return RouteActions.search();
    }

}
