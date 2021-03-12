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
	
	/*Metodo agregado por Elvis Severino*/
	private String vnroCompag;
	private String descRetencion;
	
	public IafasComprobanteRetencion() {
		// TODO Auto-generated constructor stub
	}


}
