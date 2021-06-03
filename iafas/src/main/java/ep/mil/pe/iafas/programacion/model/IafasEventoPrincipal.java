package ep.mil.pe.iafas.programacion.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasEventoPrincipal {

	private String cperiodoCodigo;
	private int nfuenteFinanciamientoCodigo;
	private int ntareaPresupuestalCodigo;
	private String canioRegistro;
	private String veventoPrincipalCodigo;
	private String veventoPrinciombre;
	private String veventoPrincipalComentario;
	private int neventoPrincipalNivel;
	private int neventoPrincipalNiveles;
	private String ceventoPrincipalFinal;
	private String cestadoCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private Date dusuarioFecha;
	private BigDecimal monto;

}
