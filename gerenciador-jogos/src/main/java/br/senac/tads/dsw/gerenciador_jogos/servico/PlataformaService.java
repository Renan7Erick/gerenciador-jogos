
package br.senac.tads.dsw.gerenciador_jogos.servico;

import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.PlataformaRepository;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.JogoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlataformaService {

	private final PlataformaRepository plataformaRepository;
	private final JogoRepository jogoRepository;

	public PlataformaService(
		PlataformaRepository plataformaRepository,
		JogoRepository jogoRepository) {
		this.plataformaRepository = plataformaRepository;
		this.jogoRepository = jogoRepository;
	}

	// Lista todas as plataformas
	public List<PlataformaEntity> listar() {
		return plataformaRepository.findAll(
			org.springframework.data.domain.Sort.by("nome")
		);
	}

	// Cadastra uma nova plataforma
	public PlataformaEntity cadastrar(PlataformaEntity plataforma) {

		plataforma.setNome(plataforma.getNome().trim());

		if (plataformaRepository.existsByNomeIgnoreCase(
			plataforma.getNome())) {
			throw new ResponseStatusException(
				HttpStatus.CONFLICT,
				"Esta plataforma já está cadastrada"
			);
		}

		return plataformaRepository.save(plataforma);
	}

	// Exclui uma plataforma que não esteja vinculada a jogos
	public void excluir(Integer id) {

		PlataformaEntity plataforma = plataformaRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Plataforma não encontrada"
			));

		if (jogoRepository.countByPlataforma_Id(id) > 0) {
			throw new ResponseStatusException(
				HttpStatus.CONFLICT,
				"Não é possível excluir uma plataforma que possui jogos"
			);
		}

		plataformaRepository.delete(plataforma);
	}
}
