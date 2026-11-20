
package br.senac.tads.dsw.gerenciador_jogos.controle;

import br.senac.tads.dsw.gerenciador_jogos.entidade.JogoEntity;
import br.senac.tads.dsw.gerenciador_jogos.servico.JogoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
public class JogoController {

	private final JogoService jogoService;

	public JogoController(JogoService jogoService) {
		this.jogoService = jogoService;
	}

	// GET: lista todos os jogos
	@GetMapping
	public ResponseEntity<List<JogoEntity>> listar() {
		return ResponseEntity.ok(jogoService.listar());
	}

	// GET: busca um jogo pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<JogoEntity> buscarPorId(@PathVariable Integer id) {
		return ResponseEntity.ok(jogoService.buscarPorId(id));
	}

	// GET: filtra jogos pela plataforma
	@GetMapping("/plataforma/{plataformaId}")
	public ResponseEntity<List<JogoEntity>> listarPorPlataforma(
		@PathVariable Integer plataformaId) {
		return ResponseEntity.ok(
			jogoService.listarPorPlataforma(plataformaId)
		);
	}

	// GET: filtra jogos pela disponibilidade
	@GetMapping("/disponibilidade/{disponivel}")
	public ResponseEntity<List<JogoEntity>> listarPorDisponibilidade(
		@PathVariable Boolean disponivel) {
		return ResponseEntity.ok(
			jogoService.listarPorDisponibilidade(disponivel)
		);
	}

	// POST: cadastra um jogo
	@PostMapping
	public ResponseEntity<JogoEntity> cadastrar(
		@Valid @RequestBody JogoEntity jogo) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(jogoService.cadastrar(jogo));
	}

	// PUT: altera um jogo existente
	@PutMapping("/{id}")
	public ResponseEntity<JogoEntity> atualizar(
		@PathVariable Integer id,
		@Valid @RequestBody JogoEntity jogo) {
		return ResponseEntity.ok(jogoService.atualizar(id, jogo));
	}

	// DELETE: exclui um jogo
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		jogoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
