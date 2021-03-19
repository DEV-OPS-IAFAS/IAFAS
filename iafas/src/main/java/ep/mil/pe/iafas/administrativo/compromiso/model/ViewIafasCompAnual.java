package ep.mil.pe.iafas.administrativo.compromiso.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ViewIafasCompAnual {
	
	private String vanoDocumento;
	private String vsecuenciaA;
	private String vcorrelativoA;
	private String vnroCertificado;
	private String vtipoDocumentoA;
	private String vnroDocumentoPagoA;
	private String vfuenteFinanciamiento;
	private String proveedorRuc;
	private double ntipCam;
	private String vcodTipoFinanciamiento;
	private String vcodProcesoSel;
	private String vcodMoneda;
	private String vcodSecFunc;
	private String vidClasificador;
	private BigDecimal importeAmpliacion;
	private BigDecimal importeRebaja;
	private BigDecimal importeAnulacion;
	private BigDecimal importeIngresado =  new BigDecimal(0);
	private String cadena;
	private String nomCla;
	private String vtipMovimiento;
	private String vusuarioIng;
	private String vglosa;

	public ViewIafasCompAnual() {
		// TODO Auto-generated constructor stub
	}

}
