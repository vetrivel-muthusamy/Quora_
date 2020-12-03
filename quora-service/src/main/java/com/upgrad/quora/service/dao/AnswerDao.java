package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.answerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;


    public answerEntity getAnswerFromUuid(final String answerUuId) {
        try {
            return entityManager.createNamedQuery("getAnswerByUuid", answerEntity.class).setParameter("uuid", answerUuId).getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
    }

    public answerEntity createAnswer(final answerEntity answerEntity) {
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    public List<answerEntity> getAnswersToQuestion(final questionEntity question) {
        try {
            List<answerEntity> allAnswersToQuestion = entityManager.createNamedQuery("getAnswersByQuestionId", answerEntity.class).setParameter("question", question).getResultList();
            return allAnswersToQuestion;
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void deleteAnswerFromUuid(final String answerId) {
        entityManager.createQuery("delete from answerEntity u where u.uuid =:answerId").setParameter("answerId", answerId).executeUpdate();
    }

}
