package ep.mil.pe.iafas.programacion.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasEventoFinal {

	private String cperiodoCodigo;
	private int nfuenteFinanciamientoCodigo;
	private int ntareaPresupuestalCodigo;
	private String canioRegistro;
	private String veventoPrincipalCodigo;
	private int neventoFinalCodigo;
	private String veventoFinalNombre;
	private int neventoFinalPrioridad;
	private int neventoFinalMetaFisica;
	private String cestadoCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private Date dusuarioFecha;
	private String descEstado;
	private String mode;
	private String codigoRespuesta;
	private String mensajeRespuesta;
}
