package ep.mil.pe.iafas.logistica.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasProcedimientoItemGrupo {
	
	private String cperiodoCodigo;
	private String ctipoProcedimientoCodigo;
	private String citemTipoCodigo;
	private String cestadocodigo;
	private String vusuarioCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private Date dusuarioFecha;
	private BigDecimal ntipoProcedimientoInicial;
	private BigDecimal ntipoProcedimientoMaximo;
	
	private String descriProceso;
	private String descripItem;
	



}
