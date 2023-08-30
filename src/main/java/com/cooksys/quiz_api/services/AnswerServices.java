package com.cooksys.quiz_api.services;

import com.cooksys.quiz_api.entities.Question;

public interface AnswerServices {

	public boolean getAnswerByQuestion(Question question);
	
}
