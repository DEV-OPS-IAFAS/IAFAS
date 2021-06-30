package ep.mil.pe.iafas.personal.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasPersonaVacaciones {
	
	private String cperiodoCodigo;
	private Integer npersonaCodigo;
	private Integer npersonalVacacionesCodigo;
	private Date dpersonalVacacionesFecha;
	private Date dpersonalVacacionesInicio;
	private Date dpersonalVacacionesDin;
	private String cubigeoOrigen;
	private String cubigeoDestino;
	private String cestadoCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private String vusuarioAprueba;
	private Date dusuarioAprueba;
	private Date dusuarioFecha;
	private Integer npersonalTipoPlanillaCodigo;
	private Integer npersonalCantidadDias;
	private Integer npersonalCantidadHoras;
	
	
	private String nombreCodigo;
	private String origenUbi;
	private String destinoUbi;
	private String usuarioApro;
	
	
	private String vpersonaNumeroDocumento;
	private String vpersonaPaterno;
	private String vpersonaMaterno;
	private String vpersonaNombre;
	private Integer ncodigo;
	
	
	private Date fecinicio;
	private Date fecfin;
	private Integer codigoinicio;
	private Integer codigovacacion;
	private String destipoPlanilla;
	
	 private String estadoCivil;
	 private String vpersonalCargo;
	 private String desArea;
	 private String desGrado;
	 private Integer nrodocumetoPer;
}
