package br.senai.sp.cfp132.pineapplesystems.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Funcionario implements Parcelable {

		private Long id;
		private String nome;
		private String email;
		private boolean status;
		private Cargo cargo;
		private Usuario usuario = new Usuario();
		
		public Funcionario() {
			// TODO Auto-generated constructor stub
		}
		
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
			this.nome = nome;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public Cargo getCargo() {
			return cargo;
		}
		public void setCargo(Cargo cargo) {
			this.cargo = cargo;
		}
	
		
		@Override
		public String toString() {
		return this.nome;
		}

    protected Funcionario(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        nome = in.readString();
        email = in.readString();
        status = in.readByte() != 0x00;
        cargo = (Cargo) in.readValue(Cargo.class.getClassLoader());
        usuario = (Usuario) in.readValue(Usuario.class.getClassLoader());
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
        dest.writeString(email);
        dest.writeByte((byte) (status ? 0x01 : 0x00));
        dest.writeValue(cargo);
        dest.writeValue(usuario);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Funcionario> CREATOR = new Parcelable.Creator<Funcionario>() {
        @Override
        public Funcionario createFromParcel(Parcel in) {
            return new Funcionario(in);
        }

        @Override
        public Funcionario[] newArray(int size) {
            return new Funcionario[size];
        }
    };
}