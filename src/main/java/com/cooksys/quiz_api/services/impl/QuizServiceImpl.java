package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final QuestionMapper questionMapper;
	private final QuizMapper quizMapper;
	
//	public void validateQuizDto (QuizRequestDto quizRequestDto) {
//		if (quizRequestDto == null || quizRequestDto.getName() == null) {
//			throw new BadRequestException();
//		}
//	}

	@Override
	public List<QuizResponseDto> getAllQuizzes() {
		return quizMapper.entitiesToDtos(quizRepository.findAll());
	}

	@Override
	public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
		if (quizRequestDto == null || quizRequestDto.getName() == null) {
			return null;
		}
		Quiz quizToCreate = quizMapper.requestDtoToEntity(quizRequestDto);
		Quiz quizCreated = quizRepository.saveAndFlush(quizToCreate);
		for (Question question : quizCreated.getQuestions()) {
			question.setQuiz(quizCreated);
			questionRepository.saveAndFlush(question);
			for (Answer answer : question.getAnswers()) {
				answer.setQuestion(question);
				answerRepository.saveAndFlush(answer);
			}
		}
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizMapper.requestDtoToEntity(quizRequestDto)));
	 
	}

	@Override
	public QuestionResponseDto getRandomQuestion(Long id) {
		Optional<Quiz> quizToFind = quizRepository.findById(id);
		if (quizToFind.isEmpty()) {
			return null;
		}
		Quiz quiz = quizToFind.get();
		Random random = new Random();
		int randomNumber = random.nextInt(quiz.getQuestions().size());
		return questionMapper.entityToDto(quiz.getQuestions().get(randomNumber));
	}

	@Override
	public QuizResponseDto renameQuiz(Long id, String newName) {
		Optional<Quiz> quizToFind = quizRepository.findById(id);
		if (quizToFind.isEmpty()) {
			return null;
		}
		Quiz quiz = quizToFind.get();
		quiz.setName(newName);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
	}

	@Override
	public QuizResponseDto deleteQuiz(Long id) {
		Optional<Quiz> potentialQuiz = quizRepository.findById(id);
		if (potentialQuiz.isEmpty()) {
			return null;
		}
		Quiz quizToDelete = potentialQuiz.get();
		quizRepository.delete(quizToDelete);
		return quizMapper.entityToDto(quizToDelete);
	}

	@Override
	public QuizResponseDto addQuestionToQuiz(Long id, QuestionRequestDto questionRequestDto) {
		Optional<Quiz> quizToAdd = quizRepository.findById(id);
		if (quizToAdd.isEmpty()) {
			return null;
		}
		Quiz quiz = quizToAdd.get();
//		Optional<Question> questionToAdd = questionRepository.requestDtoToEntity(questionRequestDto);
		Question question = questionMapper.requestDtoToEntity(questionRequestDto);
		if (question.getText() == null) {
			return null;
		}
		question.setQuiz(quiz);
		if (question.getAnswers() == null) {
			return null;
		}
		for (Answer answer : question.getAnswers()) {
			if (answer.getText() == null) {
				return null;
			}
			answer.setQuestion(question);
		}
		questionRepository.saveAndFlush(question);
		answerRepository.saveAllAndFlush(question.getAnswers());
		return quizMapper.entityToDto(quiz);
	}

	@Override
	public QuestionResponseDto deleteQuestion(Long id, Long questionId) {
		Optional<Quiz> quizToFind = quizRepository.findById(id);
		if (quizToFind.isEmpty()) {
			return null;
		}
		Quiz quiz = quizToFind.get();
		Optional<Question> questionToFind = questionRepository.findById(questionId);
		if (questionToFind.isEmpty()) {
			return null;
		}
		Question question = questionToFind.get();
		
		if (!question.getQuiz().equals(quiz)) {
			return null;
		}
		
		answerRepository.deleteAll(question.getAnswers());
		questionRepository.deleteById(questionId);
		return questionMapper.entityToDto(question);
		
//		Quiz quizToEdit = getQuiz(id);
//		Question questionToDelete = getQuestion(questionId);
//		List<Question> questionList = quizToEdit.getQuestions();
//		for (Question q : questionList) {
//			if (q.getId() == questionId) {
//				questionList.remove(q);
//			}
//		}
//		quizToEdit.setQuestions(questionList);
//		quizRepository.saveAndFlush(quizToEdit);
//		questionRepository.delete(questionToDelete);
//		for (Answer a: questionToDelete.getAnswers()) {
//			answerRepository.delete(a);
//		}
//		return quizMapper.entityToDto(quizToEdit);
	}
	
	

}
