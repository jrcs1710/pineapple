package br.senai.sp.cfp132.pineapplesystems.model;

import android.util.Base64;


public class Empresa {

	private Long id;
	private String nome;
	private byte[] logotipo;
	private String cnpj;
	private String rua;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String mascara;
	public String getMascara() {
		return mascara;
	}
	public void setMascara(String mascara) {
		this.mascara = mascara;
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
	public byte[] getLogotipo() {
		return logotipo;
	}
	public void setLogotipo(byte[] logotipo) {
		this.logotipo = logotipo;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
		
	public String getEnderecoCompleto(){
		
		return rua + ", " + numero + ". " + bairro + ", " + cidade + ", " + estado;
		
	}
	
	public String getFoto64() {
		String foto = Base64.encodeToString(logotipo, Base64.DEFAULT);
		return foto;
	}
	
}
