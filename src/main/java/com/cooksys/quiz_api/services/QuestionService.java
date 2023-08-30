package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Quiz;

public interface QuestionService {

	List<QuestionResponseDto> getQuestionsByQuiz(Quiz quiz);
	
//	QuestionResponseDto getQuestionById(Long id, QuestionRequestDto questionRequestDto);
	
}
