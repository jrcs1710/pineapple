package br.senai.sp.cfp132.PineappleSystems.Filtro;


import javax.faces.application.NavigationHandler;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;



import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

public class ControleDeAcessoInterceptor implements PhaseListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		Funcionario func = (Funcionario) Util.carregarObjeto("usuario");
		
		FacesContext context = event.getFacesContext();
		
		String pagina = ((HttpServletRequest)context.getExternalContext().getRequest()).getRequestURI();
		HttpSession sessao = (HttpSession) ((HttpServletRequest)context.getExternalContext().getRequest()).getSession();
		NavigationHandler handler = event.getFacesContext().getApplication()
				.getNavigationHandler();

	

		// Verificar se tem usuário
		if (!temUsuario(sessao)) {
			// Verifica se a url não contem página de funcionários
			if (pagina.endsWith("PineapplePatrimonios/")) {
				handler.handleNavigation(context, null, pagina + "?faces-redirect=true");
				

				// Verificar se a uri aponta para as páginas iniciais
			} else if (!pagina.endsWith("login.xhtml")
					&& !pagina.endsWith("home.xhtml")
					&& !pagina.endsWith("sobre_nos.xhtml")
					&& !pagina.endsWith("fale_conosco.xhtml")) {
				
				
				handler.handleNavigation(context, null, "/login.xhtml?faces-redirect=true");

			} else {
			
				handler.handleNavigation(context, null, pagina + "?faces-redirect=true");
			}
			// Verifica se a sessão não é nula
		} else if (sessao != null && !sessao.isNew()) {
			// Verificar qual o cargo do funcionário
			if (func.getCargo().equals(Cargo.GER)
					&& pagina.contains("gerencia")) {
			
				handler.handleNavigation(context, null, pagina + "?faces-redirect=true");
						

			} else if (func.getCargo().equals(Cargo.RESP)
					&& pagina.contains("funcionario")) {
				handler.handleNavigation(context, null, pagina + "?faces-redirect=true");

			} else if (func.getCargo().equals(Cargo.AUDIT)
					&& pagina.contains("auditor")) {
				handler.handleNavigation(context, null, pagina+ "?faces-redirect=true");

			} else if (pagina.contains("login")) {
				handler.handleNavigation(context, null, pagina+ "?faces-redirect=true");
				sessao.invalidate();

			} else {
				String caminho = null;
				
				// redirecionamento de paginas de erro
				if (func.getCargo().equals(Cargo.GER)) {
				
					caminho = "/gerencia/erro_acesso.xhtml";
					//handler.handleNavigation(context, null, "erro_acesso.xhtml?faces-redirect=true");
					
					Mensagens.erro("Erro ao acessar página", null);
						
				} else if (func.getCargo().equals(Cargo.RESP)) {
					caminho = "/funcionario/erro_acesso.xhtml";
					
				} else if (func.getCargo().equals(Cargo.AUDIT)) {
					caminho = "/auditor/erro_acesso.xhtml";
					
				}
				handler.handleNavigation(context, null, caminho + "?faces-redirect=true");
			}

		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {

		return PhaseId.RESTORE_VIEW;
	}

	private boolean temUsuario(HttpSession sessao) {
		boolean valida = false;

		if (sessao.getAttribute("usuario") != null) {
			valida = true;
		}
	

		return valida;

	}

}
