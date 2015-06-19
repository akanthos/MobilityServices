package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Filter;
import models.Item;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class ItemActions extends Controller {

    @Transactional
    public static Result addItem() {
//
//        Item newItem = Json.fromJson(request().body().asJson(), Item.class);
//        newItem.save();
//        return created(Json.toJson(newItem));
//
//        return ok(addItem.render(addItem));
        String successMessage = flash("success");
        return ok(views.html.lend.render("lend", successMessage));
    }

    @Transactional
    public static Result createItem() {
        DynamicForm form = Form.form().bindFromRequest();

        Item item = new Item();
        item.brand = form.get("brand");
        item.description = form.get("description");
        item.color = form.get("color");
        item.material = form.get("material");
        if (form.get("price") != null) {
            item.price_per_day = Float.parseFloat(form.get("price"));
        }
        item.dress_size = form.get("size");
        item.dress_type = form.get("type");

        item.save();

        flash("success", "Your item was successfully added. Thank you!");
        return redirect(controllers.routes.ItemActions.addItem());
    }

    @Transactional(readOnly = true)
    public static Result searchItems() {

        String successMessage = flash("success");
        Filter filter = new Filter(false);
        filter.SelectedItems = Item.findAll();
        filter.updatePropertyNumber();

        return ok(views.html.explore.render(filter, successMessage));
    }

    @Transactional(readOnly = true)
    public static Result searchFiltered() {

        String constraints;
        String successMessage = flash("success");
        DynamicForm form = Form.form().bindFromRequest();
        JsonNode json_packet = Json.parse(form.get("jsonPacket"));

        Filter filter = new Filter(false);
        filter.applyFilter(json_packet);
        constraints = filter.createConstraints();
        filter.updatePropertyNumber();

        try{
            filter.SelectedItems = Item.filterItems(constraints);
        }
        catch(Exception ex){
            filter.SelectedItems = Item.findAll();
        }

        return ok(views.html.explore.render(filter, successMessage));
    }
}
