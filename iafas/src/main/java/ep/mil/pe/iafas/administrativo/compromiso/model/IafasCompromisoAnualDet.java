package ep.mil.pe.iafas.administrativo.compromiso.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class IafasCompromisoAnualDet {

	private String vanoDocumento;
    private String vsecuenciaA;
    private String vcorrelativoA;
    private String vnroCertificado;
    private String vcodSec;
    private String vidClasificador;
    private String nomClasificador;
    private BigDecimal nimpMontoSol;
    private BigDecimal nimpMontoExt;
    private String vusuarioIng;
    private String cadena;
    private String descTarea;
    
    private BigDecimal montoIngresado = new BigDecimal(0);
    

}
