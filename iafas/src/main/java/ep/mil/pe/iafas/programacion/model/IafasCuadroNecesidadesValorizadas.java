package ep.mil.pe.iafas.programacion.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasCuadroNecesidadesValorizadas {

	private String cperiodoCodigo;
	private int nfuenteFinanciamientoCodigo;
	private int ntareaPresupuestalCodigo;
	private String canioRegistro;
	private String veventoPrincipalCodigo;
	private int neventoFinalCodigo;
	private int nitemCodigo;
	private int nclasificadorPresupuestalCodigo;
	private String cunidadMedidaCodigo;
	private BigDecimal ncnvCantidad;
	private BigDecimal ncnvPrecio;
	private String cestadoCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private Date dusuarioFecha;
	private String descripcionTarea;
	private String descripcionUnidadMedida;
	private BigDecimal total;
	private BigDecimal totalProgramado;
	private BigDecimal totalMetaFisica;
}
