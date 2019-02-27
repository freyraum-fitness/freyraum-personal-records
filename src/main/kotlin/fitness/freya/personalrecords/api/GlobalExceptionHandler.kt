package fitness.freya.personalrecords.api

import fitness.freya.personalrecords.api.dto.ErrorDto
import fitness.freya.personalrecords.api.exception.ExerciseNotFoundException
import fitness.freya.personalrecords.api.exception.GoalNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(IllegalAccessException::class)
  fun catchIllegalAccessException(exception: GoalNotFoundException): ResponseEntity<ErrorDto> =
      ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body<ErrorDto>(ErrorDto(exception.message!!))

  @ExceptionHandler(GoalNotFoundException::class)
  fun catchGoalNotFoundException(exception: GoalNotFoundException): ResponseEntity<ErrorDto> =
      ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body<ErrorDto>(ErrorDto(exception.message!!))

  @ExceptionHandler(ExerciseNotFoundException::class)
  fun catchExerciseNotFoundException(exception: ExerciseNotFoundException): ResponseEntity<ErrorDto> =
      ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body<ErrorDto>(ErrorDto(exception.message!!))

  @ExceptionHandler(Exception::class)
  fun catchException(exception: Exception): ResponseEntity<ErrorDto> =
      ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body<ErrorDto>(ErrorDto(exception.message!!))

}