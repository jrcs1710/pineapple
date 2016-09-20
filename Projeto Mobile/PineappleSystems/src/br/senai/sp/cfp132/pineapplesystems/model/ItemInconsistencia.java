package br.senai.sp.cfp132.pineapplesystems.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemInconsistencia implements Parcelable {

	private Long id;
	private Patrimonio patrimonio;
	private TipoInconsistencia tipoInconsistencia;
	private boolean status;
	
	public ItemInconsistencia() {
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

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public TipoInconsistencia getTipoInconsistencia() {
		return tipoInconsistencia;
	}

	public void setTipoInconsistencia(TipoInconsistencia tipoInconsistencia) {
		this.tipoInconsistencia = tipoInconsistencia;
	}


    protected ItemInconsistencia(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        patrimonio = (Patrimonio) in.readValue(Patrimonio.class.getClassLoader());
        tipoInconsistencia = (TipoInconsistencia) in.readValue(TipoInconsistencia.class.getClassLoader());
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
        dest.writeValue(patrimonio);
        dest.writeValue(tipoInconsistencia);
        dest.writeByte((byte) (status ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ItemInconsistencia> CREATOR = new Parcelable.Creator<ItemInconsistencia>() {
        @Override
        public ItemInconsistencia createFromParcel(Parcel in) {
            return new ItemInconsistencia(in);
        }

        @Override
        public ItemInconsistencia[] newArray(int size) {
            return new ItemInconsistencia[size];
        }
    };
}