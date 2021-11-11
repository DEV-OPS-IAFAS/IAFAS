package ep.mil.pe.iafas.patrimonio.model;

import java.util.Date;

import lombok.Data;

@Data
public class IafasPatrimonio {

	private int numRow;
	private int nPatrimonioCodigo;
	private String vPatrimonioCodigo;	
	private String vPatrimonioDescripcion;
	private int nMarcaCodigo;
	private String vPatrimonioModelo;
	private String vPtarimonioColorCodigo;
	private int nTipoMaterialCodigo;
	private int cPatrimonioCategoriaCodigo;
	private String vpatrimonioSerie;
	private String vPatriminioDimensiones;
	private String vPatrimonioObservacion;
	private String vpatrimonioTipo;
	private String cEstadoCodigo;
	private String vUsuarioCreador;
	private Date dUsuarioCreador;
	private String vUsuarioCodigo;
	private Date dUsuarioFecha;
	private String  desEstado;
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private String tipo;
	private String desMarca;
	private String desColor;
	private String nombrePatrimonio;
}
