package skyit.todo.model;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Marc on 21.03.2016.
 */
public class ToDo implements Comparable<ToDo> {

    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    private long rowid;
    private String name;
    private String desc;
    private Date date;
    private Boolean important;
    private Boolean done = false;
    private long UID;

    public ToDo() {
    }

    public ToDo(String name, String desc, Date date, Boolean important, Boolean done) {
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.important = important;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }


    public long getUID() {
        return UID;
    }

    @Override
    public int compareTo(ToDo another) {
        return getDate().compareTo(another.getDate());
    }
}
