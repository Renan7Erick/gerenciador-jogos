
package br.senac.tads.dsw.gerenciador_jogos.controle;

import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;
import br.senac.tads.dsw.gerenciador_jogos.servico.PlataformaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plataformas")
public class PlataformaController {

	private final PlataformaService plataformaService;

	public PlataformaController(PlataformaService plataformaService) {
		this.plataformaService = plataformaService;
	}

	// GET: lista todas as plataformas
	@GetMapping
	public ResponseEntity<List<PlataformaEntity>> listar() {
		return ResponseEntity.ok(plataformaService.listar());
	}

	// POST: cadastra uma plataforma
	@PostMapping
	public ResponseEntity<PlataformaEntity> cadastrar(
		@Valid @RequestBody PlataformaEntity plataforma) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(plataformaService.cadastrar(plataforma));
	}

	// DELETE: exclui uma plataforma
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		plataformaService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
