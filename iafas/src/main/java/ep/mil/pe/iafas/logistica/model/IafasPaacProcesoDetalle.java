package ep.mil.pe.iafas.logistica.model;

import lombok.Data;

@Data
public class IafasPaacProcesoDetalle {

	
	private String periodo;
	private int npacProcedimientoCodigo;
	private int nitemCodigo;
	private double nprocesoDetPrecio;
	private double nprocesoDetCantidad;
	private String cunidadMedidaCodigo;
	private String vpacProcesoObs;
	private int npacProcesoDetalleItem;
	private String vusuarioCodigo;
	private String tipo;
	// 
	private int nprocedimientoEtapaDocCodigo;
	private String rucProveedor;
	private String rucRNP;

	

	public IafasPaacProcesoDetalle() {
		// TODO Auto-generated constructor stub
	}
	
}
