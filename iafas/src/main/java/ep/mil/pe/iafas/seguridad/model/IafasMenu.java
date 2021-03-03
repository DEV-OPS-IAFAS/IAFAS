package ep.mil.pe.iafas.seguridad.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasMenu {

	private String cmoduloCodigo;
	private String cmenuCodigo;
	private String vmenuNombre;
	private String cestadoCodigo;
	private Date   dusuarioFecha;
	private String vmenuServlet;
	private String vmenuModo;
	private String vusuarioCreador;
	private Date   dusuarioCreador;
}
