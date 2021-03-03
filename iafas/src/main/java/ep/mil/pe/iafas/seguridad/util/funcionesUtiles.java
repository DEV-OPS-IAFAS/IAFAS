package ep.mil.pe.iafas.seguridad.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class funcionesUtiles {

	// private static final Logger logger =
	// LogManager.getLogger(funcionesUtiles.class.getName());
	private static final Logger logger = Logger.getLogger(funcionesUtiles.class.getName());

	public funcionesUtiles() {

	}

	public String EncriptCadena(String c, String t, boolean a) {
		logger.debug("[INICIO:] Metodo : EncriptarCadena.......");
		String retorno = "";
		int i = 0, j = 0, ic = c.length(), it = t.length();
		int temp;
		int[] claveascii = new int[ic];
		int[] textoascii = new int[it];
		for (i = 0; i < ic; i++)
			claveascii[i] = c.charAt(i);
		for (i = 0; i < it; i++)
			textoascii[i] = t.charAt(i);

		if (a) {
			for (i = 0; i < it; i++) {
				j++;
				if (j >= ic)
					j = 0;
				temp = textoascii[i] + claveascii[j];
				if (temp > 255)
					temp = temp - 255;
				retorno = retorno + (char) temp;
			}
		} else {
			for (i = 0; i < it; i++) {
				j++;
				if (j >= ic)
					j = 0;
				temp = textoascii[i] - claveascii[j];
				if (temp < 0)
					temp = temp + 256;
				retorno = retorno + (char) temp;
			}
		}
		return retorno;
	}

	public static DateFormat getLocalFormat() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		dateFormat.setTimeZone(TimeZone.getDefault());
		return dateFormat;
	}

	public static String printPrettyJSONString(Object o) throws JsonProcessingException {
		return new ObjectMapper().setDateFormat(funcionesUtiles.getLocalFormat())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writerWithDefaultPrettyPrinter()
				.writeValueAsString(o);
	}

}
