
package br.senac.tads.dsw.gerenciador_jogos.repositorio;

import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlataformaRepository
	extends JpaRepository<PlataformaEntity, Integer> {

	// Verifica se uma plataforma já está cadastrada
	boolean existsByNomeIgnoreCase(String nome);
}
