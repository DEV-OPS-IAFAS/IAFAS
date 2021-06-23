package ep.mil.pe.iafas.programacion.model;

import lombok.Data;

@Data
public class IafasItem {

	private int nItemCodigo;
	private String cItemCodigo;
	private String vItemDescripcion;
	private String cUnidadMedidaCodigo;
	private String vItemUbicacionA;
	private String vItemUbicacionB;
	private String vItemUbicacionC;
	private String cItemTipoCodigo;
	private String cItemGrupoCodigo;
	private String cItemClaseCodigo;
	private String cItemFamiliaCodigo;
	private String cEstadoCodigo;
	private String descripcionMoneda;
}
