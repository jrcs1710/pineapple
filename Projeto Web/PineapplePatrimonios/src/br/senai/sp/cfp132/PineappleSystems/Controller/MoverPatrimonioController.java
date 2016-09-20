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
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.ItemTransferencia;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;
import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleSystems.model.TipoMovimentacao;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Transactional
@Controller
@Scope("view")
public class MoverPatrimonioController {

	@Autowired
	MovimentacaoDao movDao;
	@Autowired
	ItemTransferenciaDao itemDao;
	@Autowired
	PatrimonioDao
	patDao;

	private List<Patrimonio> listPatrimonios;
	private List<ItemTransferencia> listItem;
	private Movimentacao mov = new Movimentacao();

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

	public List<ItemTransferencia> getListItem() {
		return listItem;
	}

	public void setListItem(List<ItemTransferencia> listItem) {
		this.listItem = listItem;
	}

	public String darBaixa() {
		ItemTransferencia it = null;
		listItem = new ArrayList<ItemTransferencia>();
		Funcionario f = (Funcionario) Util.carregarObjeto("usuario");
		for (int i = 0; i < listPatrimonios.size(); i++) {
			listPatrimonios.get(i).setDtSaida(Calendar.getInstance());
			listPatrimonios.get(i).setAmbiente(null);
			patDao.alterar(listPatrimonios.get(i));
			it = new ItemTransferencia();
			it.setPatrimonio(listPatrimonios.get(i));
			it.setStatus(true);
			it.setTipoMovimentacao(TipoMovimentacao.SAIDA);
			
			
			itemDao.inserir(it);
			listItem.add(it);
	
		}
		
		
		mov.setAvaliador(f);
		mov.setDataAprovacao(Calendar.getInstance());
		
		mov.setDtSolicitacao(Calendar.getInstance());
		mov.setSolicitante(f);
		mov.setStatus(StatusRequisicao.APROV);
		mov.setPatrimonios(listItem);
		mov.setObsSolicitante(mov.getObsAprovador());
		//Salvando movimentação
		movDao.inserir(mov);
		
		Mensagens.informacao("Baixa efetuada com sucesso!", null);
		return "lista_patrimonio.xhtml";
	}

	@PostConstruct
	public void init() {

		if (Util.carregarObjeto("patrimonioBaixa") != null) {
			listPatrimonios = (List<Patrimonio>) Util
					.carregarObjeto("patrimonioBaixa");
			Util.removerObjeto("patrimonioBaixa");
		}
	}

}
