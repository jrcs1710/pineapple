package br.senai.sp.cfp132.pineapplesystems.activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.model.Usuario;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.GMailSender;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

public class EsqueciActivity extends Activity {
	EditText etUsuario;
	EditText etEmail;
	Button btEnviar;
	Handler handler;
	EmailAsync email;

	Funcionario f;
	Usuario u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_esqueci);

		etUsuario = (EditText) findViewById(R.id.etUsuario);
		etEmail = (EditText) findViewById(R.id.etEmail);
		btEnviar = (Button) findViewById(R.id.btEnviar);

		handler = new Handler();

		btEnviar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				email = new EmailAsync();
				email.execute();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (email.getStatus() == AsyncTask.Status.RUNNING) {
							email.cancel(true);
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(),
											R.string.falha_conexao,
											Toast.LENGTH_LONG).show();
								}
							});
						}
					}
				}, 15000);
			}
		});

	}

	public class EmailAsync extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			enviar();
			return null;
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void enviar() {
		if (etUsuario.getEditableText().toString().isEmpty()
				|| (etEmail.getEditableText().toString().isEmpty())) {

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(),
							R.string.toast_vazio, Toast.LENGTH_SHORT).show();
				}

			});
		} else {
			Funcionario func = enviarUsuario(etUsuario.getEditableText()
					.toString(), etEmail.getEditableText().toString());

			if (func != null) {
				if (isOnline() == true) {
					try {
						GMailSender sender = new GMailSender(
								"pineapplesys@gmail.com", "ucantcme");
						sender.sendMail("Alteração de senha", "Prezado " + func.getNome() +
								", \n\nRecebemos um pedido de mudança de senha para a sua conta."
								+ " Segue abaixo os dados do seu login: \n\n"
								+ "Usuário: " + func.getUsuario().getNomeUsuario() + " \n"
								+ "Senha: " + func.getUsuario().getSenha() + " \n\n"
								+ "Atenciosamente, \n"
								+ "Equipe Pineapple Systems"								
								,"pineapplesys@gmail.com",
								etEmail.getEditableText().toString());
						System.out.println(func.getUsuario().getSenha());
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(),
										R.string.email_enviado,
										Toast.LENGTH_LONG).show();
							}
						});
						finish();
					} catch (Exception e) {
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(),
										R.string.falha_conexao,
										Toast.LENGTH_LONG).show();
							}
						});
					}
				} else {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									R.string.falha_conexao, Toast.LENGTH_LONG)
									.show();
						}
					});
				}
			} else {
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								R.string.usuario_invalido, Toast.LENGTH_LONG)
								.show();
					}
				});
			}
		}
	}

	private Funcionario enviarUsuario(String nomeUsuario, String email) {
		JSONObject job = new JSONObject();

		try {
			job.put("nomeUsuario", nomeUsuario);
			job.put("email", email);
			StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			HttpPost post = new HttpPost(WebService.getInstance().getURL(
					getApplicationContext()) + "funcionario/alterarSenha");
			post.setEntity(se);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			String resposta = EntityUtils.toString(response.getEntity());
			System.out.println(resposta);
			JSONObject obj = new JSONObject(resposta);

			if (!obj.isNull("id")) {
				f = ConversorObject.converterFuncionario(obj);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return f;
	}
}
