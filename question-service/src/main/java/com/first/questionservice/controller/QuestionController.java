package com.first.questionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.first.questionservice.exceptionHandling.ResourceNotFoundException;
import com.first.questionservice.model.Question;
import com.first.questionservice.model.QuestionWrapper;
import com.first.questionservice.model.Response;
import com.first.questionservice.service.QuestionService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController()
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addQuestion(@RequestBody @Valid Question question) {
		return questionService.postQuestion(question);
		
	}
	@GetMapping("/getall")
	public ResponseEntity<List<Question>> getAllQuestions(){
		return questionService.getAllQuestions();
	}
	
	@GetMapping("getById/{id}")
	public ResponseEntity<Question> getById(@PathVariable("id") int id){
		return questionService.getQuestionById(id);
	}
	 
	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("category") String category){
		return questionService.getQuestionByCategory(category);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable("id") int id, @RequestBody Question question){
		return questionService.upadateQuestion(id, question);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("id") int id ){
		questionService.deleteQuestion(id);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
	@GetMapping("/generate")
	public ResponseEntity<List<Integer>> getQuestionsIdForQuiz(@RequestParam String category,int numQ){
		return questionService.getQuestionsIdForQuiz(category, numQ);
	}
	@PostMapping("/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsIds){
		return questionService.getQuestionsFromId(questionsIds);
	}
	@PostMapping("/getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		return questionService.getScore(responses);
	}
}
