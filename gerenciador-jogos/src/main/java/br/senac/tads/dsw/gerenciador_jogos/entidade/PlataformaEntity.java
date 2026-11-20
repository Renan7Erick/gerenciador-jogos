
package br.senac.tads.dsw.gerenciador_jogos.entidade;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_plataformas")
public class PlataformaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "O nome da plataforma é obrigatório")
	@Column(nullable = false, unique = true, length = 100)
	private String nome;

	public PlataformaEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
