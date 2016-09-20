package br.senai.sp.cfp132.pineapplesystems.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Movimentacao implements Parcelable {

	private Long id;
	private Calendar dataAprovacao;
	private Funcionario solicitante;
	private Funcionario avaliador;
	private List<ItemTransferencia> patrimonios;
	private StatusRequisicao status;
	private Ambiente destino;
	private Ambiente atual;
	private String obsAprovador;
	private String obsSolicitante;
	private Calendar dtSolicitacao;

	/*---------------------------------------------------------------------------------------------------*/

	public Long getId() {
		return id;
	}

	public Ambiente getDestino() {
		return destino;
	}

	public void setDestino(Ambiente destino) {
		this.destino = destino;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusRequisicao getStatus() {
		return status;
	}

	public void setStatus(StatusRequisicao status) {
		this.status = status;
	}

	public Funcionario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Funcionario solicitante) {
		this.solicitante = solicitante;
	}

	public Funcionario getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Funcionario avaliador) {
		this.avaliador = avaliador;
	}

	public List<ItemTransferencia> getPatrimonios() {
		return patrimonios;
	}

	public void setPatrimonios(List<ItemTransferencia> patrimonios) {
		this.patrimonios = patrimonios;
	}

	public Calendar getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Calendar dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public Ambiente getAtual() {
		return atual;
	}

	public void setAtual(Ambiente atual) {
		this.atual = atual;
	}

	public String getObsAprovador() {
		return obsAprovador;
	}

	public void setObsAprovador(String obsAprovador) {
		this.obsAprovador = obsAprovador;
	}

	public String getObsSolicitante() {
		return obsSolicitante;
	}

	public void setObsSolicitante(String obsSolicitante) {
		this.obsSolicitante = obsSolicitante;
	}

	public Calendar getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(Calendar dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

	public Movimentacao() {
		// TODO Auto-generated constructor stub
	}

	protected Movimentacao(Parcel in) {
		id = in.readByte() == 0x00 ? null : in.readLong();
		dataAprovacao = (Calendar) in
				.readValue(Calendar.class.getClassLoader());
		solicitante = (Funcionario) in.readValue(Funcionario.class
				.getClassLoader());
		avaliador = (Funcionario) in.readValue(Funcionario.class
				.getClassLoader());
		if (in.readByte() == 0x01) {
			patrimonios = new ArrayList<ItemTransferencia>();
			in.readList(patrimonios, ItemTransferencia.class.getClassLoader());
		} else {
			patrimonios = null;
		}
		status = (StatusRequisicao) in.readValue(StatusRequisicao.class
				.getClassLoader());
		destino = (Ambiente) in.readValue(Ambiente.class.getClassLoader());
		atual = (Ambiente) in.readValue(Ambiente.class.getClassLoader());
		obsAprovador = in.readString();
		obsSolicitante = in.readString();
		dtSolicitacao = (Calendar) in
				.readValue(Calendar.class.getClassLoader());
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
		dest.writeValue(dataAprovacao);
		dest.writeValue(solicitante);
		dest.writeValue(avaliador);
		if (patrimonios == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(patrimonios);
		}
		dest.writeValue(status);
		dest.writeValue(destino);
		dest.writeValue(atual);
		dest.writeString(obsAprovador);
		dest.writeString(obsSolicitante);
		dest.writeValue(dtSolicitacao);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Movimentacao> CREATOR = new Parcelable.Creator<Movimentacao>() {
		@Override
		public Movimentacao createFromParcel(Parcel in) {
			return new Movimentacao(in);
		}

		@Override
		public Movimentacao[] newArray(int size) {
			return new Movimentacao[size];
		}
	};
}