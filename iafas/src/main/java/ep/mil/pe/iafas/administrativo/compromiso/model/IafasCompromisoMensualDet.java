package ep.mil.pe.iafas.administrativo.compromiso.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class IafasCompromisoMensualDet {

	private String vano;
	private String vcodSecFunc;
	private String vidClasificador;
	private String cadena;
	private String nomClasificador;
	private BigDecimal nimpMonSol;
	private String vusuarioIng;
	private String vregSiaf;
	private BigDecimal montoIngresado;

	public IafasCompromisoMensualDet() {
		// TODO Auto-generated constructor stub
	}

}
