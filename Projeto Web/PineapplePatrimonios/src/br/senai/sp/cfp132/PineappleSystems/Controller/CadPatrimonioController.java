package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleSystems.dao.ItemTransferenciaDao;
import br.senai.sp.cfp132.PineappleSystems.dao.ModeloDao;
import br.senai.sp.cfp132.PineappleSystems.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleSystems.dao.TipoDao;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Empresa;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.ItemTransferencia;
import br.senai.sp.cfp132.PineappleSystems.model.Modelo;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;
import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleSystems.model.Tipo;
import br.senai.sp.cfp132.PineappleSystems.model.TipoMovimentacao;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Transactional
@Scope("session")
public class CadPatrimonioController {

	@Autowired
	PatrimonioDao patDao;
	@Autowired
	AmbienteDao ambDao;
	@Autowired
	ModeloDao modDao;
	@Autowired
	TipoDao tipoDao;
	@Autowired
	ItemTransferenciaDao itemDao;
	@Autowired
	MovimentacaoDao movDao;
	private Movimentacao mov;
	private ItemTransferencia itemTrans;
	private Patrimonio patrimonio = new Patrimonio();
	private Ambiente ambiente = new Ambiente();
	private Modelo modelo = new Modelo();
	private Empresa empresa = new Empresa();
	private String quantidade;
	private Tipo tipo = new Tipo();
	private List<Ambiente> listAmbientes;
	private List<Tipo> listTipos;
	private List<Modelo> listModelos;
	private Long ambId;
	private Long tipoId;
	private Long modId;
	private Funcionario func;
	

	
	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}

	public Movimentacao getMov() {
		return mov;
	}

	public void setMov(Movimentacao mov) {
		this.mov = mov;
	}

	public ItemTransferencia getItemTrans() {
		return itemTrans;
	}

	public void setItemTrans(ItemTransferencia itemTrans) {
		this.itemTrans = itemTrans;
	}

	public Long getModId() {
		return modId;
	}

	public void setModId(Long modId) {
		this.modId = modId;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {

		this.tipoId = tipoId;
	}

	public List<Tipo> getListTipos() {
		listTipos = tipoDao.buscarTodos();
		return listTipos;
	}

	public void setListTipos(List<Tipo> listTipos) {
		this.listTipos = listTipos;
	}

	public List<Modelo> getListModelos() {
		if (tipoId != null) {
			tipo = (Tipo) tipoDao.buscarId(tipoId);

			listModelos = modDao.buscarModeloAtivo_Tipo(tipo);
		}

		return listModelos;
	}

	public void setListModelos(List<Modelo> listModelos) {
		this.listModelos = listModelos;
	}

	public Long getAmbId() {
		return ambId;
	}

	public void setAmbId(Long ambId) {
		this.ambId = ambId;
	}

	public List<Ambiente> getListAmbientes() {
		listAmbientes = ambDao.buscarTodosAtivos();
		return listAmbientes;
	}

	public void setAmbientes(List<Ambiente> ambientes) {
		this.listAmbientes = ambientes;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String cadastrarPatrimonio() {

		empresa = (Empresa) Util.carregarObjeto("empresa");
		return "cadastro_patrimonio.xhtml";
	}

	public String salvar() {

		modelo = (Modelo) modDao.buscarId(modId);
		patrimonio.setModelo(modelo);

		if (patrimonio.getId() != 0) {
			Patrimonio pat = patDao.buscarPatrimonio_cdPatrimonio(patrimonio
					.getCdPatrimonio().trim());

			if (pat != null) {

				if (pat.equals(patrimonio)) {
					patDao.alterar(patrimonio);
					Mensagens.informacao(
							"Sucesso! Dados alterados com sucesso!", null);
					return "lista_patrimonio.xhtml";
					
				} else {
					Mensagens.erro("Erro! Número de patrimonio duplicado!",
							null);
				}
			} else {
				Mensagens.erro("Erro! Número de patrimonio duplicado!", null);
			}

		} else {

			boolean validador = true;
			int cdPatrimonio = Integer.parseInt(patrimonio.getCdPatrimonio().trim());
			patrimonio.setCdPatrimonio(String.valueOf(cdPatrimonio));
			patrimonio.setDtEntrada(Calendar.getInstance());
			ambiente = (Ambiente) ambDao.buscarId(ambId);
			patrimonio.setAmbiente(ambiente);
			mov = new  Movimentacao();
			List<ItemTransferencia> listItem = new ArrayList<ItemTransferencia>();
			for (int i = 0; i < Integer.parseInt(quantidade); i++) {
				if (patDao.buscarPatrimonio_cdPatrimonio(String
						.format("%0" + empresa.getMascara().length() + "d",
								cdPatrimonio)) == null) {
					Patrimonio p = new Patrimonio();
					p.setAmbiente(patrimonio.getAmbiente());
					// tratativa de n de patrimonio

					String numero = String
							.format("%0" + empresa.getMascara().length() + "d",
									cdPatrimonio);

					p.setCdPatrimonio(numero);
					p.setDescricao(patrimonio.getDescricao().trim());
					p.setDtEntrada(patrimonio.getDtEntrada());
					p.setModelo(patrimonio.getModelo());

					patDao.inserir(p);
					itemTrans = new ItemTransferencia();
					itemTrans.setPatrimonio(p);
					itemTrans.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
					itemDao.inserir(itemTrans);
					listItem.add(itemTrans);
					cdPatrimonio++;
				} else {
					validador = false;
					break;
				}

			}
			if (validador) {
				mov.setAvaliador(func);
				mov.setDataAprovacao(Calendar.getInstance());
				mov.setDestino(ambiente);
				mov.setDtSolicitacao(Calendar.getInstance());
				mov.setSolicitante(func);
				mov.setStatus(StatusRequisicao.APROV);
				mov.setPatrimonios(listItem);
				movDao.inserir(mov);
				Mensagens.informacao("Sucesso! Dados cadastrados com sucesso!",
						null);
				ambId = null;
				tipoId = null;
				modId = null;
				patrimonio = new Patrimonio();
				return "lista_patrimonio.xhtml";
			} else {
				Mensagens.erro("Erro! "+ String
						.format("%0" + empresa.getMascara().length() + "d",
								cdPatrimonio)+ " é um número de patrimonio duplicado!", null);
			}

			patrimonio = new Patrimonio();
			modelo = new Modelo();
			tipo = new Tipo();
			ambiente = new Ambiente();

		}
		return "";

	}

	@PostConstruct
	public void init() {
		
		if (Util.carregarObjeto("patrimonioAlt") != null) {
			patrimonio = (Patrimonio) Util.carregarObjeto("patrimonioAlt");
			Util.removerObjeto("patrimonioAlt");
			modId = patrimonio.getModelo().getId();
			tipoId = patrimonio.getModelo().getTipo().getId();
			ambId = patrimonio.getAmbiente().getId();
			quantidade = String.valueOf(1);
		}
		if (Util.carregarObjeto("empresa") != null) {
			empresa = (Empresa) Util.carregarObjeto("empresa");
		}
		if (Util.carregarObjeto("usuario") != null) {
			func = (Funcionario) Util.carregarObjeto("usuario");
		}
		
	}
	


}
