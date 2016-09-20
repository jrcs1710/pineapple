package br.senai.sp.cfp132.PineappleWS.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {

	
		public static final String URL = "http://10.84.146.3:8080/PineappleWS/services/";

		/*
		 * M�todo para enviar a requisi��o � URL e retornar resposta
		 * 
		 * @param url do webservice
		 * 
		 * @return conte�do lido no webservice
		 */
		public static String makeRequest(String url) {		
			// criar uma referencia para HttpURLConnection
			HttpURLConnection conexao = null;
			// criar uma referencia para url
			URL endereco = null;
			// criar string de retorno
			String retorno = null;
			try {
				// instanciar objeto endereco atrav�s da strinf url recebida no
				// m�todo
				endereco = new URL(url);
				// instanciar a conexao abrindo a conexao do endereco
				conexao = (HttpURLConnection) endereco.openConnection();
				conexao.setRequestMethod("POST");
				conexao.setRequestProperty("Content-Type", "application/json");
				//jogar na string retorno os dados lidos atrav�s do inputstream da conexao
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
		 * M�todo para ler um inputStream e retornar seu resultado
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
				// instanciar o reader com o InputStream recebido no m�todo
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
