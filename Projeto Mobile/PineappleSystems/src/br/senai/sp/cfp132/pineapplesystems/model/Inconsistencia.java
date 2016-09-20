package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Inconsistencia implements Parcelable {

	private Long id;
	private List<ItemInconsistencia> itemInconsistencia;
	private Conferencia conferencia;
	
	public Inconsistencia() {
		// TODO Auto-generated constructor stub
	}

	public Conferencia getConferencia() {
		return conferencia;
	}

	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemInconsistencia> getItemInconsistencia() {
		return itemInconsistencia;
	}

	public void setItemInconsistencia(
			List<ItemInconsistencia> itemInconsistencia) {
		this.itemInconsistencia = itemInconsistencia;
	}


    protected Inconsistencia(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        if (in.readByte() == 0x01) {
            itemInconsistencia = new ArrayList<ItemInconsistencia>();
            in.readList(itemInconsistencia, ItemInconsistencia.class.getClassLoader());
        } else {
            itemInconsistencia = null;
        }
        conferencia = (Conferencia) in.readValue(Conferencia.class.getClassLoader());
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
        if (itemInconsistencia == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(itemInconsistencia);
        }
        dest.writeValue(conferencia);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Inconsistencia> CREATOR = new Parcelable.Creator<Inconsistencia>() {
        @Override
        public Inconsistencia createFromParcel(Parcel in) {
            return new Inconsistencia(in);
        }

        @Override
        public Inconsistencia[] newArray(int size) {
            return new Inconsistencia[size];
        }
    };
}