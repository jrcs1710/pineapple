package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Patrimonio implements Parcelable {

	private Long id;
	private Modelo modelo = new Modelo();
	private String descricao;
	private String cdPatrimonio;
	private Ambiente ambiente = new Ambiente();
	private Calendar dtEntrada;
	private Calendar dtSaida;

	public Patrimonio() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCdPatrimonio() {
		return cdPatrimonio;
	}

	public void setCdPatrimonio(String cdPatrimonio) {
		this.cdPatrimonio = cdPatrimonio;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Calendar getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Calendar dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public Calendar getDtSaida() {
		return dtSaida;
	}

	public void setDtSaida(Calendar dtSaida) {
		this.dtSaida = dtSaida;
	}

	@Override
	public boolean equals(Object pat) {
		if (((Patrimonio) pat).getId() == id) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return cdPatrimonio;
	}

	protected Patrimonio(Parcel in) {
		id = in.readByte() == 0x00 ? null : in.readLong();
		modelo = (Modelo) in.readValue(Modelo.class.getClassLoader());
		descricao = in.readString();
		cdPatrimonio = in.readString();
		ambiente = (Ambiente) in.readValue(Ambiente.class.getClassLoader());
		dtEntrada = (Calendar) in.readValue(Calendar.class.getClassLoader());
		dtSaida = (Calendar) in.readValue(Calendar.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (id == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeLong(id);
		}
		dest.writeValue(modelo);
		dest.writeString(descricao);
		dest.writeString(cdPatrimonio);
		dest.writeValue(ambiente);
		dest.writeValue(dtEntrada);
		dest.writeValue(dtSaida);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Patrimonio> CREATOR = new Parcelable.Creator<Patrimonio>() {
		@Override
		public Patrimonio createFromParcel(Parcel in) {
			return new Patrimonio(in);
		}

		@Override
		public Patrimonio[] newArray(int size) {
			return new Patrimonio[size];
		}
	};
}