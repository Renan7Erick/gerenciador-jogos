
package br.senac.tads.dsw.gerenciador_jogos.repositorio;

import br.senac.tads.dsw.gerenciador_jogos.entidade.JogoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<JogoEntity, Integer> {

	// Busca jogos de uma plataforma específica
	List<JogoEntity> findByPlataforma_Id(Integer plataformaId);

	// Busca jogos disponíveis ou indisponíveis
	List<JogoEntity> findByDisponivel(Boolean disponivel);

	long countByPlataforma_Id(Integer plataformaId);
}
