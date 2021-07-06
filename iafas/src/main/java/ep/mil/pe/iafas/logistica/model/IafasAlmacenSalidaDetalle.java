package ep.mil.pe.iafas.logistica.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IafasAlmacenSalidaDetalle {
	
	private String cPeriodoCodigo;
	private int nAlmacenSalidaCodigo;
	private int nItemCodigo;
	private int nAlmacenSalidaSolicitado;
	private int nAlmacenSalidaAtendido;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date dUsuarioFecha;
	private String vusuarioAtiende;
	private Date dUsuarioAtiende;
	
	
}
