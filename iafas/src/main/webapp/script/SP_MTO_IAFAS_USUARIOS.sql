CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_MTO_IAFAS_USUARIOS`(
										  IN XVUSUARIOCODIGO 	VARCHAR(20),  
										  IN XVUSUARIONOMBRES 	CHAR(200), 
                                          IN XVUSUARIOPATERO 	CHAR(200), 
                                          IN XVUSUARIOPASSWORD 	CHAR(50) ,
                                          IN XVUSUARIOCORREO		CHAR(200), 
                                          IN XVUSUARIOTELEFONO	VARCHAR(20), 
                                          IN XCESTADOCODIGO		CHAR(2), 
                                          IN XVUSUARIOMODIFICA	VARCHAR(20), 
                                          IN XDUSUARIOMODIFICA 	DATE, 
                                          IN XVUSUARIOMATERNO	VARCHAR(100),
                                          IN XVUSUARIOCARGO		VARCHAR(20), 
                                          IN XCAREALABORALCODIGO CHAR(2), 
                                          IN XVUSUARIOCREADOR 	VARCHAR(20), 
                                          IN XDUSUARIO_CREADOR 	DATE, 
                                          IN XMODO				CHAR(1)
                                          )
BEGIN
DECLARE NREG_REGISTRO INT;

SELECT 
    COUNT(*)
INTO NREG_REGISTRO FROM
    iafas_usuarios
WHERE
    VUSUARIO_CODIGO = XVUSUARIOCODIGO;

	/*MODO DE PRE - REGISTRO*/
	IF XMODO = "P" THEN 
    
            IF ( NREG_REGISTRO = 0 ) THEN 
				INSERT INTO iafas_usuarios (VUSUARIO_CODIGO, VUSUARIO_NOMBRES , VUSUARIO_PATERNO)
				VALUES (XVUSUARIOCODIGO,UPPER(XVUSUARIONOMBRES),UPPER(XVUSUARIOPATERO));
			   
				COMMIT;		 
            ELSE
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'USTED YA TIENE UNA SOLICITUD DE PRE - REGISTRO';
			END IF;   	
	END IF;            
	/* FIN MODO DE PRE - REGISTRO*/
    
    
	/*MODO DE REGISTRO*/
    IF XMODO= "I" THEN
			            
		 IF ( NREG_REGISTRO = 0 ) THEN 
			INSERT INTO iafas_usuarios (VUSUARIO_CODIGO, VUSUARIO_NOMBRES , VUSUARIO_PATERNO, VUSUARIO_MATERNO, VUSUARIO_PASSWORD, DUSUARIO_CREADOR)
			VALUES (XVUSUARIOCODIGO,UPPER(XVUSUARIONOMBRES) , UPPER(XVUSUARIOPATERO), UPPER(XVUSUARIOMATERNO),XVUSUARIOPASSWORD, SYSDATE() );
			
            COMMIT;		
		
		END IF;
	END IF;
	/* FIN MODO DE REGISTRO*/
    
    /*MODO DE UPDATE*/
    IF XMODO= "U" THEN
			            
		 IF ( NREG_REGISTRO > 0 ) THEN 
			UPDATE iafas_usuarios
            SET 
            VUSUARIO_NOMBRES =UPPER(XVUSUARIONOMBRES),
            VUSUARIO_PATERNO=UPPER(XVUSUARIOPATERO), 
			VUSUARIO_MATERNO=UPPER(XVUSUARIOMATERNO),
            VUSUARIO_PASSWORD=XVUSUARIOPASSWORD ,
            DUSUARIO_MODIFICA = SYSDATE()
            WHERE 
            VUSUARIO_CODIGO = XVUSUARIOCODIGO;
            
            COMMIT;		
		 END IF;	
	END IF;
	/* FIN MODO DE UPDATE*/
 

END