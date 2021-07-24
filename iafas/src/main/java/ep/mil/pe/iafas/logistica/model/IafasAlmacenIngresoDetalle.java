package ep.mil.pe.iafas.logistica.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasAlmacenIngresoDetalle {

	private String periodo;
	private int nalmacenIngresosCodigo;
	private int nalmacenCodigo;
	private int nitemCodigo;
	private double nalmacenIngresoDetalleCantidad;
	private double nalmacenIngresoDetallePrecio;
	private String valmacenIngresoDetalleLote;
	private Date dalmacenIngresoDetalleVencimiento;
	private String vusuarioCodigo;
	private String tipo;
	
	// vista
	private String periodoOrden;
	private int nroOrden;
	private Date fechaOrden;
	private String descOrden;
	private String ruc;
	private String razonSocial;
	private int codigoItem;
	private String nombreItem;
	private double cantidad;
	private double precio;
	
	public IafasAlmacenIngresoDetalle() {
		// TODO Auto-generated constructor stub
	}

}
