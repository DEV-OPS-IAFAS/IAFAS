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
	private int nfuenteFinanciamiento;
	private int npacProcesoCodigo;
	private String vpacProcesosNumeroPaac;
	private int nprocesoEtapaCodigo;
	private int nprocesoDocumentoCodigo;
	private String ctipoProcedimientoCodigo;
	private int ntipoProcesoContratacion;
	private String vpacProcesoNumero;
	private String vpacProcesoDescripcion;
	private String cpacProcesoCompras;
	private int npacProcesoCertificado;
	private Date dpacProcesoConvocatoria;
	private Date dpacProcesoParticipantes;
	private Date dpacProcesoObservaciones;
	private Date dpacProcesoAbsolucion;
	private Date dpacProcesoIntegracion;
	private Date dpacProcesoOfertas;
	private Date dpacProcesoEvaluacion;
	private Date dpacProcesoBuenaPro;
	private Date dpacProcesoConsentimiento;
	private Date dprocesoContrato;
	private BigDecimal npacProcesoMonto;
	private String cestadoCodigo;
	private String vusuarioCodigo;
	private String tipo;

}
