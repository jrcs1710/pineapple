package br.senai.sp.cfp132.PineappleSystems.Util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import br.senai.sp.cfp132.PineappleSystems.Dao.EmpresaDao;
import br.senai.sp.cfp132.PineappleSystems.model.Empresa;

public class RelatorioPatrimonioScriptlet extends JRDefaultScriptlet {

	@Override
	public void afterReportInit() throws JRScriptletException {
		EmpresaDao dao = new EmpresaDao();
		List<Empresa> listEmp = dao.buscarTodos();
		Empresa emp = listEmp.get(0);
		this.setVariableValue("EnderecoEmpresa", emp.getEnderecoCompleto());
		this.setVariableValue("NomeEmpresa", emp.getNome());
		this.setVariableValue("Cnpj", emp.getCnpj());
		try {
			this.setVariableValue("LogoTipo", emp.getLogoBuf());
			BufferedImage buf = ImageIO.read(getClass().getResourceAsStream(
					"/relatorio/pineapple_logo.png"));
			this.setVariableValue("LogoTipoPine", buf);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO!",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
