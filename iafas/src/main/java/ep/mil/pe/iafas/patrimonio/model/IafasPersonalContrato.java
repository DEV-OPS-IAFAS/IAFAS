package ep.mil.pe.iafas.patrimonio.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class IafasPersonalContrato {

	private String cPeriodoCodigo;
	private int nPersonaCodigo;
	private int nPersonalContratoCodigo;
	private Date dPersonalContratoInicio;
	private Date dPersonalContratoFin;
	private BigDecimal dPersonalContratoMonto;
	private String vPersonalContratoArchivo;
	private String cEstadoCodigo;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date dUsuarioFecha;
	private String  desEstado;
	private String nombrePersona;
	private String numDocPersona;
}
