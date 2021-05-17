DELIMITER $$

ALTER ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_consultacertificadoanual` AS 
SELECT
  `iafas_certificado_presupuestal`.`CPERIODO_CODIGO`               AS `vanoDocumento`,
  '001'                                                            AS `vsecuenciaA`,
  '001'                                                            AS `vcorrelativoA`,
  'CERTIFICACION'                                                  AS `tipoCertificacion`,
  (CASE `iafas_certificado_presupuestal`.`NFUENTE_FINANCIAMIENTO_CODIGO` WHEN 1 THEN '00' WHEN 2 THEN '09' ELSE '' END) AS `vfuenteFinanciamiento`,
  `iafas_certificado_presupuestal`.`NFUENTE_FINANCIAMIENTO_CODIGO` AS `vcodTipoFinanciamiento`,
  `PK_UTIL_PRESUPUESTO`(
`iafas_certificado_presupuestal`.`NFUENTE_FINANCIAMIENTO_CODIGO`)  AS `desPto`,
  ''                                                               AS `cproveedorRuc`,
  ''                                                               AS `razonSocial`,
  `iafas_certificado_presupuestal`.`NCERTIFICADO_CODIGO`           AS `vnroCertificado`,
  `iafas_certificado_presupuestal`.`VCERTIFICADO_DOCUMENTO`        AS `vnroDocumentoPagoA`,
  `iafas_certificado_presupuestal`.`VCERTIFICADO_CONCEPTO`         AS `vglosa`,
  `iafas_certificado_presupuestal`.`DCERTIFICADO_FECHA`            AS `dfechaDocumento`,
  (CASE `iafas_certificado_presupuestal`.`NMONEDA_CODIGO` WHEN 1 THEN 'S/.' WHEN 2 THEN 'US$' WHEN 3 THEN 'EURO' END) AS `vcodMoneda`,
  `iafas_certificado_presupuestal`.`NCERTIFICADO_TIPO_CAMBIO`      AS `ntipCam`,
  `iafas_certificado_presupuestal`.`NCERTIFICADO_IMPORTE`          AS `nimpMonSol`,
  `PK_AUX_TIPO_PROCESO_SELECCION`(
`iafas_certificado_presupuestal`.`NPAC_PROCESOS_CODIGO`)  AS `vcodProcesoSel`
FROM `iafas_certificado_presupuestal`
WHERE ((`iafas_certificado_presupuestal`.`CESTADO_CODIGO` = 'AP')
       AND (`iafas_certificado_presupuestal`.`CCERTIFICADO_TIPO_REGISTRO` = 'CE'))UNION SELECT
                                                                                          `iafas_compromiso_anual`.`VANO_DOCUMENTO`                         AS `vanoDocumento`,
                                                                                          `iafas_compromiso_anual`.`VSECUENCIA_A`                           AS `vsecuenciaA`,
                                                                                          `iafas_compromiso_anual`.`VCORRELATIVO_A`                         AS `vcorrelativoA`,
                                                                                          'COMPROMISO ANUAL'                                                AS `tipoCertificacion`,
                                                                                          `iafas_compromiso_anual`.`VFUENTE_FINANCIAMIENTO`                 AS `vfuenteFinanciamiento`,
                                                                                          `iafas_compromiso_anual`.`VCOD_TIPO_FINANCIAMIENTO`               AS `vcodTipoFinanciamiento`,
                                                                                          `PK_UTIL_PRESUPUESTO`(
`iafas_compromiso_anual`.`VCOD_TIPO_FINANCIAMIENTO`)  AS `desPto`,
                                                                                          `iafas_compromiso_anual`.`CPROVEEDOR_RUC`                         AS `cproveedorRuc`,
                                                                                          `PK_UTIL_RAZON_SOCIAL`(
`iafas_compromiso_anual`.`CPROVEEDOR_RUC`)  AS `razonSocial`,
                                                                                          `iafas_compromiso_anual`.`VNRO_CERTIFICADO`                       AS `vnroCertificado`,
                                                                                          CONCAT(`iafas_compromiso_anual`.`VTIPO_DOCUMENTO_A`,'-',`iafas_compromiso_anual`.`VNRO_DOCUMENTO_PAGO_A`) AS `vnroDocumentoPagoA`,
                                                                                          `iafas_compromiso_anual`.`VGLOSA`                                 AS `vglosa`,
                                                                                          `iafas_compromiso_anual`.`DFECHA_DOCUMENTO_A`                     AS `dfechaDocumento`,
                                                                                          (CASE `iafas_compromiso_anual`.`VCOD_MONEDA` WHEN 1 THEN 'S/.' WHEN 2 THEN 'US$' WHEN 3 THEN 'EURO' END) AS `vcodMoneda`,
                                                                                          `iafas_compromiso_anual`.`NTIP_CAM`                               AS `ntipCam`,
                                                                                          `iafas_compromiso_anual`.`NIMP_MON_SOL`                           AS `nimpMonSol`,
                                                                                          `iafas_compromiso_anual`.`VCOD_PROCESO_SEL`                       AS `vcodProcesoSel`
                                                                                        FROM `iafas_compromiso_anual`
ORDER BY `vsecuenciaA`$$

DELIMITER ;