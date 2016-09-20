package br.senai.sp.cfp132.pineapplesystems.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class Modelo implements Parcelable {

	private Long id;
	private String nome;
	private byte[] foto;
	private Tipo tipo = new Tipo();
	private boolean status;

	public Modelo() {
		// TODO Auto-generated constructor stub
	}

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
		String foto = Base64.encodeToString(this.foto, Base64.DEFAULT);
		return foto;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}

	protected Modelo(Parcel in) {
		id = in.readByte() == 0x00 ? null : in.readLong();
		nome = in.readString();
		tipo = (Tipo) in.readValue(Tipo.class.getClassLoader());
		status = in.readByte() != 0x00;
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
		dest.writeString(nome);
		dest.writeValue(tipo);
		dest.writeByte((byte) (status ? 0x01 : 0x00));
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Modelo> CREATOR = new Parcelable.Creator<Modelo>() {
		@Override
		public Modelo createFromParcel(Parcel in) {
			return new Modelo(in);
		}

		@Override
		public Modelo[] newArray(int size) {
			return new Modelo[size];
		}
	};
}