package ep.mil.pe.iafas.logistica.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private String descripcionItem;
	private String descripcionUnidadMedida;
	private String codigoItem;
	private String mode;
	private String codigoRespuesta;
	private String mensajeRespuesta;
}
