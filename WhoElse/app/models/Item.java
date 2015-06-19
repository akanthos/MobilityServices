package models;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue
    public Integer id;

    public String description;
    public Integer user_id;
    public String dress_size;
    public String color;
    public String material;
    public String brand;
    public String dress_type;
    public Float price_per_day;

    public void save() {

        JPA.em().persist(this);
    }

    public static List<Item> findAll() {

        TypedQuery<Item> query = JPA.em().createQuery("SELECT i FROM Item i", Item.class);
        return query.getResultList();
    }

    public static List<Item> filterItems(String constraints) {

        String string_query = "SELECT i FROM Item i WHERE " + constraints;

        TypedQuery<Item> query = JPA.em().createQuery(string_query, Item.class);
        return query.getResultList();

    }

    public static List<Item> numberOfItems(String constraints) {

        String string_query = "SELECT i FROM Item i WHERE " + constraints;

        TypedQuery<Item> query = JPA.em().createQuery(string_query, Item.class);
        return query.getResultList();

    }

}