package br.senai.sp.cfp132.pineapplesystems.activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.model.Usuario;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorJson;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "prefsWS";
	public static final String PREFS_FUNC = "prefsFunc";

	TextView tvExibir;
	TextView tvManter;
	EditText etUsuario;
	EditText etSenha;
	CheckBox chkManter;
	CheckBox chkExibir;
	Button btEsqueci;
	Button btEntrar;

	Funcionario f;
	Usuario u;

	SharedPreferences config;

	AlertDialog dialogWS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

		config = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		setContentView(R.layout.layout_login);

		etUsuario = (EditText) findViewById(R.id.etUsuario);
		etSenha = (EditText) findViewById(R.id.etSenha);

		chkExibir = (CheckBox) findViewById(R.id.chkExibir);
		chkManter = (CheckBox) findViewById(R.id.chkManter);
		btEsqueci = (Button) findViewById(R.id.btEsqueci);
		btEntrar = (Button) findViewById(R.id.btEntrar);

		tvExibir = (TextView) findViewById(R.id.tvExibir);
		tvManter = (TextView) findViewById(R.id.tvManter);

		chkExibir.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					etSenha.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				} else {
					etSenha.setInputType(129);
				}
				etSenha.setSelection(etSenha.length());
			}
		});

		if (WebService.getInstance().getIP(getApplicationContext()).isEmpty()) {
			exibirDialogWS();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_settings, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			exibirDialogWS();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void exibirDialogWS() {
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

		View dialogview = inflater.inflate(R.layout.layout_dialog, null);

		final EditText input = (EditText) dialogview
				.findViewById(R.id.etDialog);

		final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		input.setText(prefs.getString("ipPort", ""));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Digite o endereço da Web Service:");
		builder.setView(dialogview);
		builder.setNeutralButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				WebService.getInstance().setIP(getApplicationContext(),
						input.getEditableText().toString());
				dialogWS.dismiss();
			}
		});
		dialogWS = builder.create();
		builder.setCancelable(false);
		dialogWS.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (input.getEditableText().toString().isEmpty()) {
					dialogWS.show();
					input.setSelection(input.getEditableText().toString()
							.length());
				} else {
					WebService.getInstance().setIP(getApplicationContext(),
							input.getEditableText().toString());
				}
			}
		});
		dialogWS.show();

		input.setSelection(input.getEditableText().toString().length());

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void esqueci(View v) {
		startActivity(new Intent(this, EsqueciActivity.class));
	}

	private Funcionario enviarUsuario(String nomeUsuario, String senha)
			throws HttpHostConnectException {
		JSONObject job = new JSONObject();

		try {
			job.put("nomeUsuario", nomeUsuario);
			job.put("senha", senha);
			StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			HttpPost post = new HttpPost(WebService.getInstance().getURL(
					getApplicationContext())
					+ "funcionario/logar");
			post.setEntity(se);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);

			String resposta = EntityUtils.toString(response.getEntity());
			JSONObject obj = new JSONObject(resposta);
			System.out.println(resposta);
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

	public void entrar(View v) {

		if (etUsuario.getEditableText().toString().isEmpty()
				|| (etSenha.getEditableText().toString().isEmpty())) {

			Toast.makeText(this, R.string.toast_vazio, Toast.LENGTH_SHORT)
					.show();

		} else {

			try {
				f = enviarUsuario(etUsuario.getEditableText().toString(),
						etSenha.getEditableText().toString());
			} catch (HttpHostConnectException e) {
				Toast.makeText(getApplicationContext(),
						R.string.endereco_incorreto, Toast.LENGTH_LONG);
			}

			if (f != null) {
				if (chkManter.isChecked()) {
					SharedPreferences prefs = getSharedPreferences(PREFS_FUNC,
							0);
					SharedPreferences.Editor editor = prefs.edit();

					JSONObject jsonFunc;
					try {
						jsonFunc = ConversorJson.converterFuncionario(f);
						editor.putString("funcionario", jsonFunc.toString());
						editor.commit();
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					DataHolder.getInstance().setFunc(f);
				}
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
				finish();
			} else {
				Toast.makeText(this, R.string.usuario_invalido,
						Toast.LENGTH_LONG).show();
			}
		}

	}

	public void checkarManter(View v) {
		chkManter.setChecked(true);
		chkManter.invalidate();

		ViewGroup vg = (ViewGroup) findViewById(R.id.layout);
		vg.invalidate();
	}

	public void checkarExibir(View v) {
		chkExibir.setChecked(true);
		chkExibir.invalidate();

		ViewGroup vg = (ViewGroup) findViewById(R.id.layout);
		vg.invalidate();
	}

}
