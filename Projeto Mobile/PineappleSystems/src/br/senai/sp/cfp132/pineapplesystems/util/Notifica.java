package br.senai.sp.cfp132.pineapplesystems.util;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class Notifica {
	
	public static void notificar(String titulo, String texto, Context context){
		Notification.Builder notifica = new Notification.Builder(context);
		notifica.setContentTitle(titulo);
		notifica.setContentText(texto);
		notifica.setSmallIcon(android.R.drawable.ic_menu_save);
		notifica.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_menu_save));
		// 100 ms de espera, 300 de vibracao, 100 ms de espera, 300 de vibracao
		long[] vibrate = {100,300,100,300};
		NotificationManager manager = (NotificationManager) context.getSystemService(Application.NOTIFICATION_SERVICE);
		manager.notify(0, notifica.build());
		try{
			Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone toque = RingtoneManager.getRingtone(context, som);
			toque.play();
		}catch (Exception e){
			Log.w("ERRO", e.getMessage());
		}
	}
}
