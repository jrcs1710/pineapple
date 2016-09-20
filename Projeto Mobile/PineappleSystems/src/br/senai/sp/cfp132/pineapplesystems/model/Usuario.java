package br.senai.sp.cfp132.pineapplesystems.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

	private Long id;
	private String nomeUsuario;
	private String senha;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
	
	

    protected Usuario(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        nomeUsuario = in.readString();
        senha = in.readString();
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
        dest.writeString(nomeUsuario);
        dest.writeString(senha);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}