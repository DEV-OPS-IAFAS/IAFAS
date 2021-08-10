package ep.mil.pe.iafas.logistica.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasPacContratos {

	public IafasPacContratos() {
		// TODO Auto-generated constructor stub
	}

	private int ncontratoCodigo;
	private String cprocesoPeriodo;
	private int nfuenteFinanciamiento;
	private int npacProcesosCodigo;
	private String cperiodoCodigo;
	private String vnumeroContrato;
	private String vcontratoDescripcion;
	private Date dcontratoInicio;
	private Date dcontratoFin;
	private BigDecimal ncontratoMonto;
	private int nmonedaCodigo;
	private String simboloMoneda;
	private BigDecimal ncontratoTipoCambio;
	private BigDecimal ncontratoExtranjera;
	private String vcontratoArchivo;
	private String vproveedorRuc;
	private String vusuarioCodigo;
	
	private String descFuenteFinan;
	private String razonSocial;
	 // pacProcesos
	private String vpacNumeroPaac;
	private String vpacProcesosDescripcion;
	private BigDecimal montoProceso;
	
	// Procedimientos
	private String vnomenclaturaProceso;
	private String vpacNumeroConvocatoria;
	
	// Detalle DE Contrato
	private int codItem;
	private BigDecimal cantidad;
	private BigDecimal montoMNDetalle;
	private BigDecimal montoMEDetalle;
	private String unidadMedida;
   private String nomItem;
	
	private String tipo;
	
}