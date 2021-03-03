package ep.mil.pe.iafas.seguridad.model;

import java.util.Date;

import com.mysql.cj.jdbc.Blob;

import lombok.Data;

@Data
public class IafasUsuarios {

	private String vusuarioCodigo;
	private String vusuarioPaterno;
	private String vusuarioMaterno;
	private String vusuarioNombres;
	private String vusuarioPassword;
	private String vusuarioCorreo;
	private String vusuarioTelefono;
	private String cestadoCodigo;
	private String vusuarioModifica;
	private Date   dusuarioModifica;
	private String vusuarioCargo;
	private String areaLaboralCodigo;
	private String vusuarioCreador;
	private Date   dusuarioCreador;
	private Integer nusuarioAutorizacion;
	private String modo;
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private Blob cusuarioFirma;
	private Blob cusuarioHuellaDigital;
	public String get(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
