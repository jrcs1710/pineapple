package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ConferenciaGeral implements Parcelable {

	private Long id;
	private List<Conferencia> conferencia;
	private Calendar dtInicio;
	private Calendar dtFim;
	private String nrConferencia;
	
	public ConferenciaGeral() {
		// TODO Auto-generated constructor stub
	}

	public String getNrConferencia() {
		return nrConferencia;
	}

	public void setNrConferencia(String nrConferencia) {
		this.nrConferencia = nrConferencia;
	}

	public Calendar getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Calendar dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Calendar getDtFim() {
		return dtFim;
	}

	public void setDtFim(Calendar dtFim) {
		this.dtFim = dtFim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Conferencia> getConferencia() {
		return conferencia;
	}

	public void setConferencia(List<Conferencia> conferencia) {
		this.conferencia = conferencia;
	}


    protected ConferenciaGeral(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        if (in.readByte() == 0x01) {
            conferencia = new ArrayList<Conferencia>();
            in.readList(conferencia, Conferencia.class.getClassLoader());
        } else {
            conferencia = null;
        }
        dtInicio = (Calendar) in.readValue(Calendar.class.getClassLoader());
        dtFim = (Calendar) in.readValue(Calendar.class.getClassLoader());
        nrConferencia = in.readString();
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
        if (conferencia == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(conferencia);
        }
        dest.writeValue(dtInicio);
        dest.writeValue(dtFim);
        dest.writeString(nrConferencia);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ConferenciaGeral> CREATOR = new Parcelable.Creator<ConferenciaGeral>() {
        @Override
        public ConferenciaGeral createFromParcel(Parcel in) {
            return new ConferenciaGeral(in);
        }

        @Override
        public ConferenciaGeral[] newArray(int size) {
            return new ConferenciaGeral[size];
        }
    };
}