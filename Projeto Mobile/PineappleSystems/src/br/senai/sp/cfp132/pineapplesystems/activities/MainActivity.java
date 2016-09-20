package br.senai.sp.cfp132.pineapplesystems.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import br.senai.sp.cfp132.pineapplesystems.R;
import br.senai.sp.cfp132.pineapplesystems.model.Cargo;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.util.ConversorObject;

public class MainActivity extends Activity implements OnItemClickListener {
	public static final String PREFS_FUNC = "prefsFunc";

	ImageButton imageButton1;
	ImageButton imageButton2;
	Button btSair;
	Funcionario f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		SharedPreferences prefs = getSharedPreferences(PREFS_FUNC, 0);
		if (prefs.contains("funcionario")) {
			JSONObject jsonFunc;
			try {
				jsonFunc = new JSONObject(prefs.getString("funcionario", ""));
				f = ConversorObject.converterFuncionario(jsonFunc);
				if (f.getCargo() == Cargo.RESP) {
					Intent i = new Intent(getApplicationContext(),
							ConferenciaActivity.class);
					startActivity(i);
					finish();
				} else if (f.getCargo() == Cargo.AUDIT) {
					Intent i = new Intent(getApplicationContext(),
							AuditoriaActivity.class);
					startActivity(i);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (DataHolder.getInstance().getFunc() != null) {
			f = DataHolder.getInstance().getFunc();
			if (f.getCargo() == Cargo.RESP) {
				Intent i = new Intent(getApplicationContext(),
						ConferenciaActivity.class);
				startActivity(i);
				finish();
			} else if (f.getCargo() == Cargo.AUDIT) {
				Intent i = new Intent(getApplicationContext(),
						AuditoriaActivity.class);
				startActivity(i);
				finish();
			}
		} else {
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
		}
		
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		btSair = (Button) findViewById(R.id.btSair);
		
		addListenerOnButtons();

	}
	
	public void addListenerOnButtons() {
		imageButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						ConferenciaActivity.class);
				startActivity(i);
			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						ListaMovimentacoesActivity.class);
				startActivity(i);
			}
		});
	}
	
	public void sair(View v){
		getApplicationContext().getSharedPreferences(PREFS_FUNC, 0).edit()
		.clear().commit();		
		DataHolder.getInstance().setFunc(null);
		
		startActivity(new Intent(getApplicationContext(), LoginActivity.class));

		finish();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
