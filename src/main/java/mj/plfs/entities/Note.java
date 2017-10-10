package mj.plfs.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Mikołaj on 2017-10-08.
 */
@Entity
@Table(name="NOTES")
@NamedQuery(name="Note.showAll", query="select a from Note a")
public class Note {
    private int id;
    private String text;
    private Date date;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
