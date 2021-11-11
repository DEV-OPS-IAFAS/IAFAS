package ep.mil.pe.iafas.patrimonio.model;

import java.util.Date;
import lombok.Data;


@Data
public class IafasPatrimonioActa {

	private String cPeriodoCodigo;
	private int nPatrimonioTipoActaCodigo;
	private int nPatrimonioActaCodigo;
	private int nPersonaCodigo;
	private int nPersonalContratoCodigo;
	private String dPatrimonioActaFecha;
	private int nPersonaPresidente;
	private int nPersonaVocal;
	private int nPersonaSecretario;
	private String vPatrimonioActaObservacion;
	private String cEstadoCodigo;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date dUsuarioFecha;
	private String  desEstado;
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private String desTipoActa;
	private String mode;
	private String nombrePresidente;
	private String nombreSecretario;
	private String nombreVocal;
}
