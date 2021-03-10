package ep.mil.pe.iafas.administrativo.devengado.model;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class DevengadoDet {

	private String vTipoDocumentoCom;
	private String vSerieCom;
	private String vNroCom;
	private String vruc;
	private String vCodImp;
    private String vaa;
    private String vIdClasificador;
    private String vcodSec;
    private BigDecimal nImpRetSolCad;
    private String vSecuencia;
    private String vEstado;
    private String vFuenteFinanciamiento;
    private String vUsusarioCodigo;
    private Date dFechaIng;
    private String vUsuarioEstado;
    private Date dFechaEstado;
	
}
