package br.senai.sp.cfp132.PineappleSystems.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import sun.net.www.http.HttpClient;

public class WebService {

	
		public static final String URL = "http://10.84.146.20:8080/PineappleSystems/services/";

		/*
		 * Método para enviar a requisição à URL e retornar resposta
		 * 
		 * @param url do webservice
		 * 
		 * @return conteúdo lido no webservice
		 */
		public static String makeRequest(String url) {		
			// criar uma referencia para HttpURLConnection
			HttpURLConnection conexao = null;
			// criar uma referencia para url
			URL endereco = null;
			// criar string de retorno
			String retorno = null;
			try {
				// instanciar objeto endereco através da strinf url recebida no
				// método
				endereco = new URL(url);
				// instanciar a conexao abrindo a conexao do endereco
				conexao = (HttpURLConnection) endereco.openConnection();
				conexao.setRequestMethod("POST");
				conexao.setRequestProperty("Content-Type", "application/json");
				//jogar na string retorno os dados lidos através do inputstream da conexao
				retorno = lerDados(conexao.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conexao != null) {
					conexao.disconnect();	
				}
				
			}

			return retorno;
		}

		/*
		 * Método para ler um inputStream e retornar seu resultado
		 * 
		 * @param is com os dados a serem lidos
		 * 
		 * @return dados em forma de string
		 */
		private static String lerDados(InputStream is) {
			// criar uma referencia para bufferedReader
			BufferedReader reader = null;
			// criar um StringBuilder
			StringBuilder builder = new StringBuilder();

			try {
				// instanciar o reader com o InputStream recebido no método
				reader = new BufferedReader(new InputStreamReader(is));
				// cria uma string para a linha a ser lida
				String linha = null;
				// enquanto ouver linhas, guarda o conteudo na string linha
				while ((linha = reader.readLine()) != null) {
					// acrescentar a linha ao builder
					builder.append(linha + "\n");

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}

			return builder.toString();
		}


	
}
