package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    public Integer notificationId;

    public Integer to_userId;
    public Integer from_userId;
    public String nType;
    public String message;
    public Integer patternId1;
    public Integer patternId2;
    public Integer seen;
    public Integer answered;

    public Notification(){ }

    public Notification(Integer to_u, Integer from_u, String nTyp, String mes, Integer patternId1, Integer patternId2){
        this.to_userId = to_u;
        this.from_userId = from_u;
        this.nType = nTyp;
        this.message = mes;
        this.patternId1 = patternId1;
        this.patternId2 = patternId2;
        this.seen = 0;
        this.answered = 0;
    }

    public static List<Notification> getNotificationsByUserId(Integer user_id) {

        String string_query = "SELECT n FROM Notification n WHERE (to_userId    = " + user_id + "AND answered = 0)";
        TypedQuery<Notification> query = JPA.em().createQuery(string_query, Notification.class);
        return query.getResultList();
    }

    public static Integer getNotificationsNotSeen(Integer user_id) {

        String string_query = "SELECT n FROM Notification n WHERE (seen = 0) AND (to_userId = " + user_id + ")";
        TypedQuery<Notification> query = JPA.em().createQuery(string_query, Notification.class);
        List<Notification> res = query.getResultList();
        return res.size();
    }

    public static void updateNotificationsAsSeen(Integer user_id) {

        Query query = JPA.em().createQuery("UPDATE Notification SET seen = 1 WHERE to_userId = " + user_id.toString());
        query.executeUpdate();
    }

    public static void updateNotificationAsAnswered(Integer targetNotificationId) {

        Query query = JPA.em().createQuery("UPDATE Notification SET answered = 1 WHERE notificationId = " + targetNotificationId);
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
