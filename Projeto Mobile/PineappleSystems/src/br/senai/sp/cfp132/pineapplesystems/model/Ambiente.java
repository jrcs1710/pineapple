package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Ambiente implements Parcelable {

	private Long id;
	private String nome;
	private String andar;
	private Funcionario responsavel = new Funcionario();
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

	public Ambiente() {
		// TODO Auto-generated constructor stub
	}

	protected Ambiente(Parcel in) {
		id = in.readByte() == 0x00 ? null : in.readLong();
		nome = in.readString();
		andar = in.readString();
		responsavel = (Funcionario) in.readValue(Funcionario.class
				.getClassLoader());
		if (in.readByte() == 0x01) {
			patrimonios = new ArrayList<Patrimonio>();
			in.readList(patrimonios, Patrimonio.class.getClassLoader());
		} else {
			patrimonios = null;
		}
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
		dest.writeString(andar);
		dest.writeValue(responsavel);
		if (patrimonios == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(patrimonios);
		}
		dest.writeByte((byte) (status ? 0x01 : 0x00));
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Ambiente> CREATOR = new Parcelable.Creator<Ambiente>() {
		@Override
		public Ambiente createFromParcel(Parcel in) {
			return new Ambiente(in);
		}

		@Override
		public Ambiente[] newArray(int size) {
			return new Ambiente[size];
		}
	};
}