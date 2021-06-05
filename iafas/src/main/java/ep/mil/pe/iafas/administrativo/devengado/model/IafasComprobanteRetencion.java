package ep.mil.pe.iafas.administrativo.devengado.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class IafasComprobanteRetencion {

	private String vano;
	private String vtipDocumentoCom;
	private String vnroCom;
	private String vserieCom;
	private String vexpediente;
	private String vsecuencia;
	private String vcodImp;
	private BigDecimal nporImp;
	private BigDecimal importeRetencion;
	private String vusuarioIng;
	private String ruc;
	private String descImpu;
	
	/*Metodo agregado por Elvis Severino*/
	private String vnroCompag;
	private String descRetencion;
	private String idClasificador;
	private String cadena;
	private String desCadena;
	
	public IafasComprobanteRetencion() {
		// TODO Auto-generated constructor stub
	}


}
