package br.senac.tads.dsw.gerenciador_jogos.entidade;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_jogos")
public class JogoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "O título é obrigatório")
	@Column(nullable = false, length = 150)
	private String titulo;

	@NotNull(message = "O ano de lançamento é obrigatório")
	@Min(value = 1950, message = "O ano deve ser igual ou maior que 1950")
	@Column(name = "ano_lancamento", nullable = false)
	private Integer anoLancamento;

	@NotBlank(message = "A plataforma é obrigatória")
	@Column(nullable = false, length = 100)
	private String plataforma;

	@NotNull(message = "A disponibilidade é obrigatória")
	@Column(nullable = false)
	private Boolean disponivel;

	@Column(length = 1000)
	private String observacoes;

	public JogoEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(Integer anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}
