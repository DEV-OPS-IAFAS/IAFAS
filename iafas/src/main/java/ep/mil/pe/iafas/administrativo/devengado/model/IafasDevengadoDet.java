package ep.mil.pe.iafas.administrativo.devengado.model;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class IafasDevengadoDet {
	private String vano;
	private String vcodSecFunc;
	private String vidClasificador;
	private String cadena;
	private String nomClasificador;
	private BigDecimal nimpMonSol;
	private String vusuarioIng;
	private String vregSiaf;
	private BigDecimal montoIngresado;

	public IafasDevengadoDet() {
		// TODO Auto-generated constructor stub
	}

}
