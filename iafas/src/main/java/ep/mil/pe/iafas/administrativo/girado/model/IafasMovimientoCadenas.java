package ep.mil.pe.iafas.administrativo.girado.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasMovimientoCadenas {

	private String vano;
	private String vsecuencia;
	private String vsecuenciaInt;
	private String vcodSecFun;
	private String vcodCla;
	private BigDecimal impSolCad;
	private BigDecimal impSolCadMonExt;
	private String vusuarioingreso;
	private Date dfechaIng;
	private String vregSiaf;
	private String desCadena;
	private String cadena;
	private String descTarea;
}
