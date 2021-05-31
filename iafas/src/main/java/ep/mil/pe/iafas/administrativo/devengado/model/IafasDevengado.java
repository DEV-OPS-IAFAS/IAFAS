package ep.mil.pe.iafas.administrativo.devengado.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data  /* para evitar hacer get y set */
public class IafasDevengado {

	private String vano;
	private String secuencia;
	private String correlativo;
	private String vexpediente;
	private String vglosa;
	private String vcodTipoFuncionamiento;
	private String vfuenteFinanciamiento;
	private double ntipCam;
	private String vcodEstado;
	private String vcodMoneda;
	private String vtipDocumentoMen;
	private String vnroDocumentoMen;
	private Date dfechaDocumento;
	private String vanoDocumento;
	private String vsecuenciaA;
	private String vcorrelativo;
	private String vnroCertificado;
	private String vcodProcesoSel;
	private String cproveedorRuc;
	private String vtipDocumentoCom;
	private String vnroDocumentoCom;
	private String vserieCom;
	private Date   dfechaDevengado;
	private String vusuarioIng;
	private String razonSocial;
	private BigDecimal nimpMonSol;
	
	private BigDecimal montoIngresado = new BigDecimal(0);
	private String siaf;
	private String Glosa;
	
	private String ctipoActa;
	private String vnroActa;
	private Date  dfechaActa;
	
	
	
	
	
}
