package br.senac.tads.dsw.gerenciador_jogos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

	private static final String CRIACAO = "2026-11-21 19:25 - Gemini";

	@GetMapping(value = "/prompt", produces = "text/plain;charset=UTF-8")
	public String getPrompt() {
		Path caminho = Path.of("PROMPT.md");
		if (Files.exists(caminho)) {
			try {
				return Files.readString(caminho, StandardCharsets.UTF_8);
			} catch (IOException e) {
				return CRIACAO;
			}
		}
		return CRIACAO;
	}
}
