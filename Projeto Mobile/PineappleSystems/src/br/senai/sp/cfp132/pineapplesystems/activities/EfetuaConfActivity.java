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
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Ambiente;
import br.senai.sp.cfp132.pineapplesystems.model.Cargo;
import br.senai.sp.cfp132.pineapplesystems.model.Conferencia;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorJson;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.ObjectSerializer;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

public class EfetuaConfActivity extends Activity {
	public static final int REQUEST_CODE = 0;
	public static final String PREFS_NAME = "prefsConf";
	public static final String PREFS_FUNC = "prefsFunc";
	EditText input;
	HashMap<String, List<String>> listData;
	List<String> listGroup;
	ArrayList<String> listPatrimonios;
	ExpandableListView exp_list;
	ExpListAdapter exp_list_adapter;
	EditText etCdPat;
	Button btSalvarEdit;
	Button btScan;
	Button btSalvarConf;
	Button btEnviarRequisicao;
	Button btFinalizar;
	Resources res;

	Funcionario f;
	Ambiente a;
	String nrConferencia;
	Conferencia conf;

	String ambIntent;
	JSONArray jsonResposta;
	AlertDialog ambDialog;
	AlertDialog dialogInconsis;
	AlertDialog dialogSucesso;
	AlertDialog dialogLimpar;
	AlertDialog dialogResultado;
	AlertDialog dialogTransf;
	AlertDialog dialogSair;

	List<Ambiente> listaAmb;
	List<String> listStr;
	String resposta;
	boolean setouAmbiente;
	boolean finalizou = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_efetua_conf);
		res = getResources();

		nrConferencia = getIntent().getStringExtra("nrConferencia");
		ambIntent = getIntent().getStringExtra("listaAmb");

		listData = new HashMap<String, List<String>>();
		listGroup = new ArrayList<String>();
		listPatrimonios = new ArrayList<String>();

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
		btSalvarConf = (Button) findViewById(R.id.btSalvarConf);

		etCdPat = (EditText) findViewById(R.id.etCdPat);
		btSalvarEdit = (Button) findViewById(R.id.btSalvarEdit);

		btEnviarRequisicao = (Button) findViewById(R.id.btEnviarRequisicao);
		btEnviarRequisicao.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN){	
					criarDialogTransf();
				}
				return false;
			}
		});
		
		if (f.getCargo() == Cargo.GER) {
			btEnviarRequisicao.setText(getString(R.string.transf_pat)); 
		}
		
		btFinalizar = (Button) findViewById(R.id.btFinalizar);

		exp_list = (ExpandableListView) findViewById(R.id.exp_list_conf);

		exp_list.setAdapter(new ExpListAdapter(EfetuaConfActivity.this,
				listData, listGroup, false));

		try {
			getPreferences();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			jsonResposta = new JSONArray(ambIntent);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		if (jsonResposta.length() == 1) {
			for (int i = 0; i < jsonResposta.length(); i++) {
				JSONObject json = jsonResposta.optJSONObject(i);
				try {
					a = ConversorObject.converterAmbiente(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else if (a == null) {
			criarDialogAmb();
		}

	}

	@Override
	protected void onStop() {
		super.onStop();

		if (finalizou == false) {
			SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = prefs.edit();
			if (!listPatrimonios.isEmpty()) {

				try {
					if (!prefs.contains("estagio")) {
						if (prefs.getInt("estagio", 1) == 1) {
							editor.putString("lista",
									ObjectSerializer.serialize(listPatrimonios));							
						} else {
							editor.remove("lista");
						}
					}
					JSONObject jsonAmbiente = ConversorJson
							.converterAmbiente(a);
					editor.putString("ambiente", jsonAmbiente.toString());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				editor.commit();

			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);

		if (prefs.getInt("estagio", 1) == 1) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.actions_cancel, menu);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_cancel:
			exibirDialogLimpar();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void finalizar(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Deseja realmente finalizar a conferência?");
		builder.setMessage("Caso a conferência atual seja finalizada, a lista será perdida!");
		builder.setPositiveButton("Sim", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit()
						.clear().commit();
				
				finalizou = true;
				finish();
			}
		});
		builder.setNegativeButton("Não", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogResultado.dismiss();				
			}
		});
		dialogResultado = builder.create();
		dialogResultado.show();
	}

	public void exibirDialogLimpar() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Deseja limpar a lista e recomeçar a conferência?");
		builder.setPositiveButton(getString(R.string.sim),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						getApplicationContext()
								.getSharedPreferences(PREFS_NAME, 0).edit()
								.clear().commit();

						if (jsonResposta.length() != 1) {
							a = null;
						}

						listData = new HashMap<String, List<String>>();
						listGroup = new ArrayList<String>();
						listPatrimonios = new ArrayList<String>();

						exp_list = (ExpandableListView) findViewById(R.id.exp_list_conf);

						exp_list.setAdapter(new ExpListAdapter(
								EfetuaConfActivity.this, listData, listGroup,
								false));

						alterarVisibilidade(1);

						if (a == null) {
							criarDialogAmb();
						}

					}
				});
		builder.setNegativeButton(getString(R.string.nao),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialogLimpar.dismiss();
					}
				});
		dialogLimpar = builder.create();
		dialogLimpar.show();

	}

	public void salvarConf(View view) {
		if (!listPatrimonios.isEmpty()) {
			etCdPat.clearFocus();
			conf = new Conferencia();
			conf.setAmbiente(a);
			conf.setStatus(true);

			JSONObject job = new JSONObject();
			JSONObject jobConf;
			JSONArray array = new JSONArray(listPatrimonios);
			System.out.println(array.toString());

			try {
				jobConf = ConversorJson.converterConferencia(conf);

				job.put("nrConferencia", nrConferencia);
				job.put("listCdPatrimonio", array);
				job.put("conferencia", jobConf);
				StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				HttpPost post = new HttpPost(WebService.getInstance().getURL(
						getApplicationContext())
						+ "conferencia/salvar");
				post.setEntity(se);

				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);

				resposta = EntityUtils.toString(response.getEntity());
				System.out.println(resposta);
				if (resposta.equals("sucesso")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle(getString(R.string.conf_salva_sucesso));
					builder.setMessage("Nenhuma inconsistência encontrada!");
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
				} else if (resposta.endsWith("}") || resposta.endsWith("]")) {
					exibirResultado();

				} else if (resposta.equals("Conferência repetida")) {
					String toastResposta = res.getQuantityString(
							R.plurals.resposta_conf, listPatrimonios.size());

					listData = new HashMap<String, List<String>>();
					listGroup = new ArrayList<String>();
					listPatrimonios = new ArrayList<String>();

					exp_list.setAdapter(new ExpListAdapter(
							EfetuaConfActivity.this, listData, listGroup, false));

					Toast.makeText(getApplicationContext(), resposta,
							Toast.LENGTH_LONG).show();
				} else {
					String toastResposta = res.getQuantityString(
							R.plurals.resposta_conf, listPatrimonios.size());

					listData = new HashMap<String, List<String>>();
					listGroup = new ArrayList<String>();
					listPatrimonios = new ArrayList<String>();

					exp_list.setAdapter(new ExpListAdapter(
							EfetuaConfActivity.this, listData, listGroup, false));

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

				exp_list_adapter = new ExpListAdapter(EfetuaConfActivity.this,
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

	public void enviarRequisicao() {
		List<String> listStr = new ArrayList<String>();

		int index = listGroup.indexOf("Patrimônios com ambiente errado");
		for (int i = 0; i < listData.get(exp_list_adapter.getGroup(index))
				.size(); i++) {
			listStr.add(exp_list_adapter.getChild(index, i).toString());

		}

		try {
		JSONObject job = new JSONObject();
		JSONArray jsonArray = new JSONArray(listStr);
		JSONObject jobFunc = ConversorJson.converterFuncionario(f);
		JSONObject jobAmb = ConversorJson.converterAmbiente(a);
		
		if (!input.getEditableText().toString().isEmpty()) {
			job.put("observacao", input.getEditableText().toString());
			
		}

			job.put("listaNumeros", jsonArray);
			job.put("solicitante", jobFunc);
			job.put("destino", jobAmb);
			StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			HttpPost post = new HttpPost(WebService.getInstance().getURL(
					getApplicationContext())
					+ "movimentacao/salvarTodas");
			post.setEntity(se);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);

			String resposta = EntityUtils.toString(response.getEntity());
			Toast.makeText(getApplicationContext(), resposta, Toast.LENGTH_LONG)
					.show();
			getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit()
					.clear().commit();
			
			if (listGroup.size() > 1) {	
				listData.remove("Patrimônios com ambiente errado");	
				listGroup.remove(index);

				exp_list_adapter = new ExpListAdapter(EfetuaConfActivity.this,
						listData, listGroup, true);
				exp_list_adapter.notifyDataSetChanged();
				
				exp_list.setAdapter(exp_list_adapter);
				
				ViewGroup vg = (ViewGroup) findViewById(R.id.layout_conf);
				vg.invalidate();
				
				alterarVisibilidade(3);
				
			} else {				
				finalizou = true;
				finish();				
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void callZXing(View view) {
		Intent it = new Intent(EfetuaConfActivity.this,
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

				exp_list_adapter = new ExpListAdapter(EfetuaConfActivity.this,
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		
	}
	
	public void exibirResultado() throws JSONException, IOException {
		JSONObject respJson = new JSONObject(resposta);

		ArrayList<String> arrayInvalidos = new ArrayList<String>();
		ArrayList<String> arrayErrados = new ArrayList<String>();
		ArrayList<String> arrayNaoEncontrados = new ArrayList<String>();

		listGroup = new ArrayList<String>();
		listData.clear();

		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		
		if (prefs.contains("lista")) {
			editor.remove("lista");
			
		}

		if (respJson.has("naoEncontrados")) {
			for (int i = 0; i < respJson.getJSONArray("naoEncontrados")
					.length(); i++) {
				arrayNaoEncontrados.add(respJson.getJSONArray("naoEncontrados")
						.getString(i));
			}

			listGroup.add("Patrimônios não encontrados");
			listData.put(listGroup.get(listGroup
					.indexOf("Patrimônios não encontrados")),
					arrayNaoEncontrados);

			editor.putString("naoEncontrados",
					ObjectSerializer.serialize(arrayNaoEncontrados));

		}

		if (respJson.has("invalidos")) {
			for (int i = 0; i < respJson.getJSONArray("invalidos").length(); i++) {
				arrayInvalidos.add(respJson.getJSONArray("invalidos")
						.getString(i));
			}

			listGroup.add("Patrimônios inválidos");
			listData.put(
					listGroup.get(listGroup.indexOf("Patrimônios inválidos")),
					arrayInvalidos);

			editor.putString("invalidos",
					ObjectSerializer.serialize(arrayInvalidos));
		}

		if (respJson.has("errados")) {
			for (int i = 0; i < respJson.getJSONArray("errados").length(); i++) {
				arrayErrados.add(respJson.getJSONArray("errados").getString(i));
			}

			listGroup.add("Patrimônios com ambiente errado");
			listData.put(listGroup.get(listGroup
					.indexOf("Patrimônios com ambiente errado")), arrayErrados);

			editor.putString("errados",
					ObjectSerializer.serialize(arrayErrados));
		}

		if (respJson.has("errados")) {
			alterarVisibilidade(2);
		} else {
			alterarVisibilidade(3);
		}

		editor.commit();

		exp_list_adapter = new ExpListAdapter(EfetuaConfActivity.this,
				listData, listGroup, true);

		exp_list.setAdapter(exp_list_adapter);
		
		exp_list.setOnChildClickListener(new OnChildClickListener() {			
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
					int childPosition, long arg4) {
					if (exp_list_adapter.getGroup(groupPosition).toString().equals("Patrimônios não encontrados")) {
						criarDialogsResultado(0);
						
					} else if(exp_list_adapter.getGroup(groupPosition).toString().equals("Patrimônios inválidos")){
						criarDialogsResultado(1);
						
					} else if(exp_list_adapter.getGroup(groupPosition).toString().equals("Patrimônios com ambiente errado")){
						criarDialogsResultado(2);
						
					}
				
				
				return false;
			}
		});

		if (exp_list_adapter.getGroupCount() < 0) {
			for (int i = 0; i < exp_list_adapter.getGroupCount(); i++) {
				exp_list.expandGroup(i, true);
			}
		}

		if (respJson.has("invalidos") || respJson.has("naoEncontrados") || respJson.has("errados")) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Inconsistências detectadas!");
			builder.setMessage("Foram encontrados um ou mais tipos de inconsistência, clique nos itens para ver mais detalhes sobre o erro de uma determinada lista.");
			builder.setNeutralButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogInconsis.dismiss();
				}
			});
			dialogInconsis = builder.create();
			dialogInconsis.show();
		}

		invalidateOptionsMenu();

	}

	@SuppressWarnings("unchecked")
	public void getPreferences() throws JSONException {

		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);

		ArrayList<String> listaTemp = null;

		if (prefs.contains("estagio")) {
			alterarVisibilidade(prefs.getInt("estagio", 1));
		}

		if (prefs.contains("ambiente")) {
			JSONObject jsonAmbiente = new JSONObject(prefs.getString(
					"ambiente", null));
			a = ConversorObject.converterAmbiente(jsonAmbiente);
		}

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

				exp_list_adapter = new ExpListAdapter(EfetuaConfActivity.this,
						listData, listGroup, false);

				exp_list.setAdapter(exp_list_adapter);

				for (int i = 0; i < listGroup.size(); i++) {
					exp_list.expandGroup(i);
				}

			}

			listaTemp = null;
		}

		if (prefs.contains("naoEncontrados")) {
			try {
				listaTemp = (ArrayList<String>) ObjectSerializer
						.deserialize(prefs.getString("naoEncontrados",
								ObjectSerializer
										.serialize(new ArrayList<String>())));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (listaTemp != null) {

				if (!listGroup.contains("Patrimônios não encontrados")) {
					listPatrimonios = listaTemp;

					listGroup.add("Patrimônios não encontrados");

					listData.put(listGroup.get(listGroup
							.indexOf("Patrimônios não encontrados")),
							listPatrimonios);

					exp_list_adapter = new ExpListAdapter(
							EfetuaConfActivity.this, listData, listGroup, true);

					exp_list.setAdapter(exp_list_adapter);

					for (int i = 0; i < listGroup.size(); i++) {
						exp_list.expandGroup(i);
					}
				}
			}

			listaTemp = null;
		}

		if (prefs.contains("invalidos")) {
			try {
				listaTemp = (ArrayList<String>) ObjectSerializer
						.deserialize(prefs.getString("invalidos",
								ObjectSerializer
										.serialize(new ArrayList<String>())));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (listaTemp != null) {

				if (!listGroup.contains("Patrimônios inválidos")) {
					listPatrimonios = listaTemp;

					listGroup.add("Patrimônios inválidos");

					listData.put(listGroup.get(listGroup
							.indexOf("Patrimônios inválidos")), listPatrimonios);

					exp_list_adapter = new ExpListAdapter(
							EfetuaConfActivity.this, listData, listGroup, true);

					exp_list.setAdapter(exp_list_adapter);

					for (int i = 0; i < listGroup.size(); i++) {
						exp_list.expandGroup(i);
					}
				}
			}

			listaTemp = null;
		}

		if (prefs.contains("errados")) {
			try {
				listaTemp = (ArrayList<String>) ObjectSerializer
						.deserialize(prefs.getString("errados",
								ObjectSerializer
										.serialize(new ArrayList<String>())));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (listaTemp != null) {
				if (!listGroup.contains("Patrimônios com ambiente errado")) {

					listPatrimonios = listaTemp;

					listGroup.add("Patrimônios com ambiente errado");

					listData.put(listGroup.get(listGroup
							.indexOf("Patrimônios com ambiente errado")),
							listPatrimonios);

					exp_list_adapter = new ExpListAdapter(
							EfetuaConfActivity.this, listData, listGroup, true);

					exp_list.setAdapter(exp_list_adapter);

					for (int i = 0; i < listGroup.size(); i++) {
						exp_list.expandGroup(i);
					}
				}

			}

			listaTemp = null;
		}
	}

	public void criarDialogAmb() {
		setouAmbiente = false;

		listaAmb = new ArrayList<Ambiente>();
		for (int i = 0; i < jsonResposta.length(); i++) {
			JSONObject json = jsonResposta.optJSONObject(i);
			try {
				a = ConversorObject.converterAmbiente(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			listaAmb.add(a);
		}

		listStr = new ArrayList<String>();

		for (int i = 0; i < listaAmb.size(); i++) {
			listStr.add(listaAmb.get(i).getNome());
		}

		CharSequence[] cs = listStr.toArray(new CharSequence[listStr.size()]);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Escolha o ambiente:");
		builder.setNegativeButton(getString(R.string.cancelar),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});
		builder.setSingleChoiceItems(cs, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch (item) {
						default:
							a = listaAmb.get(item);
							setouAmbiente = true;
							break;
						}
						ambDialog.dismiss();
					}
				});
		ambDialog = builder.create();

		ambDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (setouAmbiente == false) {
					ambDialog.show();
				}
			}
		});
		ambDialog.show();
	}

	public void criarDialogTransf(){
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

		View dialogview = inflater.inflate(R.layout.layout_dialog, null);

		input = (EditText) dialogview.findViewById(R.id.etDialog);
		input.setHint(null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Deseja incluir uma observação?");
		builder.setMessage("Caso não queira incluir uma observação para a transferência, o campo abaixo pode ser deixado em branco.");
		builder.setView(dialogview);
		builder.setPositiveButton("Enviar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enviarRequisicao();
				dialogTransf.dismiss();
			}
		});
		builder.setNegativeButton("Cancelar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogTransf.dismiss();				
			}
		});
		dialogTransf = builder.create();
		dialogTransf.show();
		
	}
	
	public void alterarVisibilidade(int estagio) {
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();

		if (estagio == 1) {
			btScan.setVisibility(View.VISIBLE);
			btSalvarConf.setVisibility(View.VISIBLE);
			etCdPat.setVisibility(View.VISIBLE);
			btSalvarEdit.setVisibility(View.VISIBLE);

			btEnviarRequisicao.setVisibility(View.GONE);
			btFinalizar.setVisibility(View.GONE);

		} else if (estagio == 2) {
			btScan.setVisibility(View.GONE);
			btSalvarConf.setVisibility(View.GONE);
			etCdPat.setVisibility(View.GONE);
			btSalvarEdit.setVisibility(View.GONE);

			btEnviarRequisicao.setVisibility(View.VISIBLE);
			btFinalizar.setVisibility(View.VISIBLE);

		} else {
			btScan.setVisibility(View.GONE);
			btSalvarConf.setVisibility(View.GONE);
			etCdPat.setVisibility(View.GONE);
			btSalvarEdit.setVisibility(View.GONE);

			btEnviarRequisicao.setVisibility(View.VISIBLE);
			btEnviarRequisicao.setTextColor(Color.DKGRAY);
			btEnviarRequisicao.setEnabled(false);
			btFinalizar.setVisibility(View.VISIBLE);
			

		}

		editor.putInt("estagio", estagio);

		editor.commit();
	}


	public void criarDialogsResultado(int posicao){
		if (posicao == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Patrimônios não encontrados");
			builder.setMessage("Patrimônios que estão registrados no ambiente da conferência, mas que não foram registrados na mesma.\n\nLocalize o(s) patrimônio(s) e"
					+ " retorne-os para o seu ambiente. \n\nCaso a mesma inconsistência se repita na próxima conferência, um e-mail de alerta será enviado a todos os gerentes do sistema.");
			builder.setNeutralButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogResultado.dismiss();
				}
			});
			dialogResultado = builder.create();
			dialogResultado.show();
			
		} else if (posicao == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Patrimônios inválidos");
			builder.setMessage("Patrimônios que não foram registrados no sistema, ou códigos que foram inseridos por engano.\n\n"
					+ "Caso o código realmente se refira a um patrimônio, certifique-se com os gerentes de que ele realmente foi cadastrado no sistema.\n"
					+ "Caso o código tenha sido inserido por engano, ele pode ser ignorado.");
			builder.setNeutralButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogResultado.dismiss();
				}
			});
			dialogResultado = builder.create();
			dialogResultado.show();
			
		} else if (posicao == 2) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Patrimônios com ambiente errado");
			String mensagem;
			if (f.getCargo()==Cargo.GER) {
				mensagem = "Patrimônios que não pertencem ao ambiente da conferência, você pode retorná-los ao ambiente correto ou transferí-los imediatamente com o botão abaixo.";
			} else {
				mensagem = "Patrimônios que não pertencem ao ambiente da conferência, você pode retorná-los ao ambiente correto ou fazer uma requisição de transferência com o botão abaixo.";
			}
			builder.setMessage(mensagem);
			builder.setNeutralButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogResultado.dismiss();
				}
			});
			dialogResultado = builder.create();
			dialogResultado.show();
			
		}
		
	}
	
}
