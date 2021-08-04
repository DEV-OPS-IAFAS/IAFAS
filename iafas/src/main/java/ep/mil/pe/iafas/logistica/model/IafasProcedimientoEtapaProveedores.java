package ep.mil.pe.iafas.logistica.model;

import lombok.Data;

@Data
public class IafasProcedimientoEtapaProveedores {

	private String cperiooCodigo;
	private int npacProcedimientoCodigo;
	private int npacProcedimientosEtapaDoc;
	private String vproveedorRuc;
	private String vproveedorRNPCodigo;
	private String cestadoCodigo;
	private String vusuarioCodigo;
	// Detalle
	private int itemCodigo;
	private int proveedorItem;
	private double precioItem;
	private double cantidadItem;
	private String cunidadMedida;
	private String observacionItem;
	
	// auxiliares
	private String razonSocial;

	public IafasProcedimientoEtapaProveedores() {
		// TODO Auto-generated constructor stub
	}
	
	
	

}
