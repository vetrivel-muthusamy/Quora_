package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.answerEntity;
import com.upgrad.quora.service.entity.questionEntitiy;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllAnswersToQuestionService {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<answerEntity> getAllAnswersToQuestion(final String questionId, final String accessToken) throws AuthorizationFailedException, InvalidQuestionException {


        UserAuthEntity userAuthTokenEntity = userDao.getUserAuthEntityByAccessToken(accessToken);

        if(userAuthTokenEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }

        if(userAuthTokenEntity.getLogoutAt()!=null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get all questions posted by a specific user");
        }

        if(userDao.getQuestionFromUuid(questionId)==null){
            throw new InvalidQuestionException("QUES-001","The question with entered uuid whose details are to be seen does not exist");
        }

        questionEntitiy questionEntity=userDao.getQuestionFromUuid(questionId);

        List<answerEntity> allAnswerFromQuestionId = userDao.getAnswersToQuestion(questionEntity);

        return allAnswerFromQuestionId;
    }

}
