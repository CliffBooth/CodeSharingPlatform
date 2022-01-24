package platform.businessLayer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="CodeObj")
public class CodeObj {
    @Id
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private String id;

    @Column(name="code")
    private String code = "";

    @Column(name="date")
    private String date;

    @JsonIgnore
    @Column(name="savedTime")
    private LocalDateTime savedTime; //for precision

    @Column(name = "views")
    private int views = 0;

    @Column(name = "time")
    private long time = 0;

    @Column
    @JsonIgnore
    private long initialTime = 0;

    @JsonIgnore
    @Column(name = "hidden")
    private boolean hidden = true;

    @JsonIgnore
    @Column
    private boolean hasTime = false;

    @JsonIgnore
    @Column
    private boolean hasViews = false;

    public CodeObj() {
    }

    public CodeObj(String id, String code, String date, LocalDateTime savedTime, int views, long time) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.savedTime = savedTime;
        this.views = views;
        this.time = time;
        this.initialTime = time;
        if (views > 0)
            hasViews = true;
        if (time > 0)
            hasTime = true;
        if (views <= 0 && time <= 0)
            hidden = false;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getHidden() {
        return hidden;
    }

    public LocalDateTime getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(LocalDateTime savedTime) {
        this.savedTime = savedTime;
    }


    public long getInitialTime() {
        return initialTime;
    }

    public boolean isHasTime() {
        return hasTime;
    }

    public boolean isHasViews() {
        return hasViews;
    }
}
