package ep.mil.pe.iafas.programacion.model;

import ep.mil.pe.iafas.configuracion.util.Constantes;
import lombok.Data;

@Data
public class ProgramacionMultiAnualDetalle {

	private String periodo;
	private int fuenteFinac;
	private int tareaPtalCodigo; 
	private int  canio;
	private int codCla;
	private String cadena;
	private String desCadena;
	private int importe;
	private String usuarioCodigo;
	private String tipo;
	private int montoDet1;
	private int montoDet2;
	private int montoDet3;
	private int sumaTotal;
	private int ano1;
	private int ano2;
	private int ano3;
	private int codigoTareaPtal;
	private int importeA;
	private int importeB;
	private int importeC;
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private int montoDetalle1;
	private int montoDetalle2;
	private int montoDetalle3;
}
