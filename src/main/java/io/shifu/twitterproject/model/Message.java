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

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="retweet")
    private Message retweet;

    @OneToMany(mappedBy="retweet", cascade=CascadeType.ALL)
    private Set<Message> retweets;

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="answer")
    private Message answer;

    @OneToMany(mappedBy="answer", cascade=CascadeType.ALL)
    private Set<Message> reply;


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

    public Message getRetweet() {
        return retweet;
    }

    public void setRetweet(Message retweet) {
        this.retweet = retweet;
    }

    public Set<Message> getRetweets() {
        return retweets;
    }

    public void setRetweets(Set<Message> retweets) {
        this.retweets = retweets;
    }

    public Message getAnswer() {
        return answer;
    }

    public void setAnswer(Message answer) {
        this.answer = answer;
    }

    public Set<Message> getReply() {
        return reply;
    }

    public void setReply(Set<Message> reply) {
        this.reply = reply;
    }
}
