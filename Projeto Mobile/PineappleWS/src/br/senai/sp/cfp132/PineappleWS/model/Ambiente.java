package br.senai.sp.cfp132.PineappleWS.model;


import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ambiente {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique= true)
	private String nome;
	private String andar;
	@ManyToOne
	@JoinColumn(name="responsavel")
	private Funcionario responsavel = new Funcionario();
	@OneToMany(fetch = FetchType.EAGER, mappedBy="ambiente")
	private List<Patrimonio> patrimonios;
	private boolean status;
	
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAndar() {
		return andar;
	}
	public void setAndar(String andar) {
		this.andar = andar;
	}
	public Funcionario getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}
	public List<Patrimonio> getPatrimonios() {
		return patrimonios;
	}
	public void setPatrimonios(List<Patrimonio> patrimonios) {
		this.patrimonios = patrimonios;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}
	
}
