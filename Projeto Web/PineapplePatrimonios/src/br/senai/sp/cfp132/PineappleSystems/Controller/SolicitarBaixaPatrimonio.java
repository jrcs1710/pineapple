package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.ItemTransferenciaDao;
import br.senai.sp.cfp132.PineappleSystems.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.ItemTransferencia;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;
import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleSystems.model.TipoMovimentacao;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Transactional
@Scope("view")
@Controller
public class SolicitarBaixaPatrimonio {

	
	@Autowired
	PatrimonioDao patDao;
	@Autowired
	ItemTransferenciaDao itemDao;
	@Autowired
	MovimentacaoDao movDao;
	
	private List<Patrimonio> listPatrimonios;
	private List<ItemTransferencia> listItem;
	private Movimentacao mov = new Movimentacao();
	private Ambiente ambiente;
	
	
	
	
	
	
	
	public List<ItemTransferencia> getListItem() {
		return listItem;
	}

	public void setListItem(List<ItemTransferencia> listItem) {
		this.listItem = listItem;
	}

	public Movimentacao getMov() {
		return mov;
	}

	public void setMov(Movimentacao mov) {
		this.mov = mov;
	}

	public List<Patrimonio> getListPatrimonios() {
		return listPatrimonios;
	}

	public void setListPatrimonios(List<Patrimonio> listPatrimonios) {
		this.listPatrimonios = listPatrimonios;
	}

	

	public String darBaixa() {
		ItemTransferencia it = null;
		listItem = new ArrayList<ItemTransferencia>();
		Funcionario f = (Funcionario) Util.carregarObjeto("usuario");
		for (int i = 0; i < listPatrimonios.size(); i++) {
			
			
			
			it = new ItemTransferencia();
			it.setPatrimonio(listPatrimonios.get(i));
			it.setStatus(true);
			it.setTipoMovimentacao(TipoMovimentacao.SAIDA);
			
			
			itemDao.inserir(it);
			listItem.add(it);
	
		}
		ambiente = listPatrimonios.get(0).getAmbiente();
		mov.setAtual(ambiente);
		mov.setAvaliador(f);
		mov.setDataAprovacao(Calendar.getInstance());
		
		mov.setDtSolicitacao(Calendar.getInstance());
		mov.setSolicitante(f);
		mov.setStatus(StatusRequisicao.ABERTO);
		mov.setPatrimonios(listItem);
		
		//Salvando movimentação
		movDao.inserir(mov);
		
		Mensagens.informacao("Solicitação efetuada com sucesso!", null);
		return "seus_ambientes.xhtml";
	}



	@PostConstruct
	public void init() {

		if (Util.carregarObjeto("patrimonioBaixa") != null) {
			listPatrimonios = (List<Patrimonio>) Util
					.carregarObjeto("patrimonioBaixa");
			Util.removerObjeto("patrimonioBaixa");
		}
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
}
