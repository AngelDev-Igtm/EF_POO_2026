USE IndigoLoftDB;
GO

-- 1. PERSONA: validacion de documentos
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_PERSONA_DOCUMENTO')
ALTER TABLE dbo.PERSONA
ADD CONSTRAINT CK_PERSONA_DOCUMENTO
CHECK (
    UPPER(LTRIM(RTRIM(tipo_doc))) IN ('DNI', 'CE', 'PAS')
    AND nro_doc NOT LIKE '%[^0-9]%'
    AND (
        (UPPER(LTRIM(RTRIM(tipo_doc))) = 'DNI' AND LEN(nro_doc) = 8)
        OR (UPPER(LTRIM(RTRIM(tipo_doc))) = 'CE' AND LEN(nro_doc) = 10)
        OR (UPPER(LTRIM(RTRIM(tipo_doc))) = 'PAS' AND LEN(nro_doc) = 9)
    )
);
GO

-- 2. EMPLEADO: sueldo positivo
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_EMPLEADO_SUELDO')
ALTER TABLE dbo.EMPLEADO
ADD CONSTRAINT CK_EMPLEADO_SUELDO
CHECK (sueldo > 0);
GO

-- 3. EMPLEADO: no puede ser su propio jefe
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_EMPLEADO_JEFE')
ALTER TABLE dbo.EMPLEADO
ADD CONSTRAINT CK_EMPLEADO_JEFE
CHECK (nro_doc_jefe IS NULL OR nro_doc_jefe <> nro_doc);
GO

-- 4. GERENTE: bono no negativo
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_GERENTE_BONO')
ALTER TABLE dbo.GERENTE
ADD CONSTRAINT CK_GERENTE_BONO
CHECK (bono_gestion >= 0);
GO

-- 5. ADMINISTRATIVO: anexo de seis digitos
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_ADMINISTRATIVO_ANEXO')
ALTER TABLE dbo.ADMINISTRATIVO
ADD CONSTRAINT CK_ADMINISTRATIVO_ANEXO
CHECK (LEN(nro_anexo) = 6 AND nro_anexo NOT LIKE '%[^0-9]%');
GO

-- 6. DEPARTAMENTO: presupuesto positivo
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_DEPARTAMENTO_PRESUPUESTO')
ALTER TABLE dbo.DEPARTAMENTO
ADD CONSTRAINT CK_DEPARTAMENTO_PRESUPUESTO
CHECK (presupuesto_mensual > 0);
GO

-- 7. HABITACION: numero permitido
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_HABITACION_NUMERO')
ALTER TABLE dbo.HABITACION
ADD CONSTRAINT CK_HABITACION_NUMERO
CHECK (nro_habitacion BETWEEN 100 AND 2000);
GO

-- 8. HABITACION: estados permitidos
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_HABITACION_ESTADO')
ALTER TABLE dbo.HABITACION
ADD CONSTRAINT CK_HABITACION_ESTADO
CHECK (UPPER(estado) IN ('DISPONIBLE', 'OCUPADA', 'MANTENIMIENTO', 'LIMPIEZA'));
GO

-- 9. HABITACION: precio positivo
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_HABITACION_PRECIO')
ALTER TABLE dbo.HABITACION
ADD CONSTRAINT CK_HABITACION_PRECIO
CHECK (precio_noche > 0);
GO

-- 10. RECEPCIONISTA: turnos permitidos
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_RECEPCIONISTA_TURNO')
ALTER TABLE dbo.RECEPCIONISTA
ADD CONSTRAINT CK_RECEPCIONISTA_TURNO
CHECK (LOWER(turno) IN (N'mañana', N'tarde', N'noche', N'completo'));
GO

-- 11. RESERVA: fecha final posterior a fecha inicial
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_RESERVA_FECHAS')
ALTER TABLE dbo.RESERVA
ADD CONSTRAINT CK_RESERVA_FECHAS
CHECK (fecha_fin > fecha_inicio);
GO

-- 12. RESERVA: estados permitidos
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_RESERVA_ESTADO')
ALTER TABLE dbo.RESERVA
ADD CONSTRAINT CK_RESERVA_ESTADO
CHECK (estado_reserva IN ('Confirmada', 'En curso', 'Finalizada', 'Cancelada'));
GO

-- 13. RESERVA: fecha real de salida valida
IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_RESERVA_SALIDA_REAL')
ALTER TABLE dbo.RESERVA
ADD CONSTRAINT CK_RESERVA_SALIDA_REAL
CHECK (fecha_salida_real IS NULL OR fecha_salida_real >= fecha_inicio);
GO

PRINT 'Proceso de restricciones CHECK finalizado.';
GO