package ep.mil.pe.iafas.administrativo.girado.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasGirado {

	private String vano;
	private String vsecuencia;
	private String vsecuenciaInt;
	private String vregSiaf;
	private String vctaCodigo;
	private String vcodTipGiro;
	private String vnroCheCar;
	private String vestado;
	private String vusuarioIng;
	private Date dfechaIng;
	private String vusuarioMod;
	private Date dfechaMod;
	private Date dfechaCheCar;
	private Date dfechaPago;
	private String vdestinatario;
	private String vfuenteFinanciamiento;
	private String vruc;
	private String bancodPais;
	private String bancodBco;
	private String vconcepto;
	private String vtipDocumentoM;
	private String vanoDocumentoM;
	private String vnroDocumentoPagoM;
	private Date dfechaDocumentoM;
	private String vtipDocumentoBancario;
	private String vtipDocumentoCom;
	private String vserieCom;
	private String vnroCom;
	private String vanoDocumentoGiro;
	private String vnroDocumentoGiro;
	private String vtipDocumentoGiro;
	private String vcci;
	private BigDecimal ntipCam;
	private BigDecimal impMonSol;
	private BigDecimal impMonExt;
	private String vtipMon;
	private String vglosa;
	private String cproveedorCuentaBanco;
	private String nombreBanco;
	
	/*DATOS DEL MENSUAL-DEVENGADO*/
	private String vanoDev;
	private String vsecuenciaDev;
	private String vsecuenciaIntDev;
	private String vcodCla;
	private String vcodSec;
	private String mode;
	

}
