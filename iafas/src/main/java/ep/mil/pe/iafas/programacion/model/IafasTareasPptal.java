package ep.mil.pe.iafas.programacion.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasTareasPptal {

	private int nTareaPresupuestal;
	private String vTareaPresupuestalNombre;
	private String vTareaPresupuestalAbreviatura;
	private String cUnidadMedidaCodigo;
	private String cEstadoCodigo;
	private String cAreaTipo;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date dUsuarioFecha;
	
	
}
