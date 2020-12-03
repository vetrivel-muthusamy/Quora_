package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.AnswerDeleteResponse;
import com.upgrad.quora.api.model.AnswerDetailsResponse;
import com.upgrad.quora.service.business.DeleteAnswerService;
import com.upgrad.quora.service.business.GetAllAnswersToQuestionService;
import com.upgrad.quora.service.entity.answerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author github.com/vetrivel-muthusamy
 */

@RestController
@RequestMapping("/")
public class AnswerController {


    //@github.com/vetrivel-muthusamy
    @Autowired
    private DeleteAnswerService deleteAnswerService;
    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@RequestHeader("authorization")final String authorization, @PathVariable("answerId") final String answerId) throws AuthorizationFailedException, AnswerNotFoundException {
        String deletedAnswerUuid = deleteAnswerService.deleteAnswer(authorization,answerId);
        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse().id(deletedAnswerUuid).status("ANSWER DELETED");
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);
    }

    //@github.com/vetrivel-muthusamy
    @Autowired
    private GetAllAnswersToQuestionService getAllAnswersToQuestionService;
    @RequestMapping(method = RequestMethod.GET, path = "/answer/all/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AnswerDetailsResponse>> getAllAnswersToQuestion(@RequestHeader ("authorization") final String authorization, @PathVariable("questionId") final String questionId) throws AuthorizationFailedException, InvalidQuestionException {
        List<answerEntity> allAnswersToQuestion = getAllAnswersToQuestionService.getAllAnswersToQuestion(questionId, authorization);
        answerEntity answerEntity;
        List<AnswerDetailsResponse> displayAllAnswersByQuestion = new ArrayList<>();
        for (int i = 0; i < allAnswersToQuestion.size(); i++) {
            answerEntity = allAnswersToQuestion.get(i);
            AnswerDetailsResponse answerDetailsResponse = new AnswerDetailsResponse().id(answerEntity.getUuid()).questionContent(answerEntity.getQuestion().getContent()).answerContent(answerEntity.getAnswer());
            displayAllAnswersByQuestion.add(answerDetailsResponse);
        }
        return new ResponseEntity<List<AnswerDetailsResponse>>(displayAllAnswersByQuestion, HttpStatus.OK);
    }
}