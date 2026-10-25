package br.senac.tads.dsw.gerenciador_jogos.repositorio;

import br.senac.tads.dsw.gerenciador_jogos.entidade.JogoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<JogoEntity, Integer> {
}
