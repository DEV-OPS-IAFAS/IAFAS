package ep.mil.pe.iafas.seguridad.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasModulos {

	private String cmoduloCodigo;
	private String cestadoCodigo;
	private String vusaurioCodigo;
	private Date   dusuarioFecha;
	private String vusuarioCreador;
	private Date   dusuarioCreador;	
	private String vmoduloNombre;
	
}
