package io.shifu.twitterproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy="message", fetch = FetchType.EAGER)
    private Set<Like> likes;

    @Column(name = "retweet")
    private Long retweet;

    @Column(name = "thread")
    private Long thread;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "lft")
    private Long lft;

    @Column(name = "rgt")
    private Long rgt;

    @Column(name = "lvl")
    private Long lvl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Long getRetweet() {
        return retweet;
    }

    public void setRetweet(Long retweet) {
        this.retweet = retweet;
    }

    public Long getThread() {
        return thread;
    }

    public void setThread(Long thread) {
        this.thread = thread;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLft() {
        return lft;
    }

    public void setLft(Long lft) {
        this.lft = lft;
    }

    public Long getRgt() {
        return rgt;
    }

    public void setRgt(Long rgt) {
        this.rgt = rgt;
    }

    public Long getLvl() {
        return lvl;
    }

    public void setLvl(Long lvl) {
        this.lvl = lvl;
    }
}
