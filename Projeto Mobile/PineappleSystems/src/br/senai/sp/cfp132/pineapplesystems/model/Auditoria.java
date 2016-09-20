package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Auditoria implements Parcelable {

	private Long id;
	private String nrAuditoria;
	private List<Patrimonio> patrimonio;
	private Calendar dtInicio;
	private Calendar dtFim;
	
	public Auditoria() {
		// TODO Auto-generated constructor stub
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

	public List<Patrimonio> getPatrimonio() {
		return patrimonio;
	}

	public String getNrAuditoria() {
		return nrAuditoria;
	}

	public void setNrAuditoria(String nrAuditoria) {
		this.nrAuditoria = nrAuditoria;
	}

	public void setPatrimonio(List<Patrimonio> patrimonio) {
		this.patrimonio = patrimonio;
	}


    protected Auditoria(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        nrAuditoria = in.readString();
        if (in.readByte() == 0x01) {
            patrimonio = new ArrayList<Patrimonio>();
            in.readList(patrimonio, Patrimonio.class.getClassLoader());
        } else {
            patrimonio = null;
        }
        dtInicio = (Calendar) in.readValue(Calendar.class.getClassLoader());
        dtFim = (Calendar) in.readValue(Calendar.class.getClassLoader());
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
        dest.writeString(nrAuditoria);
        if (patrimonio == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(patrimonio);
        }
        dest.writeValue(dtInicio);
        dest.writeValue(dtFim);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Auditoria> CREATOR = new Parcelable.Creator<Auditoria>() {
        @Override
        public Auditoria createFromParcel(Parcel in) {
            return new Auditoria(in);
        }

        @Override
        public Auditoria[] newArray(int size) {
            return new Auditoria[size];
        }
    };
}