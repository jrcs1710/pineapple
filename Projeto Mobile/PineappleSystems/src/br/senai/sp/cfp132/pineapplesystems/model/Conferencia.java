package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Conferencia implements Parcelable {

	private Long id;
	private Ambiente ambiente = new Ambiente();
	private boolean status;
	private Calendar dtFim;
	private Calendar dtInicio;
	private List<Patrimonio> patrimonio;
	
	public Conferencia() {
		// TODO Auto-generated constructor stub
	}

	public List<Patrimonio> getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(List<Patrimonio> patrimonio) {
		this.patrimonio = patrimonio;
	}

	public Calendar getDtFim() {
		return dtFim;
	}

	public void setDtFim(Calendar dtFim) {
		this.dtFim = dtFim;
	}

	public Calendar getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Calendar dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


    protected Conferencia(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        ambiente = (Ambiente) in.readValue(Ambiente.class.getClassLoader());
        status = in.readByte() != 0x00;
        dtFim = (Calendar) in.readValue(Calendar.class.getClassLoader());
        dtInicio = (Calendar) in.readValue(Calendar.class.getClassLoader());
        if (in.readByte() == 0x01) {
            patrimonio = new ArrayList<Patrimonio>();
            in.readList(patrimonio, Patrimonio.class.getClassLoader());
        } else {
            patrimonio = null;
        }
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
        dest.writeValue(ambiente);
        dest.writeByte((byte) (status ? 0x01 : 0x00));
        dest.writeValue(dtFim);
        dest.writeValue(dtInicio);
        if (patrimonio == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(patrimonio);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Conferencia> CREATOR = new Parcelable.Creator<Conferencia>() {
        @Override
        public Conferencia createFromParcel(Parcel in) {
            return new Conferencia(in);
        }

        @Override
        public Conferencia[] newArray(int size) {
            return new Conferencia[size];
        }
    };
}