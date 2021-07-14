package ep.mil.pe.iafas.logistica.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasPaacProcesos {

	public IafasPaacProcesos() {
		// TODO Auto-generated constructor stub
	}
	
	private String periodo;
	private int npacProcedimientoCodigo;
	private String vpacProcedimientoNomenclatura;
	private int nprocedimientoConvocatoria;
	private int nprocesoDocumentoCodigo;
	private String ctipoProcedimientoCodigo;
	private String ctipoCodigoItem;
	private int nisistemaContratacion;
	private int nmodalidadContratacion;
	private String vpacProcedimientoDescripcion;
	private int nmonedaCodigo;
	private double ntipoCambio;
	private BigDecimal npacProcesoMonto;
	private BigDecimal npacProcedimientoExtranjera;
	private Date dfechaProcedimiento;
	private String nombreArchivo;
	private String cestadoCodigo;
	private String vusuarioCodigo;
	private String tipo;
	
	private String desMoneda;
	
	// Etapas
	private int nprocedimientoEtapaCodigo;
	private Date dpacProcedimientoInicio;
	private Date dpacProcedimientoFin;
	private String estadoEtpa;
	private String descripcionEtapa;
	
	private int nprocedimientoEtapaDocCodigo;
	private String descArchEtapa;
	private String archivoEtapa;
	private String estadoDocEtapa;


}
