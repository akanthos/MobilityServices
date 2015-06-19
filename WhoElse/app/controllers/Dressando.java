package controllers;

import models.Filter;
import models.Item;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.ok;

public class Dressando {

    public static Result lander() {

        return ok(views.html.lander.render("Lander"));
    }


    @Transactional(readOnly = true)
    public static Result explore() {

        return ItemActions.searchItems();
    }

    public static Result lend() {

        return ok(views.html.lend.render("lend", null));
    }

    public static Result profile() {

        return ok(views.html.profile.render("profile"));
    }

    public static Result impressum() {

        return ok(views.html.impressum.render("impressum"));
    }
}
