package ep.mil.pe.iafas.patrimonio.model;

import java.util.Date;
import lombok.Data;

@Data
public class IafasPatrimonioActaDetalle {

	private String cPeriodoCodigo;
	private int nPatrimonioTipoActaCodigo;
	private int nPatrimonioActaCodigo;
	private int nPersonaCodigo;
	private int nPersonalContratoCodigo;
	private int nPatrimonioCodigo;
	private String vinternamientoDetalleUbicacion;
	private String vinternamientoDetalleObservacion;
	private String cEstadoCodigo;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date dUsuarioFecha;
	private String  desEstado;
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private String descripcionPatrimonio;
	private String mode;
}
