package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    public Integer notificationId;

    public Integer userId;
    public String nType;
    public String message;
    public Integer seen;
    public Integer answered;

    public Notification(){ }

    public Notification(Integer id, String nTyp, String mes){
        this.userId = id;
        this.nType = nTyp;
        this.message = mes;
        this.seen = 0;
        this.answered = 0;
    }

    public static List<Notification> getNotificationsByUserId(Integer user_id) {

        String string_query = "SELECT n FROM Notification n WHERE userId    = " + user_id;
        TypedQuery<Notification> query = JPA.em().createQuery(string_query, Notification.class);
        return query.getResultList();
    }

    public static Integer getNotificationsNotSeen(Integer user_id) {

        String string_query = "SELECT n FROM Notification n WHERE (seen = 0) AND (userId = " + user_id + ")";
        TypedQuery<Notification> query = JPA.em().createQuery(string_query, Notification.class);
        return query.getResultList().size();
    }

    public static void updateNotificationsAsSeen(Integer user_id) {

        Query query = JPA.em().createQuery("UPDATE Notification SET seen = 1 WHERE userId = " + user_id.toString());
        query.executeUpdate();
    }

    public static Notification getNotificationsById(Integer not_id) {

        String string_query = "SELECT n FROM Notification n WHERE notificationId    = " + not_id;
        TypedQuery<Notification> query = JPA.em().createQuery(string_query, Notification.class);
        return query.getSingleResult();
    }

    public void save() {
        JPA.em().persist(this);
    }

    public void update() {
        JPA.em().merge(this);
    }
}
