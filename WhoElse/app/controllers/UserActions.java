package controllers;

import models.Filter;
import play.data.DynamicForm;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import models.User;
import scala.collection.concurrent.Debug;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import static play.data.Form.form;

public class UserActions extends Controller {


    public static Result getSignUpPage() {
        String successMessage = flash("success");
        return ok(views.html.signup.render("getSignUpPage", successMessage));
    }

    @Transactional
    public static Result postNewUser() {
        DynamicForm form = form().bindFromRequest();

        User user = new models.User();
        user.firstname = form.get("firstname");
        user.lastname = form.get("lastname");
        user.email = form.get("email");
        user.password_hash = org.apache.commons.codec.digest.DigestUtils.shaHex(form.get("password"));

        user.save();

        flash("success", user.firstname+" thank you for joining the dressando community. We will get you dressed very soon. An email with the activation link has been sent to "+user.email+".");
        return redirect(controllers.routes.UserActions.getSignUpPage());
    }

    @Transactional(readOnly = true)
    public static Result authenticate(){
        DynamicForm form = form().bindFromRequest();

        LoginData loginData = new LoginData();
        loginData.email = form.get("email");
        loginData.password_hash = org.apache.commons.codec.digest.DigestUtils.shaHex(form.get("password"));

        TypedQuery<String> query = JPA.em().createQuery("SELECT u.password_hash FROM User AS u WHERE u.email=:email",String.class);
        query.setParameter("email",loginData.email);

        boolean authenticated = false;

        for(String object : query.getResultList()){
            if(object.equalsIgnoreCase(loginData.password_hash)){
                authenticated = true;
                break;
            }
        }

        if(authenticated){
            return redirect(controllers.routes.Dressando.explore());
        }else {
            return redirect(controllers.routes.UserActions.getSignUpPage());
        }
    }

    public static class LoginData{
        public String email;
        public String password_hash;
    }


}
