package br.senai.sp.cfp132.pineapplesystems.activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Ambiente;
import br.senai.sp.cfp132.pineapplesystems.model.Auditoria;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.ObjectSerializer;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

public class EfetuaAuditActivity extends Activity {
	public static final int REQUEST_CODE = 0;
	public static final String PREFS_NAME = "prefsAudit";
	public static final String PREFS_FUNC = "prefsFunc";
	HashMap<String, List<String>> listData;
	List<String> listGroup;
	ArrayList<String> listPatrimonios;
	ExpandableListView exp_list;
	ExpandableListAdapter listAdapter;
	ExpListAdapter exp_list_adapter;
	EditText etCdPat;
	Button btSalvarEdit;
	Button btScan;
	Button btSalvarAudit;
	Resources res;

	Funcionario f;
	Ambiente a;
	String nrAuditoria;
	Auditoria auditoria;

	String escaneadosIntent;
	JSONArray jsonResposta;
	AlertDialog ambDialog;
	AlertDialog dialogSucesso;

	List<Ambiente> lista;
	List<String> listStr;
	String resposta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_efetua_audit);
		res = getResources();

		listData = new HashMap<String, List<String>>();
		listGroup = new ArrayList<String>();
		listPatrimonios = new ArrayList<String>();

		nrAuditoria = getIntent().getStringExtra("nrAuditoria");
		
		if (getIntent().hasExtra("escaneados")) {
			escaneadosIntent = getIntent().getStringExtra("escaneados");
			
			JSONArray jsonEscaneados = new JSONArray();
			
			try {
				jsonEscaneados = new JSONArray(escaneadosIntent);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			for (int i = 0; i < jsonEscaneados.length(); i++) {
				String cdPat = jsonEscaneados.optString(i);
				listPatrimonios.add(cdPat);
			}

			if (listGroup.isEmpty()) {
				listGroup.add("Patrimônios");
			}

			listData.put(listGroup.get(0), listPatrimonios);
			
		}

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

		btScan = (Button) findViewById(R.id.btScan);
		btSalvarAudit = (Button) findViewById(R.id.btSalvarAudit);

		etCdPat = (EditText) findViewById(R.id.etCdPat);
		btSalvarEdit = (Button) findViewById(R.id.btSalvarEdit);

		exp_list = (ExpandableListView) findViewById(R.id.exp_list_audit);

		try {
			getPreferences();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		exp_list.setAdapter(new ExpListAdapter(EfetuaAuditActivity.this,
				listData, listGroup, false));

	}


	public void salvarAudit(View view) {
		if (!listPatrimonios.isEmpty()) {

			JSONObject job = new JSONObject();
			JSONArray array = new JSONArray(listPatrimonios);

			try {

				job.put("nrAuditoria", nrAuditoria);
				job.put("listCdPatrimonio", array);
				StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				HttpPost post = new HttpPost(WebService.getInstance().getURL(
						getApplicationContext())
						+ "auditoria/salvar");
				post.setEntity(se);

				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);

				resposta = EntityUtils.toString(response.getEntity());
				if (resposta.equals("sucesso")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle(getString(R.string.audit_salva_sucesso));
					//builder.setMessage("Nenhuma inconsistência encontrada!");
					builder.setNeutralButton("OK", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialogSucesso.dismiss();
							finish();
						}
					});
					dialogSucesso = builder.create();
					dialogSucesso.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							finish();
						}
					});
					dialogSucesso.show();
					listPatrimonios.clear();
					getApplicationContext().getSharedPreferences(PREFS_NAME, 0)
							.edit().clear().commit();				
				} else {
					String toastResposta = res.getQuantityString(
							R.plurals.resposta_conf, listPatrimonios.size());

					listData = new HashMap<String, List<String>>();
					listGroup = new ArrayList<String>();
					listPatrimonios = new ArrayList<String>();

					exp_list.setAdapter(new ExpListAdapter(
							getApplicationContext(), listData, listGroup, false));

					Toast.makeText(getApplicationContext(), toastResposta,
							Toast.LENGTH_LONG).show();
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

		} else {
			Toast.makeText(getApplicationContext(), R.string.lista_conf_vazia,
					Toast.LENGTH_LONG).show();
		}

	}

	public void salvarCdEdit(View view) {
		if (!etCdPat.getEditableText().toString().isEmpty()) {
			if (!listPatrimonios.contains(etCdPat.getEditableText().toString())) {
				listPatrimonios.add(etCdPat.getEditableText().toString());

				if (listGroup.isEmpty()) {
					listGroup.add("Patrimônios");
				}

				listData.put(listGroup.get(0), listPatrimonios);

				exp_list_adapter = new ExpListAdapter(EfetuaAuditActivity.this,
						listData, listGroup, false);

				int numberOfGroups = exp_list_adapter.getGroupCount();
				boolean[] groupExpandedArray = new boolean[numberOfGroups];
				for (int i = 0; i < numberOfGroups; i++)
					groupExpandedArray[i] = exp_list.isGroupExpanded(i);

				exp_list.setAdapter(exp_list_adapter);

				for (int i = 0; i < groupExpandedArray.length; i++)
					if (groupExpandedArray[i] == true) {
						exp_list.expandGroup(i);
						exp_list.setSelection(exp_list_adapter
								.getChildrenCount(i));
					}
				String toastAdicionado = String.format(res
						.getString(R.string.cdPatAdicionado), etCdPat
						.getEditableText().toString());

				Toast.makeText(getApplicationContext(), toastAdicionado,
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(getApplicationContext(),
						R.string.cdPatDuplicado, Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(getApplicationContext(), R.string.campo_vazio,
					Toast.LENGTH_SHORT).show();
		}

	}

	public void callZXing(View view) {
		Intent it = new Intent(getApplicationContext(),
				com.google.zxing.client.android.CaptureActivity.class);
		startActivityForResult(it, REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
			String resultado = data.getStringExtra("SCAN_RESULT");
			if (!listPatrimonios.contains(resultado)) {

				listPatrimonios.add(resultado);

				if (listGroup.isEmpty()) {
					listGroup.add("Patrimônios");
				}

				listData.put(listGroup.get(0), listPatrimonios);

				exp_list_adapter = new ExpListAdapter(getApplicationContext(),
						listData, listGroup, false);

				int numberOfGroups = exp_list_adapter.getGroupCount();
				boolean[] groupExpandedArray = new boolean[numberOfGroups];
				for (int i = 0; i < numberOfGroups; i++)
					groupExpandedArray[i] = exp_list.isGroupExpanded(i);

				exp_list.setAdapter(exp_list_adapter);

				for (int i = 0; i < groupExpandedArray.length; i++)
					if (groupExpandedArray[i] == true) {
						exp_list.expandGroup(i);
						exp_list.setSelection(exp_list_adapter
								.getChildrenCount(i));
					}

				String toastAdicionado = String.format(
						res.getString(R.string.cdPatAdicionado), resultado);

				Toast.makeText(getApplicationContext(), toastAdicionado,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						R.string.cdPatDuplicado, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void getPreferences() throws JSONException {

		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);

		ArrayList<String> listaTemp = null;

		if (prefs.contains("lista")) {
			try {
				listaTemp = (ArrayList<String>) ObjectSerializer
						.deserialize(prefs.getString("lista", ObjectSerializer
								.serialize(new ArrayList<String>())));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (listaTemp != null) {

				listPatrimonios = listaTemp;

				listGroup.add("Patrimônios");

				listData.put(listGroup.get(listGroup.indexOf("Patrimônios")),
						listPatrimonios);

				exp_list_adapter = new ExpListAdapter(getApplicationContext(),
						listData, listGroup, false);

				exp_list.setAdapter(exp_list_adapter);

				for (int i = 0; i < listGroup.size(); i++) {
					exp_list.expandGroup(i);
				}

			}

			listaTemp = null;
		}
	}


}
