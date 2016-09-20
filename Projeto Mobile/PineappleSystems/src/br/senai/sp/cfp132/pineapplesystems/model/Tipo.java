package br.senai.sp.cfp132.pineapplesystems.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tipo implements Parcelable {

	private Long id;
	private String descricao;
	
	public Tipo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {

		return descricao;
	}


    protected Tipo(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        descricao = in.readString();
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
        dest.writeString(descricao);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Tipo> CREATOR = new Parcelable.Creator<Tipo>() {
        @Override
        public Tipo createFromParcel(Parcel in) {
            return new Tipo(in);
        }

        @Override
        public Tipo[] newArray(int size) {
            return new Tipo[size];
        }
    };
}