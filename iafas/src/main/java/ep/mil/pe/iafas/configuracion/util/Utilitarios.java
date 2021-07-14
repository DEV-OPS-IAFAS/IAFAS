package ep.mil.pe.iafas.configuracion.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class Utilitarios {
	
	public Date verFechaFin(Date fechaInicio, int cantidad) {
		 Date fecha=null;
		 SimpleDateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar c = Calendar.getInstance();
		//sumando los dias
		c.setTime(fechaInicio);
		c.add(Calendar.DATE, (cantidad-1));
		fecha = c.getTime();
		System.out.println("Utilitario: "+fechaInicio +" "+cantidad+" "+fecha);
		 return fecha;
	}
	
	public static void changeFileName(String origin, String destino) {
		
		try {
			FileUtils.moveFile(FileUtils.getFile(origin), FileUtils.getFile(destino));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
