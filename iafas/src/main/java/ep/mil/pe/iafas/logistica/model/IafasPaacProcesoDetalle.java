package ep.mil.pe.iafas.logistica.model;

import lombok.Data;

@Data
public class IafasPaacProcesoDetalle {

	
	private String periodo;
	private int nfuenteFinanciamiento;
	private int npacProcesoCodigo;
	private int nitemCodigo;
	private double nprocesoDetPrecio;
	private double nprocesoDetCantidad;
	private String cunidadMedidaCodigo;
	private String vpacProcesoObs;
	private int npacProcesoDetalleItem;
	private String vusuarioCodigo;
	

	public IafasPaacProcesoDetalle() {
		// TODO Auto-generated constructor stub
	}
	
}
