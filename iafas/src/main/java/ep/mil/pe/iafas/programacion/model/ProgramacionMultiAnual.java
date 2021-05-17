package ep.mil.pe.iafas.programacion.model;

import lombok.Data;

@Data
public class ProgramacionMultiAnual {

	private String periodo;
	private int fuenteFinac;
	private int tareaPtalCodigo;
	private String ubigeoCodigo;
	private int importeA;
	private int importeB;
	private int importeC;
	private int metaFisicaA;
	private int metaFisicaB;
	private int metaFisicaC;
	private String usuarioCodigo;
	private String tipo;
	
	private int canio;
	private int ano1;
	private int ano2;
	private int ano3;
	private int codigoTareaPtal;
	private String tarea;
	private int monto1;
	private int monto2;
	private int monto3;
	private int meta1;
	private int meta2;
	private int meta3;
	private String  estado;
	private String  desEstado;
	private String ubigeo;
	private int detalle1;
	private int detalle2;
	private int detalle3;
	private int saldo1;
	private int saldo2;
	private int saldo3;
	private String codDepa;
	private String codProv;
	private String codDistr;
	private String codigoRespuesta;
	private String mensajeRespuesta;
}
