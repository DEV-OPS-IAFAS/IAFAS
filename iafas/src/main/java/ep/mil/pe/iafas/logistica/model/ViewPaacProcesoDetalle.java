package ep.mil.pe.iafas.logistica.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ViewPaacProcesoDetalle {

	public ViewPaacProcesoDetalle() {
		// TODO Auto-generated constructor stub
	}

	private String periodo;
	private int presupuesto;
	private int codProceso;
	private int codItem;
	private String descItem;
    private BigDecimal precioItem;
    private BigDecimal cantidadItem;
    private String unidadMedidaCod;
    private String unidadMedDesc;
    
    private BigDecimal cantidadIng = new BigDecimal(0);
          
          

}
