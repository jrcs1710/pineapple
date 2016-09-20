package br.senai.sp.cfp132.pineapplesystems.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.model.Movimentacao;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;
import br.senai.sp.cfp132.pineapplesystems.util.WebService;

@SuppressLint("SimpleDateFormat")
public class ListaMovimentacoesActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	public static final String PREFS_NAME = "prefsWS";
	public static final String PREFS_FUNC = "prefsFunc";
	ListView listOpcoes;

	List<String> listaStr;
	List<Movimentacao> lista;
	Funcionario f;
	AlertDialog dialogSaida;

	String dataSoli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_movimentacoes);

		listOpcoes = (ListView) findViewById(R.id.listOpcoes);

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

		dialogSaida = criarDialogSaida();
		dialogSaida.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				finish();
			}
		});

		ArrayAdapter<String> adapterWS = new ArrayAdapter<String>(this,
				R.layout.list_texto, R.id.list_content, getMovimentacoesWS());

		listOpcoes.setAdapter(adapterWS);
		listOpcoes.setOnItemClickListener(this);

	}

	public List<String> getMovimentacoesWS() {
		String url = WebService.getInstance().getURL(
				getApplicationContext()) + "movimentacao/buscarAbertas";
		String resposta = WebService.makeRequest(url);
		System.out.println(resposta);
		String strMovi;
		lista = new ArrayList<Movimentacao>();
		listaStr = new ArrayList<String>();
		try {
			if (!resposta.isEmpty()) {
			JSONArray array = new JSONArray(resposta);
				for (int i = 0; i < array.length(); i++) {
					JSONObject job = array.getJSONObject(i);
					Movimentacao m = ConversorObject.converterMovimentacao(job);

					Calendar dtSolicitacao = Calendar.getInstance();
					dtSolicitacao.setTimeInMillis(job.getLong("dtSolicitacao"));
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					dataSoli = sdf.format(dtSolicitacao.getTime());
					strMovi = dataSoli + "  |  Amb. atual: "
							+ m.getAtual().getNome() + "  |  Destino: "
							+ m.getDestino().getNome()
							+ "  |  Qnt. de patrimônios: "
							+ m.getPatrimonios().size();
					
					System.out.println(m.getPatrimonios().get(0).getPatrimonio().getCdPatrimonio());

					listaStr.add(strMovi);
					lista.add(m);
				}

			} else {
				dialogSaida.show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return listaStr;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		default:
			Movimentacao movi = new Movimentacao();
			movi = lista.get(position);

			Intent i = new Intent(getApplicationContext(),
					GerenMovimentacoesActivity.class);
			i.putExtra("movimentacao", (Parcelable) movi);
			startActivity(i);
		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();

		ArrayAdapter<String> adapterWS = new ArrayAdapter<String>(this,
				R.layout.list_texto, R.id.list_content, getMovimentacoesWS());

		listOpcoes.setAdapter(adapterWS);
	}

	@Override
	public void onClick(DialogInterface dialog, int item) {
		switch (item) {
		case DialogInterface.BUTTON_NEUTRAL:
			finish();
			break;
		default:
			break;
		}

	}

	private AlertDialog criarDialogSaida() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Não há nenhuma requisição aberta");
		builder.setNeutralButton("OK", this);
		return builder.create();

	}
}
