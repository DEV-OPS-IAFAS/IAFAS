CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_MTO_IAFAS_USUARIOS`(
                      IN XVUSUARIOCODIGO  VARCHAR(20),  
                      IN XVUSUARIONOMBRES   CHAR(200), 
                                          IN XVUSUARIOPATERO  CHAR(200), 
                                          IN XVUSUARIOPASSWORD  CHAR(50) ,
                                          IN XVUSUARIOCORREO    CHAR(200), 
                                          IN XVUSUARIOTELEFONO  VARCHAR(20), 
                                          IN XCESTADOCODIGO   CHAR(2), 
                                          IN XVUSUARIOMODIFICA  VARCHAR(20), 
                                          IN XDUSUARIOMODIFICA  DATE, 
                                          IN XVUSUARIOMATERNO VARCHAR(100),
                                          IN XVUSUARIOCARGO   VARCHAR(20), 
                                          IN XCAREALABORALCODIGO CHAR(2), 
                                          IN XVUSUARIOCREADOR   VARCHAR(20), 
                                          IN XDUSUARIO_CREADOR  DATE, 
                                          IN XMODO        CHAR(1),
                      OUT CODIGO_RESPUESTA  CHAR(1),
                                          OUT MENSAJE_RESPUESTA VARCHAR(100)
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
        ##SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'USTED YA TIENE UNA SOLICITUD DE PRE - REGISTRO';
                SET CODIGO_RESPUESTA  = '1';
        SET MENSAJE_RESPUESTA = 'USTED YA TIENE UNA SOLICITUD DE PRE - REGISTRO';
      END IF;     
  END IF;            
  /* FIN MODO DE PRE - REGISTRO*/
    
    
  /*MODO DE REGISTRO*/
    IF XMODO= "I" THEN
                  
     IF ( NREG_REGISTRO = 0 ) THEN 
      INSERT INTO iafas_usuarios (VUSUARIO_CODIGO, VUSUARIO_NOMBRES , VUSUARIO_PATERNO, VUSUARIO_MATERNO, VUSUARIO_PASSWORD, DUSUARIO_CREADOR,CAREA_LABORAL_CODIGO, CESTADO_CODIGO )
      VALUES (XVUSUARIOCODIGO,UPPER(XVUSUARIONOMBRES) , UPPER(XVUSUARIOPATERO), UPPER(XVUSUARIOMATERNO),XVUSUARIOPASSWORD, SYSDATE(),XCAREALABORALCODIGO ,XCESTADOCODIGO );
      SET CODIGO_RESPUESTA  = '0';
            SET MENSAJE_RESPUESTA = 'GRABACION EXITOSA';
            COMMIT;   
    
         ELSE
        #SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'USTED YA TIENE UN REGISTRO';
                SET CODIGO_RESPUESTA  = '2';
        SET MENSAJE_RESPUESTA = 'USTED YA TIENE UN REGISTRO';
            
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
            DUSUARIO_MODIFICA = SYSDATE(),
      CAREA_LABORAL_CODIGO=XCAREALABORALCODIGO 
            WHERE 
            VUSUARIO_CODIGO = XVUSUARIOCODIGO;
            
            COMMIT;   
     END IF;  
  END IF;
  /* FIN MODO DE UPDATE*/
    
       /*MODO DE UPDATE*/
    IF XMODO= "D" THEN
                  
     IF ( NREG_REGISTRO > 0 ) THEN 
      UPDATE iafas_usuarios
            SET 
            CESTADO_CODIGO = XCESTADOCODIGO,
            DUSUARIO_MODIFICA = SYSDATE() 
            WHERE 
            VUSUARIO_CODIGO = XVUSUARIOCODIGO;
            
            COMMIT;   
     END IF;  
  END IF;
  /* FIN MODO DE UPDATE*/
 

END