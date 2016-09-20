package br.senai.sp.cfp132.PineappleWS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
@Entity
public class Modelo {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique= true)
	private String nome;
	@Column(columnDefinition="mediumblob")
	private byte[] foto;
	@ManyToOne
	@JoinColumn(name="tipo")
	private Tipo tipo = new Tipo();
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
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public String getFoto64() {
		String foto = Base64.encode(this.foto);
		return foto;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}
}
