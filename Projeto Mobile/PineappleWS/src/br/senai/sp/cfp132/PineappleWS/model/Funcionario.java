package br.senai.sp.cfp132.PineappleWS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Funcionario {

	
		@Id
		@GeneratedValue
		private Long id;
		private String nome;
		@Column(unique= true)
		private String email;
		private boolean status;
		private Cargo cargo;
		@OneToOne
		@JoinColumn(name="usuario")
		private Usuario usuario = new Usuario();
		
		public Usuario getUsuario() {
			return usuario;
		}
		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
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
			System.out.println("teste 01");
			this.nome = nome;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			System.out.println("teste 02");
			this.email = email;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			System.out.println("teste 03");
			this.status = status;
		}
		public Cargo getCargo() {
			return cargo;
		}
		
	
		
		public void setCargo(Cargo cargo) {
			System.out.println("teste 04");
			this.cargo = cargo;
		}
		@Override
		public String toString() {
		return this.nome;
		}
}
