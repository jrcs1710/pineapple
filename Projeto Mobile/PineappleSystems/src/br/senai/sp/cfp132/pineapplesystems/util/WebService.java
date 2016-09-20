package br.senai.sp.cfp132.pineapplesystems.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;

public class WebService {
	public static final String PREFS_NAME = "prefsWS";
	public String ipPort;
	public static final String URL = "/PineappleWS/services/";

	private static final WebService WS = new WebService();

	public static WebService getInstance() {
		return WS;
	}
	
	public String getURL(Context ctx){		
		String URLfinal;
		ipPort = getIP(ctx);
		if (!ipPort.contains("http://")) {
			URLfinal = "http://"+ipPort+URL;
		} else{
			URLfinal = ipPort+URL;
		}
		return URLfinal;
	}

	public String getIP(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);

		ipPort = prefs.getString("ipPort", "");
		return ipPort;
	}

	public void setIP(Context ctx, String ip) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putString("ipPort", ip.trim());
		editor.commit();
	}

	/**
	 * Método para enviar a requisição à URL e receber a resposta
	 * 
	 * @param url
	 *            do webservice
	 * @return conteúdo lido no webservice
	 */
	public static String makeRequest(String url) {
		// criar uma referencia para HTTPURLConnection
		HttpURLConnection conexao = null;
		// criar uma referencia pra URL
		URL endereco = null;
		// criar a string de retorno
		String retorno = "";
		try {
			// instanciar a URL através da String url recebida no metodo
			endereco = new URL(url);
			// instanciar a conexao abrindo a conexao do endereço
			conexao = (HttpURLConnection) endereco.openConnection();
			// jogar na string retorno os dados lidos atraves do inputstream da
			// conexao
			retorno = lerDados(conexao.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conexao != null) {
				// desconecta
				conexao.disconnect();
			}
		}
		// retorna o conteudo lido na requisição
		return retorno;
	}

	/**
	 * Método para ler um InputStream e retornar seu resultado
	 * 
	 * @param is
	 *            com os dados a serem lidos
	 * @return dados em forma de String
	 */
	private static String lerDados(InputStream is) {
		// criar uma referencia para BufferedReader
		BufferedReader reader = null;
		// criar um StringBuilder
		StringBuilder builder = new StringBuilder();
		try {
			// instanciar o reader com o InputStream recebido no metodo
			reader = new BufferedReader(new InputStreamReader(is));
			// criar uma String para a linha a ser lida
			String linha = null;
			// enquanto houver linhas, guarda o conteudo na String linha
			while ((linha = reader.readLine()) != null) {
				// acrescentar a linha ao builder
				builder.append(linha + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// se o reader nao for nulo, tenta fecha-lo
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		// retorna o conteudo lido
		return builder.toString();
	}

}
