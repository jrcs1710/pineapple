package br.senai.sp.cfp132.pineapplesystems.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemTransferencia implements Parcelable {

	private Long id;
	private Patrimonio patrimonio;
	private boolean status;
	private TipoMovimentacao tipoMovimentacao;

	/*-------------------------------------------------------------------------------------------------------------------------------------------------*/

	public ItemTransferencia() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	protected ItemTransferencia(Parcel in) {
		id = in.readByte() == 0x00 ? null : in.readLong();
		patrimonio = (Patrimonio) in.readValue(Patrimonio.class
				.getClassLoader());
		status = in.readByte() != 0x00;
		tipoMovimentacao = (TipoMovimentacao) in
				.readValue(TipoMovimentacao.class.getClassLoader());
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
		dest.writeValue(patrimonio);
		dest.writeByte((byte) (status ? 0x01 : 0x00));
		dest.writeValue(tipoMovimentacao);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<ItemTransferencia> CREATOR = new Parcelable.Creator<ItemTransferencia>() {
		@Override
		public ItemTransferencia createFromParcel(Parcel in) {
			return new ItemTransferencia(in);
		}

		@Override
		public ItemTransferencia[] newArray(int size) {
			return new ItemTransferencia[size];
		}
	};
}