package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.ItemTransferenciaDao;
import br.senai.sp.cfp132.PineappleSystems.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;
import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Transactional
@Scope("view")
@Controller
public class ListaRequisicaoController {

	@Autowired
	MovimentacaoDao movDao;
	@Autowired
	ItemTransferenciaDao itemDao;
	@Autowired
	PatrimonioDao patDao;

	private List<Movimentacao> listSolicitacaoAberta;

	private Movimentacao selectedMovimentacao;
	private Funcionario avaliador;
	private String observacao;

	public String getObservacao() {

		return observacao;
	}

	public void setObservacao(String observacao) {

		this.observacao = observacao;
	}

	public Funcionario getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Funcionario avaliador) {
		this.avaliador = avaliador;
	}

	public Movimentacao getSelectedMovimentacao() {
		return selectedMovimentacao;
	}

	public void setSelectedMovimentacao(Movimentacao selectedMovimentacao) {
		this.selectedMovimentacao = selectedMovimentacao;
	}

	public List<Movimentacao> getListSolicitacaoAberta() {
		listSolicitacaoAberta = movDao
				.buscarMovimentacao_Status(StatusRequisicao.ABERTO);

		return listSolicitacaoAberta;
	}

	public void setListSolicitacaoAberta(
			List<Movimentacao> listSolicitacaoAberta) {
		this.listSolicitacaoAberta = listSolicitacaoAberta;
	}

	public void aceitar() {

		for (int i = 0; i < selectedMovimentacao.getPatrimonios().size(); i++) {
			if (selectedMovimentacao.getDestino() == null) {
				selectedMovimentacao.getPatrimonios().get(i).getPatrimonio()
						.setDtSaida(Calendar.getInstance());
			}
			selectedMovimentacao.getPatrimonios().get(i).getPatrimonio()
					.setAmbiente(selectedMovimentacao.getDestino());

			patDao.alterar(selectedMovimentacao.getPatrimonios().get(i)
					.getPatrimonio());

		}

		selectedMovimentacao.setAvaliador(avaliador);
		selectedMovimentacao.setStatus(StatusRequisicao.APROV);
		selectedMovimentacao.setDataAprovacao(Calendar.getInstance());
		movDao.alterar(selectedMovimentacao);
		Mensagens.informacao("Solicitação Aprovada", null);
	}

	public void recusar() {

		selectedMovimentacao.setObsAprovador(observacao);
		selectedMovimentacao.setAvaliador(avaliador);
		selectedMovimentacao.setStatus(StatusRequisicao.RECUS);
		selectedMovimentacao.setDataAprovacao(Calendar.getInstance());
		movDao.alterar(selectedMovimentacao);

		Mensagens.informacao("Solicitação Recusada", null);

	}

	@PostConstruct
	private void init() {
		if (Util.carregarObjeto("usuario") != null) {
			avaliador = (Funcionario) Util.carregarObjeto("usuario");
		}

	}
}
