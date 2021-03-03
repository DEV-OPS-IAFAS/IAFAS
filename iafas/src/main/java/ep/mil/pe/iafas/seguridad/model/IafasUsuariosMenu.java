package ep.mil.pe.iafas.seguridad.model;

import java.util.Date;

import lombok.Data;


@Data
public class IafasUsuariosMenu {

	private String vusuarioCodigo;
	private String cmoduloCodigo;
	private String cmenuCodigo;
	private String vusuarioCreador;
	private Date   dusuarioFecha;
	private String vmoduloNombre;
	private String vmenuNombre;
	private String vmenuServlet;
	
}
