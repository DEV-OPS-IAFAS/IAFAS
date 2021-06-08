package ep.mil.pe.iafas.personal.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasPersona {
	
	 private String vpersonaNumeroDoc; 
	 private String vpersonaPaterno;
	 private String vpersonaMaterno ;
	 private String vpersonaNombres ;
	 private String cpersonaGenero ;
	 private Date  dpersonaNacimiento; 
	 private Integer npersonaCodigo ;
	 private String ctipoDocumentocodigo; 
	 private Integer nestadoCivilCodigo ;
	 private String vpersonaCelular ;
	 private String vpersonaTelefono ;
	 private String vpersonaCorreo ;
	 private String vpersonaDireccion; 
	 private String vusuarioCreador ;
	 private Date dusuarioCreador ;
	 private String vusuarioCodigo ;
	 private Date dusuarioFecha;
	 
	 private String genero;
	 private String documento;
	 private String estadoCivil;
	 
	 private String desArea;
	 private String desGrado;
	 
	 private String ndetalle;
	 
	 private String cperiodoCodigo;
	 private Integer ngradoCodigo;
	 private String cgradoTipo;
	 private String careaLaboralCodigo;
	 private String vpersonalCargo;
	 private String cestadoCodigo;
	 private String vcip;
	 
	 private Integer npersonaFamiliaCodigo;
	 private Integer npersonaFamilaPersona;
	 private Integer ntipoFamiliaCodigo;
	 
	 private String numeroVer;
	 private String paternoVer;
	 private String maternoVer;
	 private String nombreVer;
	 private String codigoVer;
    
}
