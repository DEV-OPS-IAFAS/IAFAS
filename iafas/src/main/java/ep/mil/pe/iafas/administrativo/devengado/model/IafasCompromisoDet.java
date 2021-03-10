package ep.mil.pe.iafas.administrativo.devengado.model;

import java.math.BigDecimal;


import lombok.Data;
@Data
public class IafasCompromisoDet {
	
	private String vano;
	private String vexpediente;
	private String secFun;
	private String idClasificador;
	private BigDecimal impSol;
	private String secuencia;
	private String secInt;
	private String tipMon;
	private BigDecimal tipoCam;
	private String cadena;
	private String nomCla;
	
	private BigDecimal montoIngresado = new BigDecimal(0);

	public IafasCompromisoDet() {
		// TODO Auto-generated constructor stub
	
		
	}

}
