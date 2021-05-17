DELIMITER $$

USE `iafas`$$

DROP PROCEDURE IF EXISTS `SP_REGISTRO_COMP_ANUAL`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_REGISTRO_COMP_ANUAL`(
    IN xvano_documento VARCHAR(4),
    IN xvnro_certificado VARCHAR(10),
    IN xvtipo_documento_a VARCHAR(8),
    IN xvnro_documento_pago_a VARCHAR(6),
    IN xdfecha_documento DATE,
    IN xvtipo_movimiento VARCHAR(1),
    IN xvtipo_operacion VARCHAR(2),
    IN xvfuente_financiamiento VARCHAR(2),
    IN xcproveedor_ruc VARCHAR(11),
    IN xntip_cam  DECIMAL(13,2),
    IN xvcod_tipo_financiamiento VARCHAR(1),
    IN xvcod_proceso_sel VARCHAR(2),
    IN xvcod_moneda VARCHAR(3),
    IN xnimp_mon_sol DECIMAL(13,2),
    IN xvusuario_ing VARCHAR(10),
    IN xvglosa VARCHAR(500))
BEGIN
      DECLARE secuencia VARCHAR(3);
      DECLARE correlativo VARCHAR(3);
      declare cantidadRUC INTEGER;
      
    -- Generando secuencia por certificado
    SELECT IFNULL(LPAD(MAX(vsecuencia_a+1),3,0),'002') INTO secuencia
    FROM iafas_compromiso_anual
    WHERE vano_documento = xvano_documento AND
    vnro_certificado = xvnro_certificado;
    
    -- Generando correlativo por secuencia y certificado
    SELECT IFNULL(LPAD(MAX(vcorrelativo_a+1),3,0),'001') INTO correlativo
    FROM iafas_compromiso_anual
    WHERE vano_documento = xvano_documento AND
    vnro_certificado = xvnro_certificado AND
    vsecuencia_a = secuencia;
    
    select count(*) into cantidadRUC
    from iafas_proveedores
    where cproveedor_ruc = xcproveedor_ruc;
    
    IF cantidadRUC = 0 then
    INSERT INTO iafas_proveedores(cproveedor_ruc)
    VALUES(xcproveedor_ruc);
    COMMIT;
    end if;
    

        INSERT INTO iafas_compromiso_anual(vano_documento,vsecuencia_a,vcorrelativo_a,vnro_certificado,
    vtipo_documento_a,vnro_documento_pago_a,dfecha_documento_a,vtipo_movimiento,vtipo_operacion,
    vfuente_financiamiento,cproveedor_ruc,ntip_cam,vcod_tipo_financiamiento,vcod_proceso_sel,
    vcod_moneda,nimp_mon_sol,vusuario_ing,vglosa,dfecha_ing,vcod_estado)
    VALUES(xvano_documento, secuencia, correlativo, xvnro_certificado,xvtipo_documento_a,
    substr(xvnro_documento_pago_a,3,10),
    xdfecha_documento,xvtipo_movimiento,xvtipo_operacion,xvfuente_financiamiento,xcproveedor_ruc,xntip_cam,
    xvcod_tipo_financiamiento, xvcod_proceso_sel,xvcod_moneda,xnimp_mon_sol,xvusuario_ing,xvglosa,NOW(),1);
    
   
    COMMIT;
END$$

DELIMITER ;