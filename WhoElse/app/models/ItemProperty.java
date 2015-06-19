package models;

public class ItemProperty {

    public String name;
    public Boolean selected;
    public Integer no_of_items;

    public ItemProperty(String name, Boolean selection) {
        this.name = name;
        this.no_of_items = 0;
        if (selection)
            this.selected = true;
        else
            this.selected = false;
    }
}