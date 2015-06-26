package controllers;

import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.TypedQuery;

public class UserActions extends Controller {

    @Transactional
    public static Result addNewUser()
    {
        DynamicForm form = Form.form().bindFromRequest();

        try
        {
            User user = new models.User();
            user.username = form.get("username");
            user.firstName = form.get("firstname");
            user.lastName = form.get("lastname");
            user.company = form.get("company");
            user.email = form.get("email");
            user.username = form.get("username");
            user.password = org.apache.commons.codec.digest.DigestUtils.shaHex(form.get("password"));
            user.balance = 0.0;

            user.save();

            user = User.findByUsername(user.username);

            session().clear();
            session("whoelse_user", user.firstName + " " + user.lastName);
            session("whoelse_user_id", user.userId.toString());

            return redirect(controllers.routes.WhoElse.main());
        }
        catch (Exception ex)
        {
            return ok(views.html.signup.render("An Error Occurred"));
        }
    }

    @Transactional(readOnly = true)
    public static Result authenticate()
    {
        User user;
        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String pass = org.apache.commons.codec.digest.DigestUtils.shaHex(form.get("password"));

        try
        {
            String query = "SELECT u FROM User u WHERE username = '" + username + "')";
            TypedQuery<User> query_result = JPA.em().createQuery(query, User.class);
            user = query_result.getSingleResult();

            if (pass.equals(user.password)) {
                session().clear();
                session("whoelse_user", user.firstName + " " + user.lastName);
                session("whoelse_user_id", user.userId.toString());
                return redirect(controllers.routes.WhoElse.profile());
            } else {
                return ok(views.html.login.render("Wrong Password"));
            }
        }
        catch (Exception ex)
        {
            return ok(views.html.login.render("An Error Occurred"));
        }
    }
}
