package com.upgrad.quora.service.dao;
/* spring imports */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* app imports */
import com.upgrad.quora.service.entity.questionEntitiy;

/* java imports */
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class QuestionDao {
    @Autowired
    private EntityManager entityManager;

    /* method to retrieve a user by the user name */
    public void createQuestion(questionEntitiy q) throws Exception {
        entityManager.persist(q);
    }

    /* method to retrieve all the questions from the database */
    public List<questionEntitiy> getAllQuestions() throws Exception {
        try {
            return entityManager.createNamedQuery("performFetchAllQuestions", questionEntitiy.class).getResultList();
        }
        catch (Exception e) {
            /* just a precautionary fallback incase something goes wrong, we can get to know what happed */
            System.out.println("Get All Questions: Exception Raised");
            System.out.println(e);
            return null;
        }
    }

    /* method to retreive a single question entity by id */
    public questionEntitiy getQuestionByIdValue(long qId) throws Exception {
        try {
            return entityManager.createNamedQuery("performQuestionGetByIdQuery", questionEntitiy.class).setParameter("questionId", qId).getSingleResult();
        }
        catch (Exception e) {
            System.out.println("Get Question By Id Value: Exception Thrown");
            System.out.println(e);
            return null;
        }
    }

    /* method to help update an existing question entity */
    public void editQuestionContent(questionEntitiy updatedQuestion) {
        entityManager.merge(updatedQuestion);
    }

    /* method helps to delete an existing question entity */
    public String deleteQuestion(questionEntitiy question) {
        String uuid = question.getUuid();
        this.entityManager.remove(question);
        return uuid;
    }

    /* method helps to get all questions pertaining to a particuarl user */
    public List<questionEntitiy> getAllQuestionsByUser(final long userId) throws Exception {
        System.out.println("User Id: " + userId);
        try {
            return entityManager.createNamedQuery("performFetchAllQuestionsByUser", questionEntitiy.class).setParameter("userId", (int) userId).getResultList();
        }
        catch (Exception e) {
            System.out.println("Get Alll Questions By User: Exception Thrown");
            System.out.println(e);
            return null;
        }
    }

    public questionEntity getQuestionFromUuid(final String questionId) {
        try {
            return entityManager.createNamedQuery("getQuestionByUuid", questionEntity.class).setParameter("uuid", questionId).getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
    }
}