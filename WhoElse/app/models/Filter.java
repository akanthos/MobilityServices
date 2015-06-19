package models;

import com.fasterxml.jackson.databind.JsonNode;
import play.db.jpa.JPA;
import javax.persistence.*;
import java.util.*;

public class Filter {

    public List<Item> SelectedItems;
    public List<ItemProperty> Size;
    public List<ItemProperty> Color;
    public List<ItemProperty> Material;
    public List<ItemProperty> Brand;
    public List<ItemProperty> Types;
    public float MinPrice;
    public float MaxPrice;

    public Filter(Boolean selection) {

        Size = new ArrayList<ItemProperty>();
        TypedQuery<String> query = JPA.em().createQuery("SELECT DISTINCT i.dress_size FROM Item AS i ORDER BY dress_size", String.class);
        for (String item : query.getResultList()) {
            ItemProperty newProperty = new ItemProperty(item, selection);
            Size.add(newProperty);
        }

        Color = new ArrayList<ItemProperty>();
        query = JPA.em().createQuery("SELECT DISTINCT i.color FROM Item AS i ORDER BY color", String.class);
        for (String item : query.getResultList()) {
            ItemProperty newProperty = new ItemProperty(item, selection);
            Color.add(newProperty);
        }

        Material = new ArrayList<ItemProperty>();
        query = JPA.em().createQuery("SELECT DISTINCT i.material FROM Item AS i ORDER BY material", String.class);
        for (String item : query.getResultList()) {
            ItemProperty newProperty = new ItemProperty(item, selection);
            Material.add(newProperty);
        }

        Brand = new ArrayList<ItemProperty>();
        query = JPA.em().createQuery("SELECT DISTINCT i.brand FROM Item AS i ORDER BY brand", String.class);
        for (String item : query.getResultList()) {
            ItemProperty newProperty = new ItemProperty(item, selection);
            Brand.add(newProperty);
        }

        Types = new ArrayList<ItemProperty>();
        query = JPA.em().createQuery("SELECT DISTINCT i.dress_type FROM Item AS i ORDER BY dress_type", String.class);
        for (String item : query.getResultList()) {
            ItemProperty newProperty = new ItemProperty(item, selection);
            Types.add(newProperty);
        }

        MinPrice = 0;
        MaxPrice = 9999;
    }

    public void applyFilter(JsonNode myjson) {

        int min_price;
        int max_price;
        String string_price;

        updateProperties(myjson, "size", this.Size);
        updateProperties(myjson, "color", this.Color);
        updateProperties(myjson, "material", this.Material);
        updateProperties(myjson, "brand", this.Brand);
        updateProperties(myjson, "types", this.Types);

        //update price properties
        if (!myjson.path("minPrice").asText().equals("")){
            this.MinPrice = Float.parseFloat(myjson.path("minPrice").asText());
        }
        else{
            this.MinPrice = 0;
        }

        if (!myjson.path("maxPrice").asText().equals("")){
            this.MaxPrice = Float.parseFloat(myjson.path("maxPrice").asText());
        }
        else{
            this.MaxPrice = 9999;
        }
    }

    public String createConstraints() {

        List<String> constraints = new ArrayList<String>();
        String stringResult;
        String priceconstraint;

        constraints.add(propertyConstraint("dress_size", this.Size));
        constraints.add(propertyConstraint("color", this.Color));
        constraints.add(propertyConstraint("material", this.Material));
        constraints.add(propertyConstraint("brand", this.Brand));
        constraints.add(propertyConstraint("dress_type", this.Types));
        priceconstraint = "((price_per_day >= " + this.MinPrice  + ") AND (price_per_day <= " + this.MaxPrice  + "))";
        constraints.add(priceconstraint);

        stringResult = combineConstraints(constraints);
        return stringResult;
    }

    private String combineConstraints(List<String> propertyStrings){

        String constraint;

        constraint = propertyStrings.get(0);

        for(int i = 1; i < propertyStrings.size(); i++) {
            if ( (!constraint.isEmpty()) && (!propertyStrings.get(i).isEmpty()) )
            {
                constraint += " AND ";
            }
            constraint += propertyStrings.get(i);
        }

        return constraint;
    }

    private String propertyConstraint(String propertyName, List<ItemProperty> myProperty){
        String constraints = "";
        int no_of_selected_items = 0;

        if (someSelected(myProperty))
        {
            for (ItemProperty itemproperty : myProperty) {
                if (itemproperty.selected) {
                    if (no_of_selected_items > 0)
                        constraints += " OR ";
                    constraints += "(i." + propertyName + " = '" + itemproperty.name + "')";
                    no_of_selected_items++;
                }
            }
            constraints = "(" + constraints + ")" ;
        }

        return constraints;
    }

    private void updateProperties(JsonNode myjson, String property, List<ItemProperty> propList){
        String a_value;

        Iterator<JsonNode> myIterator = myjson.path(property).elements();
        if(myIterator.hasNext()){
            while (myIterator.hasNext()) {
                a_value = myIterator.next().asText();
                for (ItemProperty itemProperty : propList) {
                    if (itemProperty.name.equals(a_value)) {
                        itemProperty.selected = true;
                    }
                }
            }
        }else{                                                  //has either one or no value
            a_value = myjson.path(property).asText();
            for (ItemProperty itemProperty : propList) {
                if (itemProperty.name.equals(a_value)) {
                    itemProperty.selected = true;
                }
            }
        }
    }

    private boolean someSelected(List<ItemProperty> propList){

        boolean someSelectedResult = false;

        for (ItemProperty itemProperty : propList) {
            if (itemProperty.selected == true)
                someSelectedResult = true;
        }

        return someSelectedResult;
    }

    public void updatePropertyNumber(){

        String stringResult, temp;
        List<String> constraints = new ArrayList<String>();
        List<String> propertyConstraints = new ArrayList<String>();

        propertyConstraints.add(propertyConstraint("dress_size", this.Size));
        propertyConstraints.add(propertyConstraint("color", this.Color));
        propertyConstraints.add(propertyConstraint("material", this.Material));
        propertyConstraints.add(propertyConstraint("brand", this.Brand));
        propertyConstraints.add(propertyConstraint("dress_type", this.Types));

        //Size
        constraints.clear();
        constraints.addAll(Arrays.asList( propertyConstraints.get(1), propertyConstraints.get(2), propertyConstraints.get(3), propertyConstraints.get(4)) );
        stringResult = combineConstraints(constraints);
        for (ItemProperty itemproperty : this.Size) {
            if (!stringResult.isEmpty())
                temp = "SELECT i.dress_size FROM Item AS i WHERE ( " + stringResult + " AND (dress_size = " + itemproperty.name + ") )";
            else
                temp = "SELECT i.dress_size FROM Item AS i WHERE (dress_size = " + itemproperty.name + ")";
            TypedQuery<String> query = JPA.em().createQuery(temp, String.class);
            itemproperty.no_of_items = query.getResultList().size();
        }

        //Color
        constraints.clear();
        constraints.addAll(Arrays.asList( propertyConstraints.get(0), propertyConstraints.get(2), propertyConstraints.get(3), propertyConstraints.get(4)) );
        stringResult = combineConstraints(constraints);
        for (ItemProperty itemproperty : this.Color) {
            if (!stringResult.isEmpty())
                temp = "SELECT i.color FROM Item AS i WHERE ( " + stringResult + " AND (color = '" + itemproperty.name + "') )";
            else
                temp = "SELECT i.color FROM Item AS i WHERE (color = '" + itemproperty.name + "')";
            TypedQuery<String> query = JPA.em().createQuery(temp, String.class);
            itemproperty.no_of_items = query.getResultList().size();
        }

        //Material
        constraints.clear();
        constraints.addAll(Arrays.asList( propertyConstraints.get(0), propertyConstraints.get(1), propertyConstraints.get(3), propertyConstraints.get(4)) );
        stringResult = combineConstraints(constraints);
        for (ItemProperty itemproperty : this.Material) {
            if (!stringResult.isEmpty())
                temp = "SELECT i.material FROM Item AS i WHERE ( " + stringResult + " AND (material = '" + itemproperty.name + "') )";
            else
                temp = "SELECT i.material FROM Item AS i WHERE (material = '" + itemproperty.name + "')";
            TypedQuery<String> query = JPA.em().createQuery(temp, String.class);
            itemproperty.no_of_items = query.getResultList().size();
        }

        //Brand
        constraints.clear();
        constraints.addAll(Arrays.asList( propertyConstraints.get(0), propertyConstraints.get(1), propertyConstraints.get(2), propertyConstraints.get(4)) );
        stringResult = combineConstraints(constraints);
        for (ItemProperty itemproperty : this.Brand) {
            if (!stringResult.isEmpty())
                temp = "SELECT i.brand FROM Item AS i WHERE ( " + stringResult + " AND (brand = '" + itemproperty.name + "') )";
            else
                temp = "SELECT i.brand FROM Item AS i WHERE (brand = '" + itemproperty.name + "')";
            TypedQuery<String> query = JPA.em().createQuery(temp, String.class);
            itemproperty.no_of_items = query.getResultList().size();
        }

        //Types
        constraints.clear();
        constraints.addAll(Arrays.asList( propertyConstraints.get(0), propertyConstraints.get(1), propertyConstraints.get(2), propertyConstraints.get(3)) );
        stringResult = combineConstraints(constraints);
        for (ItemProperty itemproperty : this.Types) {
            if (!stringResult.isEmpty())
                temp = "SELECT i.dress_type FROM Item AS i WHERE ( " + stringResult + " AND (dress_type = '" + itemproperty.name + "') )";
            else
                temp = "SELECT i.dress_type FROM Item AS i WHERE (dress_type = '" + itemproperty.name + "')";
            TypedQuery<String> query = JPA.em().createQuery(temp, String.class);
            itemproperty.no_of_items = query.getResultList().size();
        }
    }
}
