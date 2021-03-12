CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_MTO_GIRADO`(IN XANO VARCHAR(4),
                                             IN XREG_SIAF VARCHAR(10), 
 IN XCOD_CTE VARCHAR(3), 
 IN XTIP_GIR VARCHAR(3),
 IN XGLOSA VARCHAR(500),
 IN XIMP_DOCU DECIMAL(13,2),
 IN XRUC        VARCHAR(11),
 IN XUSUARIO    VARCHAR(10),
 IN XMODO CHAR(1), 
 IN XTIP_CAM    DECIMAL(13,2),
 IN XTIP_MON    VARCHAR(3))
BEGIN
DECLARE VAR_SECUENCIA       VARCHAR(4);
DECLARE VAR_SECUENCIA_INT   VARCHAR(4);
DECLARE VAR_SECUENCIA_DEV       VARCHAR(4);
DECLARE VAR_SECUENCIA_INT_DEV   VARCHAR(4);
DECLARE VAR_CONT_DEV        INT;
DECLARE VAR_CONT_GIRO       INT;
DECLARE VAR_FUENTE_FINANCIAMIENTO   VARCHAR(2);
DECLARE VAR_TIPO_FINANCIAMIENTO VARCHAR(2);
DECLARE VAR_NIMP_MON_EXT        DECIMAL(13,2);  
DECLARE VAR_TIP_MOVIMIENTO      CHAR(1);
DECLARE VAR_COD_ORIGEN          VARCHAR(2);
DECLARE VAR_COD_RECURSO         VARCHAR(2); 
DECLARE VAR_SECUENCIA_GIRO      VARCHAR(6);
DECLARE VAR_SECUENCIA_INT_GIRO  VARCHAR(4);
DECLARE VAR_ID_CLASIFICADOR     VARCHAR(4);
DECLARE VAR_COD_SEC_FUNC        VARCHAR(4);
DECLARE VAR_NIMP_MON_SOL        DECIMAL(13,2);
DECLARE VAR_NRO_CHE_CAR         VARCHAR(10);

SELECT 
    COUNT(1)
INTO VAR_CONT_DEV FROM
    iafas_movimiento
WHERE
    VANO = XANO AND VREG_SIAF = XREG_SIAF
        AND VCOD_ESTADO = 'N'
        AND VFASE = 'D';

/* INICIO MODO INSERCION*/
IF XMODO = "I" THEN 
 
    IF ( VAR_CONT_DEV = 1 ) THEN 
        
        SELECT IFNULL(LPAD(MAX(VNRO_CHE_CAR+1),10,0),'0000000001') INTO VAR_NRO_CHE_CAR
        FROM iafas_girado
        WHERE VANO=DATE_FORMAT(sysdate(), '%Y');
 
 
 /*[INICIO]:: OBTENIENDO DATOS DE LA FASE ANTERIOR*/
            SELECT 
    VFUENTE_FINANCIAMIENTO,
    VCOD_TIPO_FINANCIAMIENTO,
    VSECUENCIA,
    VSECUENCIA_INT
    INTO VAR_FUENTE_FINANCIAMIENTO , VAR_TIPO_FINANCIAMIENTO , VAR_SECUENCIA_DEV , VAR_SECUENCIA_INT_DEV FROM
    iafas_movimiento
    WHERE
    VANO = XANO AND VREG_SIAF = XREG_SIAF
        AND VCOD_ESTADO = 'N'
        AND VFASE = 'D';
 /*[FIN]:: OBTENIENDO DATOS DE LA FASE ANTERIOR*/
 
 
            SELECT 
            IFNULL(LPAD(MAX(VSECUENCIA + 1), 4, 0), '0001')
            INTO VAR_SECUENCIA FROM
            iafas_movimiento
            WHERE
            VANO = DATE_FORMAT(SYSDATE(), '%Y');
 
            SELECT 
            IFNULL(LPAD(MAX(VSECUENCIA_INT + 1), 4, 0),'0001')
            INTO VAR_SECUENCIA_INT 
            FROM iafas_movimiento
            WHERE
            VANO = DATE_FORMAT(SYSDATE(), '%Y')
            AND VSECUENCIA = VAR_SECUENCIA;
            /*[FIN]:: GENERANDO LAS SECUENCIAS PARA LA FASE DE GIRO*/
 
            SELECT XIMP_DOCU * XTIP_CAM INTO VAR_NIMP_MON_EXT FROM DUAL;
            /*[FIN]:: CALCULANDO EL IMPORTE CUANDO ES EN OTRO TIPO DE MONEDA*/
 
        INSERT INTO iafas_movimiento(VANO,VSECUENCIA, VSECUENCIA_INT,VREG_SIAF,VGLOSA,VFASE,VTIP_MOVIMIENTO,VCOD_ORIGEN,VCOD_RECURSO,NIMP_MON_SOL,
        NTIP_CAM,VTIP_MON, VCOD_ESTADO,VFUENTE_FINANCIAMIENTO,VCOD_TIPO_FINANCIAMIENTO,VUSUARIO_ING , DFECHA_ING, NIMP_MON_EXT) 
 
        VALUES(XANO, VAR_SECUENCIA, VAR_SECUENCIA_INT, XREG_SIAF, UPPER(XGLOSA), 'G', VAR_TIP_MOVIMIENTO, VAR_COD_ORIGEN, VAR_COD_RECURSO,XIMP_DOCU,
        XTIP_CAM, XTIP_MON, 'N',VAR_FUENTE_FINANCIAMIENTO,VAR_TIPO_FINANCIAMIENTO,XUSUARIO,SYSDATE(), VAR_NIMP_MON_EXT );
 
 
 BEGIN
        -- Declaración de variables
        DECLARE COD_CLA VARCHAR(4);
        DECLARE SEC_FUNC VARCHAR(4);
        DECLARE IMPORTE DECIMAL;
 DECLARE no_hay_mas_registros INT default 0;
 
 ##Se declara el cursor con el select con cuyos datos se va a iterar
        DECLARE F1 CURSOR FOR

            SELECT CAD.VID_CLASIFICADOR AS COD_CLA, CAD.VCOD_SEC_FUNC AS SEC_FUNC, sum(CAD.NIMP_MON_SOL ) IMPORTE
                        FROM iafas_movimiento CAB , iafas_movimiento_mensual_cad CAD, iafas_devengado DEV
                        WHERE 
                        CAB.VFASE='D'
                        AND CAB.VANO = CAD.VANO
                        AND CAB.VSECUENCIA = CAD.VSECUENCIA
                        AND CAB.VSECUENCIA_INT = CAD.VSECUENCIA_INT
                        AND CAD.VANO = DEV.VANO
                        AND CAD.VSECUENCIA = DEV.VSECUENCIA
                        AND CAD.vsecuencia_int = DEV.vsecuencia_int AND
                        CAB.VREG_SIAF =XREG_SIAF AND 
                        CAB.VANO=XANO
                        group by CAD.VID_CLASIFICADOR,CAD.VCOD_SEC_FUNC;
 
    declare continue handler for not found set no_hay_mas_registros = 1;
 OPEN F1 ;
 
 F2: LOOP
 
 -- Obtenemos la primera fila en la variables correspondientes
        FETCH F1 INTO COD_CLA, SEC_FUNC,IMPORTE; 
 if (no_hay_mas_registros = 1) then
                leave F2;
            end if;
 
            INSERT INTO iafas_movimiento_mensual_cad(VANO,VSECUENCIA,VSECUENCIA_INT,VCOD_SEC_FUNC,VID_CLASIFICADOR, NIMP_MON_SOL ,NIMP_MON_EXT, VUSUARIO_ING,DFECHA_ING )
            VALUES(XANO, VAR_SECUENCIA ,VAR_SECUENCIA_INT , SEC_FUNC, COD_CLA, IMPORTE,IMPORTE , XUSUARIO, SYSDATE());
            COMMIT;
 
 END LOOP F2; 
 CLOSE F1; 
 
 END;
 
 ## INICIO DE LA INSERCION EN EL GIRADO 
 INSERT INTO iafas_girado(VANO,VSECUENCIA, VSECUENCIA_INT,VCTACODIGO, VCOD_TIP_GIRO, VCONCEPTO, VNRO_CHE_CAR, VESTADO, VUSUARIO_ING,DFEC_CHE_CAR,VRUC )
 VALUES(XANO, VAR_SECUENCIA, VAR_SECUENCIA_INT, XCOD_CTE, XTIP_GIR, UPPER(XGLOSA), VAR_NRO_CHE_CAR, 'N', XUSUARIO,SYSDATE(),XRUC);
 
 COMMIT;
 ## FIN DE LA INSERCION EN EL GIRADO 
 

     ##GENERANDO EL GIRO DE LAS RETENCIONES
     BEGIN
        -- Declaración de variables
        DECLARE COD_CLA_RET VARCHAR(4);
        DECLARE SEC_FUNC_RET VARCHAR(4);
        DECLARE IMPORTE_RET DECIMAL;
        DECLARE VCOD_IMP     VARCHAR(2);    
        DECLARE VTIPDOC_COM  VARCHAR(3);
        DECLARE VSERIE_COM   VARCHAR(4);
        DECLARE VNRO_COM     VARCHAR(11);
        DECLARE VRUC_RET     VARCHAR(11); 
        DECLARE no_hay_mas_registros_ret INT default 0;
     
      ##Se declara el cursor con el select con cuyos datos se va a iterar
        DECLARE F1_RET CURSOR FOR
            SELECT ret.VNRO_COM AS VNRO_COM, ret.VTIP_DOCUMENTO_COM AS VTIPDOC_COM, ret.VSERIE_COM AS VSERIE_COM, ret.VRUC AS VRUC_RET, ret_cad.VID_CLASIFICADOR AS COD_CLA_RET, ret_cad.VCOD_SEC AS SEC_FUNC_RET, 
            ret_cad.VCOD_IMP AS VCOD_IMP, ret_cad.NIMP_RET_SOL_CAD AS IMPORTE_RET
            FROM iafas_movimiento cab, iafas_devengado dev , iafas_ret_comprobante ret , iafas_ret_comprobante_cad ret_cad
                    where 
                    cab.VANO=dev.VANO AND
                    cab.VSECUENCIA=dev.VSECUENCIA AND
                    cab.VSECUENCIA_INT=dev.VSECUENCIA_INT AND
                    cab.VANO=XANO AND 
                    cab.VREG_SIAF=XREG_SIAF AND 
                    cab.VFASE = 'D' AND 
                    dev.VTIP_DOCUMENTO_COM= ret.VTIP_DOCUMENTO_COM and
                    dev.VSERIE_COM= ret.VSERIE_COM and
                    dev.VNRO_DOCUMENTO_COM = ret.VNRO_COM and 
                    dev.CPROVEEDOR_RUC = ret.VRUC and 
                    ret.VTIP_DOCUMENTO_COM= ret_cad.VTIP_DOCUMENTO_COM and
                    ret.VSERIE_COM= ret_cad.VSERIE_COM and
                    ret.VNRO_COM = ret_cad.VNRO_COM and 
                    ret.VRUC = ret_cad.VRUC and 
                    ret.VCOD_IMP = ret_cad.VCOD_IMP;
     
        declare continue handler for not found set no_hay_mas_registros_ret = 1;
    OPEN F1_RET ;
    F2_RET: LOOP
     -- Obtenemos la primera fila en la variables correspondientes
    FETCH F1_RET INTO VNRO_COM, VTIPDOC_COM,VSERIE_COM,VRUC_RET, COD_CLA_RET,SEC_FUNC_RET,VCOD_IMP,IMPORTE_RET  ; 
         if (no_hay_mas_registros_ret = 1) then
                        leave F2_RET;
         end if;
            
            SELECT 
            IFNULL(LPAD(MAX(VSECUENCIA + 1), 4, 0), '0001')
            INTO VAR_SECUENCIA FROM
            iafas_movimiento
            WHERE
            VANO = DATE_FORMAT(SYSDATE(), '%Y');
 
            SELECT 
            IFNULL(LPAD(MAX(VSECUENCIA_INT + 1), 4, 0),'0001')
            INTO VAR_SECUENCIA_INT 
            FROM iafas_movimiento
            WHERE
            VANO = DATE_FORMAT(SYSDATE(), '%Y')
            AND VSECUENCIA = VAR_SECUENCIA;
            
            SELECT IFNULL(LPAD(MAX(VNRO_CHE_CAR+1),10,0),'0000000001') INTO VAR_NRO_CHE_CAR
            FROM iafas_girado
            WHERE VANO=DATE_FORMAT(sysdate(), '%Y');
         
         INSERT INTO iafas_movimiento(VANO,VSECUENCIA, VSECUENCIA_INT,VREG_SIAF,VGLOSA,VFASE,VTIP_MOVIMIENTO,VCOD_ORIGEN,VCOD_RECURSO,NIMP_MON_SOL,
        NTIP_CAM,VTIP_MON, VCOD_ESTADO,VFUENTE_FINANCIAMIENTO,VCOD_TIPO_FINANCIAMIENTO,VUSUARIO_ING , DFECHA_ING, NIMP_MON_EXT) 
 
        VALUES(XANO, VAR_SECUENCIA, VAR_SECUENCIA_INT, XREG_SIAF, UPPER('QUE GLOSA IRA EN LAS RETENCIONES'), 'G', VAR_TIP_MOVIMIENTO, VAR_COD_ORIGEN, VAR_COD_RECURSO,IMPORTE_RET,
        XTIP_CAM, XTIP_MON, 'N',VAR_FUENTE_FINANCIAMIENTO,VAR_TIPO_FINANCIAMIENTO,XUSUARIO,SYSDATE(), IMPORTE_RET );
        COMMIT;
        
        INSERT INTO iafas_movimiento_mensual_cad(VANO,VSECUENCIA,VSECUENCIA_INT,VCOD_SEC_FUNC,VID_CLASIFICADOR, NIMP_MON_SOL ,NIMP_MON_EXT, VUSUARIO_ING,DFECHA_ING )
        VALUES(XANO, VAR_SECUENCIA ,VAR_SECUENCIA_INT , SEC_FUNC_RET, COD_CLA_RET, IMPORTE_RET,IMPORTE_RET , XUSUARIO, SYSDATE());
        COMMIT;
 
        INSERT INTO iafas_girado(VANO,VSECUENCIA, VSECUENCIA_INT,VCTACODIGO, VCOD_TIP_GIRO, VCONCEPTO, VNRO_CHE_CAR, VESTADO, VUSUARIO_ING,DFEC_CHE_CAR,VRUC, VTIP_DOCUMENTO_COM, VSERIE_COM, VNRO_COM )
        VALUES(XANO, VAR_SECUENCIA, VAR_SECUENCIA_INT, XCOD_CTE, XTIP_GIR, UPPER('QUE GLOSA IRA EN LAS RETENCIONES'), VAR_NRO_CHE_CAR, 'N', XUSUARIO,SYSDATE(),VRUC_RET,VTIPDOC_COM,VSERIE_COM  ,VNRO_COM );
        COMMIT;
        
        UPDATE iafas_ret_comprobante SET VNRO_COM_PAG=VAR_NRO_CHE_CAR
        WHERE 
        VTIP_DOCUMENTO_COM=VTIPDOC_COM and 
        vserie_com=VSERIE_COM and 
        vnro_com=VNRO_COM and 
        vruc=VRUC_RET;
        COMMIT;
        
         END LOOP F2_RET; 
 CLOSE F1_RET; 
 END;
 ## FIN DEL REGISTRO DE LAS RETENCIONES
 
 
 ELSE
         SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NO EXISTE UN REGISTRO EN LA FASE DEVENGADO PARA REALZIAR EL GIRO.';
    END IF;     
END IF; 
/* FIN MODO INSERCION*/



/* INICIO MODO ACTUALIZACION*/
IF XMODO = "U" THEN 

SELECT COUNT(1)INTO VAR_CONT_GIRO 
FROM iafas_movimiento 
WHERE 
VREG_SIAF=XREG_SIAF AND 
VCOD_ESTADO = 'AC' AND 
VFASE='G';

    IF (VAR_CONT_GIRO = 1 ) THEN 
        
 SELECT VSECUENCIA , VSECUENCIA_INT
 INTO VAR_SECUENCIA_GIRO , VAR_SECUENCIA_INT_GIRO
        FROM iafas_movimiento 
        WHERE 
        VREG_SIAF=XREG_SIAF AND 
        VCOD_ESTADO = 'AC' AND 
        VFASE='G';
 
        UPDATE iafas_movimiento 
SET 
    DFECHA_MOD = SYSDATE()
WHERE
    VANO = XANO
        AND VSECUENCIA = VAR_SECUENCIA_GIRO
        AND VSECUENCIA_INT = VAR_SECUENCIA_INT_GIRO;
 ELSE 
 SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'NO EXISTE REGISTRO EN LA FASE DE GIRO.';
 END IF;
 
END IF;
/* FIN MODO ACTUALIZACION*/
END