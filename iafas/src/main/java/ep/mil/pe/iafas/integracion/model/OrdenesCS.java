package ep.mil.pe.iafas.integracion.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrdenesCS {
	private int periodo;
	private String tipoDocumento;
	private String numeroOrden;
	private String ruc;
	private String proveedor;
	private String descripcion;
	private String observacion;
	private BigDecimal montoTotal;
	private Date fechaOrden;
	private String estado;

	public OrdenesCS() {
		// TODO Auto-generated constructor stub
	}

}
