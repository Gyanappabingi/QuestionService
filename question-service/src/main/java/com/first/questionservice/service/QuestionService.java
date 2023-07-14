package com.first.questionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.first.questionservice.exceptionHandling.ResourceNotFoundException;
import com.first.questionservice.model.Question;
import com.first.questionservice.model.QuestionWrapper;
import com.first.questionservice.model.Response;
import com.first.questionservice.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	public ResponseEntity<String> postQuestion(Question question) {
		questionRepository.save(question);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
		
	}
	
	public ResponseEntity<Question> getQuestionById(int id) {
		Question q= questionRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("question not with id "+id));
		 return new ResponseEntity<>(q,HttpStatus.OK);
	}
	
	public ResponseEntity<List<Question>> getAllQuestions(){
		List<Question> questions= questionRepository.findAll();
		return new ResponseEntity<>(questions,HttpStatus.OK);
	//	return ResponseEntity.ok().body(questions);
	}
	
	public ResponseEntity<List<Question>> getQuestionByCategory(String category){
		List<Question> questions= questionRepository.getQuestionsByCategory(category);
		
			return new ResponseEntity<>(questions,HttpStatus.OK);
		
	}
	
	public ResponseEntity<Question> upadateQuestion(int id,Question question) {
		Question existingQuestion=questionRepository.findById(id)
				.orElseThrow(
				()->new ResourceNotFoundException("Resource not found "+id));
		
		existingQuestion.setQuestionTitle(question.getQuestionTitle());
		existingQuestion.setOption1(question.getOption1());
		existingQuestion.setOption2(question.getOption2());
		existingQuestion.setOption3(question.getOption3());
		existingQuestion.setOption4(question.getOption4());
		existingQuestion.setRightAnswer(question.getRightAnswer());
		existingQuestion.setCategory(question.getCategory());
		existingQuestion.setDifficultylevel(question.getDifficultylevel());
		
		questionRepository.save(existingQuestion);
		return new ResponseEntity<>(questionRepository.save(existingQuestion),HttpStatus.OK);
		
	}
	
	public void deleteQuestion(int id) {
		questionRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("not found "+id));
		questionRepository.deleteById(id);  
	}
	
	//generate
	public ResponseEntity<List<Integer>> getQuestionsIdForQuiz(String category,int numQ){
		List<Integer> questionsIds= questionRepository.getRandomQuestions(category, numQ);
		return new ResponseEntity<>(questionsIds,HttpStatus.OK);
	}
	//getQuestions(QuestionId)
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds){
		List<QuestionWrapper> wrappers=new ArrayList<>();
		List<Question> questions=new ArrayList<>();
		
		for(Integer id:questionIds) {
			questions.add(questionRepository.findById(id).get());
		}
		
		for(Question question:questions) {
			QuestionWrapper wrapper=new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrappers.add(wrapper);
		}
		return new ResponseEntity<>(wrappers,HttpStatus.OK);
	}
	
	//getScore
	public ResponseEntity<Integer> getScore(List<Response> responses){
		int right=0;
		for(Response response:responses) {
			Question question=questionRepository.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer()))
			right++;
		}
		return new ResponseEntity<>(right,HttpStatus.OK);
		
	}
	
}
