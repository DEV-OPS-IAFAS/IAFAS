CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_IDU_PROGRAMACION_MULTIANUAL`(
xCPERIODO_CODIGO                             	CHAR(4),
xNFUENTE_FINANCIAMIENTO_CODIGO                	INTEGER,
xNTAREA_PRESUPUESTAL_CODIGO                 	INTEGER,
xCUBIGEO_CODIGO									CHAR(6),
xNPROGRAMACION_MULTIANUAL_IMPORTE_A           	INTEGER,
xNPROGRAMACION_MULTIANUAL_IMPORTE_B           	INTEGER,
xNPROGRAMACION_MULTIANUAL_IMPORTE_C           	INTEGER,
xNPROGRAMACION_MULTIANUAL_META_FISICA_A			INTEGER,
xNPROGRAMACION_MULTIANUAL_META_FISICA_B			INTEGER,
xNPROGRAMACION_MULTIANUAL_META_FISICA_C			INTEGER,
xVUSUARIO_CODIGO                             	VARCHAR(20),
xTIPO 											CHAR(1),
OUT CODIGO_RESPUESTA	CHAR(1),
OUT MENSAJE_RESPUESTA	VARCHAR(100)
)
BEGIN

/*DECLARAMOS VARIABLES*/
    DECLARE nRegistro									INTEGER;
    DECLARE cPeriodo									CHAR(4);
    DECLARE cEstado                     				CHAR(2);
	DECLARE	dMENS_ERROR                     			VARCHAR(1024);
	    
    /*OBTENEMOS EL AF SIGUIENTE*/
    SELECT DATE_FORMAT(SYSDATE()+1,'%Y') INTO cPeriodo;    
    /*VERIFICAMOS QUE EL PERIODO DE LA SOLICITUD DE CREDITO PRESUPUESTAL CORRESPONDA AL AF.*/
    IF cPeriodo!=xCPERIODO_CODIGO THEN
		SIGNAL SQLSTATE  '45000'
		SET MESSAGE_TEXT= 'PERIODO CERRADO. NO PUEDE GENERAR UN CERTIFICADO DE CREDITO PRESUPUESTAL QUE NO SEA DEL PERIODO ACTUAL. VERIFICAR!!.',
		MYSQL_ERRNO = 1643;
    END IF;

    -- Buscamos el Registro para INSERTAR, MODIFICAR , ELIMINAR
    SELECT COUNT(*) INTO nRegistro 
    FROM IAFAS_PROGRAMACION_MULTIANUAL_GASTO WHERE 
    CPERIODO_CODIGO=xCPERIODO_CODIGO AND
    NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
    NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO;

    IF nRegistro=0 THEN
        IF xTIPO = 'I' THEN  
            INSERT INTO IAFAS_PROGRAMACION_MULTIANUAL_GASTO(CPERIODO_CODIGO, CANIO_REGISTRO, NFUENTE_FINANCIAMIENTO_CODIGO, NTAREA_PRESUPUESTAL_CODIGO, 
            CUBIGEO_CODIGO, NPROGRAMACION_MULTIANUAL_IMPORTE, NPROGRAMACION_MULTIANUAL_META_FISICA, CESTADO_CODIGO,
            VUSUARIO_CREADOR, DUSUARIO_CREADOR, VUSUARIO_CODIGO, DUSUARIO_FECHA)     
            VALUES(xCPERIODO_CODIGO, CAST(xCPERIODO_CODIGO AS SIGNED), xNFUENTE_FINANCIAMIENTO_CODIGO, xNTAREA_PRESUPUESTAL_CODIGO, 
            xCUBIGEO_CODIGO, xNPROGRAMACION_MULTIANUAL_IMPORTE_A, xNPROGRAMACION_MULTIANUAL_META_FISICA_A, 'PE',
            xVUSUARIO_CODIGO, SYSDATE(), xVUSUARIO_CODIGO, SYSDATE());

            INSERT INTO IAFAS_PROGRAMACION_MULTIANUAL_GASTO(CPERIODO_CODIGO, CANIO_REGISTRO, NFUENTE_FINANCIAMIENTO_CODIGO, NTAREA_PRESUPUESTAL_CODIGO, 
            CUBIGEO_CODIGO, NPROGRAMACION_MULTIANUAL_IMPORTE, NPROGRAMACION_MULTIANUAL_META_FISICA,  CESTADO_CODIGO,
            VUSUARIO_CREADOR, DUSUARIO_CREADOR, VUSUARIO_CODIGO, DUSUARIO_FECHA)     
            VALUES(xCPERIODO_CODIGO, CAST(xCPERIODO_CODIGO+1 AS SIGNED), xNFUENTE_FINANCIAMIENTO_CODIGO, xNTAREA_PRESUPUESTAL_CODIGO, 
            xCUBIGEO_CODIGO, xNPROGRAMACION_MULTIANUAL_IMPORTE_B, xNPROGRAMACION_MULTIANUAL_META_FISICA_B, 'PE',
            xVUSUARIO_CODIGO, SYSDATE(), xVUSUARIO_CODIGO, SYSDATE());

            INSERT INTO IAFAS_PROGRAMACION_MULTIANUAL_GASTO(CPERIODO_CODIGO, CANIO_REGISTRO, NFUENTE_FINANCIAMIENTO_CODIGO, NTAREA_PRESUPUESTAL_CODIGO, 
            CUBIGEO_CODIGO, NPROGRAMACION_MULTIANUAL_IMPORTE, NPROGRAMACION_MULTIANUAL_META_FISICA,  CESTADO_CODIGO,
            VUSUARIO_CREADOR, DUSUARIO_CREADOR, VUSUARIO_CODIGO, DUSUARIO_FECHA)     
            VALUES(xCPERIODO_CODIGO, CAST(xCPERIODO_CODIGO+2 AS SIGNED), xNFUENTE_FINANCIAMIENTO_CODIGO, xNTAREA_PRESUPUESTAL_CODIGO, 
            xCUBIGEO_CODIGO, xNPROGRAMACION_MULTIANUAL_IMPORTE_C, xNPROGRAMACION_MULTIANUAL_META_FISICA_C, 'PE',
            xVUSUARIO_CODIGO, SYSDATE(), xVUSUARIO_CODIGO, SYSDATE());
            SET CODIGO_RESPUESTA  = '0';
			SET MENSAJE_RESPUESTA = 'GRABACION EXITOSA!';
        ELSE
            #SIGNAL SQLSTATE  '45000'
			#SET MESSAGE_TEXT= 'NO EXISTE REGISTRO DE DESEE ACTUALIZAR/ELIMINAR. VERIFICAR!!.',
             SET CODIGO_RESPUESTA  = '1';
			SET MENSAJE_RESPUESTA = 'NO EXISTE REGISTRO DE DESEE ACTUALIZAR/ELIMINAR. VERIFICAR!!.';
			#MYSQL_ERRNO = 1643;
        END IF;
    ELSE 
        SELECT DISTINCT CESTADO_CODIGO INTO cEstado 
        FROM IAFAS_PROGRAMACION_MULTIANUAL_GASTO WHERE 
        CPERIODO_CODIGO=xCPERIODO_CODIGO AND
		NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
		NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO;

        IF cEstado='CE' AND xTIPO!='A' THEN
            SIGNAL SQLSTATE  '45000'
			SET MESSAGE_TEXT= 'ESTADO CERRADO, NO PUEDE MODIFICAR/ELIMINAR. VERIFICAR!!.',
			MYSQL_ERRNO = 1643;
        END IF;

        IF  xTIPO = 'F' THEN    
            UPDATE IAFAS_PROGRAMACION_MULTIANUAL_GASTO SET 
            CESTADO_CODIGO='CE',
            VUSUARIO_CODIGO=xVUSUARIO_CODIGO,
            DUSUARIO_FECHA=SYSDATE() WHERE       
            CPERIODO_CODIGO=xCPERIODO_CODIGO AND
			NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
			NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO;  
        ELSE 
            IF xTIPO = 'U' THEN                
                UPDATE IAFAS_PROGRAMACION_MULTIANUAL_GASTO SET 
                NPROGRAMACION_MULTIANUAL_IMPORTE=xNPROGRAMACION_MULTIANUAL_IMPORTE_A,
                NPROGRAMACION_MULTIANUAL_META_FISICA=xNPROGRAMACION_MULTIANUAL_META_FISICA_A,
                CUBIGEO_CODIGO=xCUBIGEO_CODIGO,
                VUSUARIO_CODIGO=xVUSUARIO_CODIGO,
                DUSUARIO_FECHA=SYSDATE() WHERE 
                CPERIODO_CODIGO=xCPERIODO_CODIGO AND
                CANIO_REGISTRO=CAST(xCPERIODO_CODIGO AS SIGNED) AND  
				NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
				NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO;  

                UPDATE IAFAS_PROGRAMACION_MULTIANUAL_GASTO SET 
                NPROGRAMACION_MULTIANUAL_IMPORTE=xNPROGRAMACION_MULTIANUAL_IMPORTE_B,
                NPROGRAMACION_MULTIANUAL_META_FISICA=xNPROGRAMACION_MULTIANUAL_META_FISICA_B,
                CUBIGEO_CODIGO=xCUBIGEO_CODIGO,
                VUSUARIO_CODIGO=xVUSUARIO_CODIGO,
                DUSUARIO_FECHA=SYSDATE() WHERE 
                CPERIODO_CODIGO=xCPERIODO_CODIGO AND
                CANIO_REGISTRO=CAST(xCPERIODO_CODIGO+1 AS SIGNED) AND  
				NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
				NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO; 

                UPDATE IAFAS_PROGRAMACION_MULTIANUAL_GASTO SET 
                NPROGRAMACION_MULTIANUAL_IMPORTE=xNPROGRAMACION_MULTIANUAL_IMPORTE_C,
                NPROGRAMACION_MULTIANUAL_META_FISICA=xNPROGRAMACION_MULTIANUAL_META_FISICA_C,
                CUBIGEO_CODIGO=xCUBIGEO_CODIGO,
                VUSUARIO_CODIGO=xVUSUARIO_CODIGO,
                DUSUARIO_FECHA=SYSDATE() WHERE 
                CPERIODO_CODIGO=xCPERIODO_CODIGO AND
                CANIO_REGISTRO=CAST(xCPERIODO_CODIGO+2 AS SIGNED) AND  
				NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
				NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO; 

            ELSE
				IF xTIPO = 'D' THEN
					/*ELIMINA EL REGISTRO DEL DETALLE*/
					DELETE FROM IAFAS_PROGRAMACION_MULTIANUAL_GASTO_DETALLE WHERE
					CPERIODO_CODIGO=xCPERIODO_CODIGO AND 
					NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
					NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO; 
					
                    /*ELIMINA REGISTROS DE LA CABECERA*/
					DELETE FROM IAFAS_PROGRAMACION_MULTIANUAL_GASTO WHERE 
					CPERIODO_CODIGO=xCPERIODO_CODIGO AND 
					NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
					NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO;                       
				ELSE
					IF xTIPO ='A' THEN
						UPDATE IAFAS_PROGRAMACION_MULTIANUAL_GASTO SET 
						CESTADO_CODIGO='PE',
						VUSUARIO_CODIGO=xVUSUARIO_CODIGO,
						DUSUARIO_FECHA=SYSDATE() WHERE 
						CPERIODO_CODIGO=xCPERIODO_CODIGO AND 
						NFUENTE_FINANCIAMIENTO_CODIGO=xNFUENTE_FINANCIAMIENTO_CODIGO AND
						NTAREA_PRESUPUESTAL_CODIGO=xNTAREA_PRESUPUESTAL_CODIGO;                 
					ELSE
						SIGNAL SQLSTATE  '45000'
						SET MESSAGE_TEXT= 'NO EXISTE REGISTRO PARA MODIFICAR/ELIMINAR. VERIFICAR!!.',
						MYSQL_ERRNO = 1643;
					END IF;
				END IF;                
            END IF;
        END IF;
    END IF; 
	COMMIT;

END