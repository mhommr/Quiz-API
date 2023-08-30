package com.cooksys.quiz_api.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class QuestionResponseDto {

  private Long id;

  private List<AnswerResponseDto> answers;

}
