package ep.mil.pe.iafas.administrativo.compromiso.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasCompromisoAnual {
	
	private String vanoDocumento;
    private String vsecuenciaA;
    private String vcorrelativoA;
    private String vnroCertificado;
    private String vtipoDocumentoA;
    private String vnroDocumentoPagoA;
    private Date dfechaDocumento;
    private String vtipoMovimiento;
    private String vtipoOperacion;
    private String vfuenteFinanciamiento;
    private String cproveedorRuc;
    private double ntipCam;
    private String vcodProcesoSel;
    private String vcodMoneda;
    private BigDecimal nimpMonSol;
    private BigDecimal nimpMonDol;
    private String vusuarioIng;
    private String vglosa;
    private String vcodEstado;
    private String vidProv;
    private String vcodTipoFinanciamiento;
    
    private String tipoCertificacion;
    private String razonSocial;
    private String desPto;
    
    private BigDecimal totCert;
    private BigDecimal totCA;
    private BigDecimal difCA;

}
