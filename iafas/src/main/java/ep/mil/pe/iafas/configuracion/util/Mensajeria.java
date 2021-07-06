package ep.mil.pe.iafas.configuracion.util;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

public class Mensajeria implements Serializable {
	
	private static final long serialVersionUID = -4921923594811228699L;
	private static final Logger logger = Logger.getLogger(Mensajeria.class.getName());
	
	public static String showMessages(int opcion, String mensaje, int typeMessages, 
			String messages, String metodoJS) {

		logger.info("[INICIO:] Metodo : showMessages:::");
		
		switch (opcion) {
		
		case Constantes.CERO_INT:
			typeMessages = Constantes.CERO_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript(metodoJS);
			break;
			
		case Constantes.UNO_INT:
			typeMessages = Constantes.UNO_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript(metodoJS);
			break;
			
		case Constantes.DOS_INT:
			typeMessages = Constantes.DOS_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript(metodoJS);
			break;
			
		case Constantes.TRES_INT:
			typeMessages = Constantes.TRES_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript(metodoJS);
			break;
			
		default:
			typeMessages = Constantes.TRES_INT;
			messages = mensaje;
			break;
		}
		
		logger.info("[FIN:] Metodo : showMessages:::");
		return messages;
	}
	
}
