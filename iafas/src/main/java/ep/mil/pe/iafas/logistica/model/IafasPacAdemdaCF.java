package ep.mil.pe.iafas.logistica.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class IafasPacAdemdaCF {

	public IafasPacAdemdaCF() {
		// TODO Auto-generated constructor stub
	}
    // Ademda
	private int ncontratoCodigo;
	private String cperiodoCodigo;
	private int npacContratoAdemdaCodigo;
	private Date dpacContratoAdemdaFecha;
	private String vpacContratoAdemdaDocumento;
	private String vpacContratoAdemdaMotivo;
	private String vpacContratoAdemdaArchivo;
	private String cestadoCodigo;
	private String vusuarioCodigo;
	           
    // Carta Fianza
	private int npacContCFCod;
	private String npacContCFNumero;
	private BigDecimal npacContCFMonto;
	private int nmonedaCodigo;
	private BigDecimal npacContCFMontoEx;
	private BigDecimal npacContCFTipCam;
	private Date npacContCFVigencia;
	private String cbancoCodigo;
	private String vpacContCFArchivo;
	
	private String tipo;
	private String simboloMon;
	 
}
