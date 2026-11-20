
package br.senac.tads.dsw.gerenciador_jogos.servico;

import br.senac.tads.dsw.gerenciador_jogos.entidade.JogoEntity;
import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.JogoRepository;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.PlataformaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JogoService {

	private final JogoRepository jogoRepository;
	private final PlataformaRepository plataformaRepository;

	public JogoService(
		JogoRepository jogoRepository,
		PlataformaRepository plataformaRepository) {
		this.jogoRepository = jogoRepository;
		this.plataformaRepository = plataformaRepository;
	}

	// Lista todos os jogos
	public List<JogoEntity> listar() {
		return jogoRepository.findAll();
	}

	// Busca um jogo pelo ID
	public JogoEntity buscarPorId(Integer id) {
		return jogoRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Jogo não encontrado"
			));
	}

	// Busca os jogos de uma plataforma
	public List<JogoEntity> listarPorPlataforma(Integer plataformaId) {
		return jogoRepository.findByPlataforma_Id(plataformaId);
	}

	// Busca jogos pela disponibilidade
	public List<JogoEntity> listarPorDisponibilidade(Boolean disponivel) {
		return jogoRepository.findByDisponivel(disponivel);
	}

	// Verifica e associa uma plataforma cadastrada ao jogo
	private void validarPlataforma(JogoEntity jogo) {

		if (jogo.getPlataforma() == null ||
			jogo.getPlataforma().getId() == null) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"Selecione uma plataforma válida"
			);
		}

		PlataformaEntity plataforma = plataformaRepository
			.findById(jogo.getPlataforma().getId())
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"A plataforma selecionada não existe"
			));

		jogo.setPlataforma(plataforma);
	}

	// Cadastra um novo jogo
	public JogoEntity cadastrar(JogoEntity jogo) {

		jogo.setTitulo(jogo.getTitulo().trim());
		validarPlataforma(jogo);

		return jogoRepository.save(jogo);
	}

	// Atualiza os dados de um jogo
	public JogoEntity atualizar(Integer id, JogoEntity dados) {

		JogoEntity jogo = buscarPorId(id);

		dados.setTitulo(dados.getTitulo().trim());
		validarPlataforma(dados);

		jogo.setTitulo(dados.getTitulo());
		jogo.setAnoLancamento(dados.getAnoLancamento());
		jogo.setPlataforma(dados.getPlataforma());
		jogo.setDisponivel(dados.getDisponivel());
		jogo.setObservacoes(dados.getObservacoes());

		return jogoRepository.save(jogo);
	}

	// Exclui um jogo
	public void excluir(Integer id) {
		JogoEntity jogo = buscarPorId(id);
		jogoRepository.delete(jogo);
	}
}
