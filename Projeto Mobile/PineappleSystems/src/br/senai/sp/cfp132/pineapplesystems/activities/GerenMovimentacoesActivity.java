package br.senai.sp.cfp132.pineapplesystems.activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.model.Movimentacao;
import br.senai.sp.cfp132.pineapplesystems.model.StatusRequisicao;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorJson;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

@SuppressLint("SimpleDateFormat")
public class GerenMovimentacoesActivity extends Activity {
	public static final String PREFS_FUNC = "prefsFunc";
	HashMap<String, List<String>> listData;
	List<String> listGroup;
	ExpandableListView exp_list;
	ExpListAdapter adapter;
	Movimentacao movimentacao;
	Funcionario f;

	String dataSoli;
	String solicitante;
	String ambAtual;
	String ambDestino;

	TextView tvData;
	TextView tvSolicitante;
	TextView tvAmbientes;

	EditText etObsAvaliador;
	EditText etObsSolicitante;
	Button btAprovar;
	Button btRejeitar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_gerenciar);
		movimentacao = getInfo();

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

		tvData = (TextView) findViewById(R.id.tvData);
		tvData.setText("Data da solicitação: " + dataSoli);

		tvSolicitante = (TextView) findViewById(R.id.tvSolicitante);
		tvSolicitante.setText("Solicitante: "
				+ movimentacao.getSolicitante().getNome());

		tvAmbientes = (TextView) findViewById(R.id.tvAmbientes);
		tvAmbientes.setText("Amb. atual: " + movimentacao.getAtual().getNome()
				+ "  |  Destino: " + movimentacao.getDestino().getNome());

		etObsAvaliador = (EditText) findViewById(R.id.etObsAvaliador);
		etObsSolicitante = (EditText) findViewById(R.id.etObsSolicitante);
		if (!movimentacao.getObsSolicitante().equals("null")) {
			etObsSolicitante.setText(movimentacao.getObsSolicitante());
		}
		btAprovar = (Button) findViewById(R.id.btAprovar);
		btRejeitar = (Button) findViewById(R.id.btRejeitar);

		exp_list = (ExpandableListView) findViewById(R.id.exp_list);

		exp_list.setAdapter(new ExpListAdapter(GerenMovimentacoesActivity.this,
				listData, listGroup, false));
		exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				setListViewHeight(exp_list, groupPosition);
				return false;
			}
		});
	}

	public void aprovar(View v) {
		movimentacao.setAvaliador(f);
		if (!etObsAvaliador.getEditableText().toString().isEmpty()) {
			movimentacao.setObsAprovador(etObsAvaliador.getEditableText().toString());
			
		}

		try {
			salvarMovimentacao(movimentacao);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finish();

	}

	public void rejeitar(View v) {
		movimentacao.setAvaliador(f);
		movimentacao.setStatus(StatusRequisicao.RECUS);
		if (!etObsAvaliador.getEditableText().toString().isEmpty()) {
			movimentacao.setObsAprovador(etObsAvaliador.getEditableText().toString());
			
		}

		try {
			salvarMovimentacao(movimentacao);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finish();
	}

//	@Override
//	protected void onRestart() {
//		super.onRestart();
//
//		movimentacao = getInfo();
//	}


	private void salvarMovimentacao(Movimentacao m) throws JSONException {
		m.setDataAprovacao(null);
		JSONObject job = ConversorJson.converterMovimentacao(m);

		try {
			StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			HttpPost post = new HttpPost(WebService.getInstance().getURL(
					getApplicationContext())
					+ "movimentacao/salvar");
			post.setEntity(se);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);

			String resposta = EntityUtils.toString(response.getEntity());
			Toast.makeText(getApplicationContext(), resposta, Toast.LENGTH_LONG)
					.show();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Movimentacao getInfo() {
		listData = new HashMap<String, List<String>>();
		List<String> listPatrimonios = new ArrayList<String>();
		listGroup = new ArrayList<String>();

		movimentacao = (Movimentacao) getIntent().getParcelableExtra(
				"movimentacao");

		Calendar dtSolicitacao = Calendar.getInstance();
		dtSolicitacao.setTimeInMillis(movimentacao.getDtSolicitacao()
				.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataSoli = sdf.format(dtSolicitacao.getTime());

		for (int i = 0; i < movimentacao.getPatrimonios().size(); i++) {
			listPatrimonios.add(movimentacao.getPatrimonios().get(i)
					.getPatrimonio().getCdPatrimonio());
			System.out.println(movimentacao.getPatrimonios().get(i)
					.getPatrimonio().getCdPatrimonio());
		}

		listGroup.add("Patrimônios");
		listData.put(listGroup.get(0), listPatrimonios);

		return movimentacao;
	}

	private void setListViewHeight(ExpandableListView listView, int group) {
		ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView
				.getExpandableListAdapter();
		int totalHeight = 0;
		int desiredWidth = View.MeasureSpec.makeMeasureSpec(
				listView.getWidth(), View.MeasureSpec.EXACTLY);
		for (int i = 0; i < listAdapter.getGroupCount(); i++) {
			View groupItem = listAdapter.getGroupView(i, false, null, listView);
			groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

			totalHeight += groupItem.getMeasuredHeight();

			if (((listView.isGroupExpanded(i)) && (i != group))
					|| ((!listView.isGroupExpanded(i)) && (i == group))) {
				for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
					View listItem = listAdapter.getChildView(i, j, false, null,
							listView);
					listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

					totalHeight += listItem.getMeasuredHeight();

				}
			}
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		int height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
		if (height < 10)
			height = 200;
		params.height = height;
		listView.setLayoutParams(params);
		listView.requestLayout();

	}

}
