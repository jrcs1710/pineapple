package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.AuditoriaDao;
import br.senai.sp.cfp132.PineappleSystems.dao.ConferenciaGeralDao;
import br.senai.sp.cfp132.PineappleSystems.model.Auditoria;
import br.senai.sp.cfp132.PineappleSystems.model.ConferenciaGeral;
import br.senai.sp.cfp132.PineappleSystems.util.GMailSender;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;

@Scope("view")
@Controller
@Transactional
public class AgendamentoController {

	@Autowired
	ConferenciaGeralDao confGerDao;
	@Autowired
	AuditoriaDao audDao;

	private Calendar dtFinal;
	private Calendar dtInicial;
	private Date data = new Date();
	private Date dataFinal = new Date();
	private ConferenciaGeral confGeral = new ConferenciaGeral();
	private String nrConferencia;
	private Auditoria auditoria = new Auditoria();
	private long tipo;

	public long getTipo() {
		return tipo;
	}

	public void setTipo(long tipo) {
		this.tipo = tipo;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String getNrConferencia() {
		return nrConferencia;
	}

	public void setNrConferencia(String nrConferencia) {
		this.nrConferencia = nrConferencia;
	}

	public ConferenciaGeral getConfGeral() {
		return confGeral;
	}

	public void setConfGeral(ConferenciaGeral confGeral) {
		this.confGeral = confGeral;
	}

	public Calendar getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(Calendar dtFinal) {
		this.dtFinal = dtFinal;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getHoje() {
		return new Date();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Calendar getDtInicial() {
		return dtInicial;
	}

	public void setDtInicial(Calendar dtInicial) {

		this.dtInicial = dtInicial;
	}

	public void agendarConferencia() {
		dtInicial = Calendar.getInstance();
		dtInicial.setTimeInMillis(data.getTime());
		dtFinal = Calendar.getInstance();
		dtFinal.setTimeInMillis(dataFinal.getTime());
		boolean valida = true;

		if (tipo == 0) {
			List<ConferenciaGeral> conf = confGerDao.buscarTodos();
			for (int i = 0; i < conf.size(); i++) {
				if (conf.get(i).getDtInicio().before(dtInicial)
						&& conf.get(i).getDtFim().after(dtInicial)) {
					valida = false;
				}else {
					if (conf.get(i).getDtInicio().before(dtFinal)
						&& conf.get(i).getDtFim().after(dtFinal)) {
						valida = false;
					}
				}
			}
			if (valida) {
				confGeral.setDtInicio(dtInicial);
				confGeral.setDtFim(dtFinal);
				confGeral.setNrConferencia(nrConferencia.trim());
				if (confGeral.getId() != 0) {
					confGerDao.alterar(confGeral);
				} else {
					confGerDao.inserir(confGeral);
					Mensagens.informacao("Conferência agendada com sucesso!", null);
					data = new Date();
					dataFinal.setDate(data.getDate() + 1);
					gerarCodigo();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								enviarEmail();
							} catch (Exception e) {

								Mensagens
										.erro("Falha ao enviar e-mail. Verifique sua conexão de internet",
												null);
							}

						}
					}).start();

				}
			}else {
				Mensagens.erro("Erro! Ja existe uma conferencia agendada no mesmo período!", null);
			}
			

		} else {
			auditoria.setDtInicio(dtInicial);
			auditoria.setDtFim(dtFinal);
			auditoria.setNrAuditoria(nrConferencia.trim());
			audDao.inserir(auditoria);
		}

	}

	public void enviarEmail() throws Exception {

		GMailSender sender = new GMailSender("pineapplesys@gmail.com",
				"ucantcme");
		sender.sendMail(
				"Nova conferencia",
				"Prezado, \n\nVocê possui uma nova conferência agendada."
						+ " Verifique os ambientes do qual você é responsável!"
						+ "\nInsira o código da conferência no app do seu dispositivo móvel: "
						+ nrConferencia, "pineapplesys@gmail.com",
				"l.felipepaulino@gmail.com, pineapplesys@gmail.com");

	}

	public void gerarCodigo() {
		double codigo;
		codigo = (Math.random() * Calendar.getInstance().getTimeInMillis());

		nrConferencia = String.valueOf(Math.round(codigo));
		nrConferencia = (String) nrConferencia.subSequence(0, 8);

	}

	public void ajustarData() {
		dataFinal.setDate(data.getDate() + 1);
	}

	@PostConstruct
	public void init() {
		dataFinal.setDate(data.getDate() + 1);
		gerarCodigo();
	}
}
