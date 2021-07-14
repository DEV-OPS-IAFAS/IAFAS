package ep.mil.pe.iafas.logistica.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasProveedores {
	
	
	private String cproveedorRuc;
	private String vproveedorRazonSocial;
	private String vproveedorRepresentante;
	private String vproveedorDireccion;
	private String cproveedorTipo;
	private String vproveedorTelefono;
	private String cestadoCodigo;
	private String vusuarioCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private Date dusuarioFecha;
	
	private String vproveedorBancoCci;
	private String vproveedorBancoCuenta;
	private String cbancoCodigo;
	
	private String vproveedorRnpCodigo;
	private Date dproveedorRnpInicio;
	private Date dproveedorRnpVigencia;   
	

}
