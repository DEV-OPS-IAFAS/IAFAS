package ep.mil.pe.iafas.logistica.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IafasAlmacenSalida {

	private String cPeriodoCodigo;
	private int nAlmacenSalidaCodigo;
	private int nAlmacenCodigo;
	private String cMesCodigo;
	private String vAlmacenSalidaMotivo;
	private String cAreaLaboralCodigo;
	private Date dAlmacenSalidaSolicitado;
	private String vUsuarioRecibe;
	private String cEstadoCodigo;
	private Date dAlmacenSalidaAtencion;
	private String vUsuarioAtiende;
	private Date dUsuarioAtiende;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date Dusuariofecha;	
	private String mode;
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private String numeroAlmacen;
	private String nombreAlmacen;
	private String nombreAreaLaboral;
	private String desEstado;
}
