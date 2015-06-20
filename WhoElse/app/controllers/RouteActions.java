package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class RouteActions extends Controller {

//    @Transactional
//    public static Result createItem() {
//        DynamicForm form = Form.form().bindFromRequest();
//
//        Item item = new Item();
//        item.brand = form.get("brand");
//        item.description = form.get("description");
//        item.color = form.get("color");
//        item.material = form.get("material");
//        if (form.get("price") != null) {
//            item.price_per_day = Float.parseFloat(form.get("price"));
//        }
//        item.dress_size = form.get("size");
//        item.dress_type = form.get("type");
//
//        item.save();
//
//        flash("success", "Your item was successfully added. Thank you!");
//        return redirect(controllers.routes.ItemActions.addItem());
//    }

    @Transactional(readOnly = true)
    public static Result search() {

        //return ok(views.html.search.render(filter, successMessage));
        return ok(views.html.search.render());
    }

//    @Transactional(readOnly = true)
//    public static Result searchFiltered() {
//
//        String constraints;
//        String successMessage = flash("success");
//        DynamicForm form = Form.form().bindFromRequest();
//        JsonNode json_packet = Json.parse(form.get("jsonPacket"));
//
//        Filter filter = new Filter(false);
//        filter.applyFilter(json_packet);
//        constraints = filter.createConstraints();
//        filter.updatePropertyNumber();
//
//        try{
//            filter.SelectedItems = Item.filterItems(constraints);
//        }
//        catch(Exception ex){
//            filter.SelectedItems = Item.findAll();
//        }
//
//        return ok(views.html.explore.render(filter, successMessage));
//    }
}
