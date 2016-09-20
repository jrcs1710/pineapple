package br.senai.sp.cfp132.PineappleSystems.Util;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;

public class PatrimonioGenerico {

	private PatrimonioUtil patrimonio;

	private SimpleStringProperty cdPatrimonio;
	private SimpleStringProperty modelo;
	private SimpleStringProperty tipo;
	
	private SimpleStringProperty ambiente;
	
	public SimpleStringProperty getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(SimpleStringProperty ambiente) {
		this.ambiente = ambiente;
	}

	public SimpleStringProperty getModelo() {
		return modelo;
	}

	public void setModelo(SimpleStringProperty modelo) {
		this.modelo = modelo;
	}

	public SimpleStringProperty getTipo() {
		return tipo;
	}

	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}

	

	public SimpleStringProperty getCdPatrimonio() {
		return cdPatrimonio;
	}

	public void setCdPatrimonio(SimpleStringProperty cdPatrimonio) {
		this.cdPatrimonio = cdPatrimonio;
	}

	public PatrimonioGenerico(PatrimonioUtil p){
		this.patrimonio = p;
	
		cdPatrimonio = new SimpleStringProperty(p.getCdPatrimonio());
		modelo = new SimpleStringProperty(p.getModelo());
		tipo = new SimpleStringProperty(p.getTipo());
		ambiente = new SimpleStringProperty(p.getAmbiente());
	}
}
