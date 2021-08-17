package ep.mil.pe.iafas.logistica.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data

public class IafasPacOrden {
	

	
	
	private String cperiodoCodigo  ;
	private Integer nfuenteFinanciamiento;
    private String citemTipoCodigo;
    private Integer nordenCodigo;
    private String cperiodoContrato;
    private Integer ncontratoCodigo;
    private Integer ntareaPresupuestalCodigo;
    private Integer nclasificadorPresupuestalCodigo;
    private Date dordenFecha;
    private Integer nplazoEntregaCodigo;
    private String vordenDescripcion;
    private String cproveedorRuc;
    private Integer nmonedaCodigo;
    private BigDecimal nordenTipoCambio;
    private Integer ncondicionEntregaCodigo;
    private Integer ncondicionPagoCodigo;
    private Integer npacOrdenPlazo;
    private Integer ncertificadoPresupuestalCodigo;
    private String cestadoCodigo;
    private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private Date Dusuariofecha;	
	
	private String descripcionFF;
	private String razonSocial;
	private String moneda;
	private String descripcionItem;
	private String descripcionContrato;
	private String vcodtipoFinancieamiento;
	private BigDecimal nordenDetalleMonto;
	
	// detalle de la orden
	 private Integer nitemCodigo;
	 private BigDecimal nordenDetalleCantidad;
	 private BigDecimal nordenDetalleImporte;
	 private BigDecimal nordenDetalleExtranjera;
     private String vordenDetalleObservacion;
     private String descripcionTarea;
     private String clasificador;
     private String descripcionClasificador;

 	// unidad medida
     private String cunidadMedidaCodigo;
     private String descripcionUnidadMedida;
     // CANTIDADES
     
     private Integer cantidadContrato;
     private Integer cantidadOrden;
     
     private String busqueda;
     private String descripcionBien;
     private Integer ctipoOrden;
     
     public IafasPacOrden() {}


     //CONSTRUCTOR PARA EL DETALLE AGREGAR
	public IafasPacOrden(String cperiodoCodigo, String citemTipoCodigo, Integer nordenCodigo, String cperiodoContrato,
			Integer ncontratoCodigo, String vusuarioCreador, String vusuarioCodigo, String descripcionItem,
			Integer nitemCodigo, BigDecimal nordenDetalleCantidad, BigDecimal nordenDetalleImporte,
			BigDecimal nordenDetalleExtranjera, String vordenDetalleObservacion) {
		super();
		this.cperiodoCodigo = cperiodoCodigo;
		this.citemTipoCodigo = citemTipoCodigo;
		this.nordenCodigo = nordenCodigo;
		this.cperiodoContrato = cperiodoContrato;
		this.ncontratoCodigo = ncontratoCodigo;
		this.vusuarioCreador = vusuarioCreador;
		this.vusuarioCodigo = vusuarioCodigo;
		this.descripcionItem = descripcionItem;
		this.nitemCodigo = nitemCodigo;
		this.nordenDetalleCantidad = nordenDetalleCantidad;
		this.nordenDetalleImporte = nordenDetalleImporte;
		this.nordenDetalleExtranjera = nordenDetalleExtranjera;
		this.vordenDetalleObservacion = vordenDetalleObservacion;
	}
     
   



}
