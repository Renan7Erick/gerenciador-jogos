
package br.senac.tads.dsw.gerenciador_jogos.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Trata erros de validação dos campos
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> tratarValidacao(
		MethodArgumentNotValidException exception) {

		Map<String, String> erros = new HashMap<>();

		exception.getBindingResult().getFieldErrors().forEach(erro ->
			erros.put(erro.getField(), erro.getDefaultMessage())
		);

		return ResponseEntity.badRequest().body(erros);
	}

	// Trata erros gerados pelos serviços
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, String>> tratarStatus(
		ResponseStatusException exception) {

		Map<String, String> resposta = new HashMap<>();

		resposta.put("erro", exception.getReason());

		return ResponseEntity.status(exception.getStatusCode())
			.body(resposta);
	}

	// Trata erros inesperados
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> tratarErroGeral(
		Exception exception) {

		Map<String, String> resposta = new HashMap<>();

		resposta.put("erro", "Ocorreu um erro interno no servidor");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(resposta);
	}
}
