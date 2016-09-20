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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorJson;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

public class AuditoriaActivity extends Activity {
	public static final String PREFS_FUNC = "prefsFunc";
	public static final String PREFS_NAME = "prefsAudit";
	EditText etAudit;

	Button btEnviar;
	Button btSair;

	Funcionario f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_audit);

		SharedPreferences prefs = getSharedPreferences(PREFS_FUNC, 0);
		if (prefs.contains("funcionario")) {
			JSONObject jsonFunc;
			try {
				jsonFunc = new JSONObject(prefs.getString("funcionario", ""));
				f = ConversorObject.converterFuncionario(jsonFunc);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (DataHolder.getInstance().getFunc() != null) {
			f = DataHolder.getInstance().getFunc();
		}

		etAudit = (EditText) findViewById(R.id.etAudit);
		btEnviar = (Button) findViewById(R.id.btEnviar);
		btSair = (Button) findViewById(R.id.btSair);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}	
	
	public void sair(View v){
		getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit()
				.clear().commit();
		getApplicationContext().getSharedPreferences(PREFS_FUNC, 0).edit()
		.clear().commit();		
		DataHolder.getInstance().setFunc(null);
		
		startActivity(new Intent(this, LoginActivity.class));
		finish();
		
	}

	public void enviar(View v) {
		if (!etAudit.getEditableText().toString().isEmpty()) {
			try {
				enviarNrAudit(etAudit.getEditableText().toString(), f);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(), R.string.campo_vazio,
					Toast.LENGTH_SHORT).show();
		}

	}

	private void enviarNrAudit(String nrAuditoria, Funcionario func)
			throws JSONException {
		JSONObject job = new JSONObject();
		JSONObject funcJSON = ConversorJson.converterFuncionario(func);

		try {
			job.put("nrAuditoria", nrAuditoria);
			job.put("auditor", funcJSON);
			StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			HttpPost post = new HttpPost(WebService.getInstance().getURL(
					getApplicationContext())
					+ "auditoria/buscarNrAudit");
			post.setEntity(se);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);

			String resposta = EntityUtils.toString(response.getEntity());
			System.out.println(resposta);
			
			if (resposta.endsWith("}") || resposta.endsWith("]")) {
				Intent i = new Intent(getApplicationContext(),
						EfetuaAuditActivity.class);
				i.putExtra("nrAuditoria", nrAuditoria);
				i.putExtra("escaneados", resposta);
				startActivity(i);
				
			} else if (resposta.equals("OK")){
				Intent i = new Intent(getApplicationContext(),
						EfetuaAuditActivity.class);
				i.putExtra("nrAuditoria", nrAuditoria);
				startActivity(i);
				
			} else {
				Toast.makeText(this, R.string.nrAuditInvalido, Toast.LENGTH_SHORT)
						.show();
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
	}
}
