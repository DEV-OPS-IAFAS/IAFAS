package ep.mil.pe.iafas.administrativo.compromiso.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ViewIafasCompromisoMensual {
	
	private String vano;
	private String vsecuencia;
	private String vsecuenciaInt;
	private String vnroCertificado;
	private String vruc;
	private String vsecuenciaA;
	private String vcorrelativoA;
	private String vcodProcesoSel;
	private String vfuenteFinanciamiento;
	private String vcodTipoFinanciamiento;
	private String vtipoOperacion;
	private String vcodMoneda;
	private String vcodSecFunc;
	private String vidClasificador;
	private String nomCla;
	private String cadena;
	private BigDecimal nimpMontoSol;
    private String vtipoDocumentoA;
    private String vnroDocumentoPagoA;
    private String razonSocial;
    private double ntipCam;
    
    private String concepto;
    private String abTipoDoc;
    private String descTarea;
    
    private BigDecimal montoIngresado = new BigDecimal(0);

	public ViewIafasCompromisoMensual() {
		// TODO Auto-generated constructor stub
	}

}
