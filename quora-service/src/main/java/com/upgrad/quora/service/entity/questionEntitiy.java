package com.upgrad.quora.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


//This model is implemented to map with question table in DB
@Entity
@Table(name = "question")
@NamedQueries({
        @NamedQuery(name = "performFetchAllQuestions", query = "Select q from questionEntitiy q"),
        @NamedQuery(name = "performQuestionGetByIdQuery", query = "Select q from questionEntitiy q where q.id=:questionId"),
        @NamedQuery(name = "performFetchAllQuestionsByUser", query = "Select q from questionEntitiy q where q.userId=:userId")
})
public class questionEntitiy {

    //id field is primary key
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //uuid column is universal unique identity field
    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    //content column will contain the content of question
    @Column(name = "content")
    @Size(max = 500)
    private String content;

    //date column will contain the date at whichh the question is posted
    @Column(name = "date")
    private Timestamp date;

    //user_id column will contain the user who posted the question
    @Column(name = "user_id")
    private Integer userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}