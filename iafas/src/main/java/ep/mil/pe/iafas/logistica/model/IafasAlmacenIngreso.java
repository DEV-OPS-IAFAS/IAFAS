package ep.mil.pe.iafas.logistica.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasAlmacenIngreso {
	
	private String periodo;
	private int nalmacenIngresoCodigo;
	private int nalmacenCodigo;
	private String cmesCodigo;
	private String cperiodoOrden;
	private int cordenCodigo;
	private Date dalmacenIngresosFecha;
	private String valmacenIngresosGuia;
	private String usuario;
	private String cestadoCodigo;
	private String descAlmacen;
	private String tipo;

	public IafasAlmacenIngreso() {
		// TODO Auto-generated constructor stub
	}


}
