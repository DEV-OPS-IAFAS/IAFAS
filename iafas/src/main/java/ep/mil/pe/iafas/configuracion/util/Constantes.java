package ep.mil.pe.iafas.configuracion.util;

import java.math.BigDecimal;

public class Constantes {

	private Constantes() {		
	}
	
	//Rutas para Almacenar Archivos
	public static final String ROOT_LOCAL="D:\\OPRE\\Proyectos\\IafasV2.0\\IAFAS\\iafas\\src\\main\\webapp\\Archivos\\";
	public static final String ROOT_PRODUCCION="/opt/tomcat/webapps/iafas/Archivos/";
	
	public static final String VACIO = "";
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String MODE_PRE_REGISTRO = "P";
	public static final String MODE_REGISTRO = "I";
	public static final String MODE_ACTUALIZACION = "U";
	public static final String MODE_ELIMINACION_LOGICA = "D";
	public static final String CERO_STRING = "0";
	public static final String UNO_STRING = "1";
	public static final String LOGIN = "login.xhtml";
	public static final String MAIN_PRINCIPAL = "mainPrincipal.xhtml";
	public static final String ESTADO_ACTIVO = "AC";
	public static final String ESTADO_ANULADO = "AN";
	public static final String CERO_CERO = "00";
	public final static BigDecimal UNO_BG = new BigDecimal(1);
	
	public final static String TMOV_INICIAL = "N";
	public final static String TMOV_AMPLIACION = "M";
	public final static String TMOV_ANULACION = "A";
	public final static String TMOV_REBAJA = "R";
	
	public final static String ESTADO_REGISTRADO = "1";
	public static final String MODE_ANULACION = "A";
	
	public static final String ID_ORDEN_COMPRA = "031"; 
	public static final String ID_ORDEN_SERVICIO = "032"; 
	public static final String ID_CONTRATOS = "060"; 
	
	/*Cambios agregados el 31.03.2021*/
	public static final int CERO_INT =0;
	public static final String CERO_CERO_STRING = "00";
	public static final String DOS_STRING = "2";
	public static final int UNO_INT =1;
	public static final int DOS_INT =2;
	public static final int TRES_INT = 3;
	public static final String MESSAGE_VALIDACION_PARAMETROS = "Debe seleccionar los parámetros de entrada.";
	public static final int UNO_NEGATIVO = -1;
	public static final String MODE_CERRAR_TECHO = "F";
	public static final String MODE_ABRIR_TECHO = "A";
	public static final String ESTADO_TECHO_CERRADO = "CE";
	public static final String MESSAGE_VALIDACION_TECHO_CERRADO = "El registro se encuentra en estado CERRADO!.";
	public static final String MESSAGE_VALIDACION_SALDOS = "El monto a ingresar no puede ser mayor al SALDO!.";
	
	/*Cambios agrehgado para CNV*/
	public static final String TEXTO_CNV = "CNV-";
	public static final String GUION="-";
	public static final String DOS_PUNTOS=":";
	public static final String VALOR_R="R";
	public static final String GIRO_TOTAL="T";
	public static final String GIRO_CLASIFICADOR="C";
	public static final String GIRO_RETENCION="R";
	public static final String MESSAGE_VALIDACION_BUSQUEDA_GIRO = "El expediente no esta aprobado por devengado o no existe!";
	public static final String MESSAGE_VALIDACION_MONTO_GIRO = "El Monto a girar es mayor al Techo asignado a esta cuenta!";
	public static final String MESSAGE_VALIDACION_SALDO_GIRO = "El Monto a girar es mayor al Saldo de esta cuenta.!";
	public static final String MESSAGE_VALIDACION_ENTIDAD_GIRO = "Debe seleccionar la entidad del giro.";
	public static final String MESSAGE_VALIDACION_CUENTA_GIRO = "Debe seleccionar la Cuenta  del giro.";
	public static final String MESSAGE_VALIDACION_MONEDA_GIRO = "Debe seleccionar la Moneda del giro.";
	public static final BigDecimal ZERO_BIG_DECIMAL= BigDecimal.ZERO;
	public static final String MESSAGE_IS_GIRO = "El expediente se encuentra Girado!, dar click en visualizar para ver su detalle";
	public static final String CONSULTA_PRINCIPAL_GIROS = "mainIafasConsultaGiradosExpediente.xhtml";
	public static final String MESSAGE_VALIDACION_TIPO_CAMBIO= "Debe llenar correctamente el tipo de cambio.";
	public static final boolean FALSE = false;
	public static final boolean TRUE = true;
	public static final String TEXTO_HTS = "HTS-";
	public static final String METODO_JS_CNV="verMensajes()";
	public static final String MESSAGE_VALIDACION_CAMPOS_NULOS = "Debe ingresar los datos requeridos.";
	public static final String MESSAGE_VALIDACION_TOTAL_EXCEDE_AL_SALDO = "El total excede al saldo.";
	public static final String CODIGO_CIERRE = "C";
	public static final String VALIDACION_AF = "Debe Seleccionar el Año.";
	public static final String VALIDACION_MES = "Debe Seleccionar el Mes.";
}
