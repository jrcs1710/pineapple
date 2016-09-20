package br.senai.sp.cfp132.PineappleSystems.Util;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import br.senai.sp.cfp132.PineappleSystems.Dao.EmpresaDao;
import br.senai.sp.cfp132.PineappleSystems.model.Empresa;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeScriptlet extends JRDefaultScriptlet {
	public void afterDetailEval() throws JRScriptletException {

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = null;
		try {
			matrix = writer.encode(getFieldValue("cdPatrimonio").toString(),
					BarcodeFormat.QR_CODE, 256, 256);
			this.setVariableValue("BarCodeImage",
					MatrixToImageWriter.toBufferedImage(matrix));
			String s = getFieldValue("cdPatrimonio").toString();
			this.setVariableValue("BarCodeString", s);
			this.setVariableValue("modelo", getFieldValue("modelo").toString());
			this.setVariableValue("tipo", getFieldValue("tipo").toString());
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	
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
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
