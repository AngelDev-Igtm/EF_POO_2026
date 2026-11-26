-- DEFAULT
USE IndigoLoftDB;
GO

-- 1. HUESPED: cliente no frecuente por defecto
IF NOT EXISTS (
    SELECT 1 FROM sys.default_constraints
    WHERE name = 'DF_HUESPED_ES_FRECUENTE'
)
BEGIN
    ALTER TABLE dbo.HUESPED
    ADD CONSTRAINT DF_HUESPED_ES_FRECUENTE
    DEFAULT (0) FOR esFrecuente;
END;
GO

-- 2. RESERVA: estado inicial confirmado
IF NOT EXISTS (
    SELECT 1 FROM sys.default_constraints
    WHERE name = 'DF_RESERVA_ESTADO'
)
BEGIN
    ALTER TABLE dbo.RESERVA
    ADD CONSTRAINT DF_RESERVA_ESTADO
    DEFAULT ('Confirmada') FOR estado_reserva;
END;
GO

-- 3. FACTURA: fecha de emision actual
IF NOT EXISTS (
    SELECT 1 FROM sys.default_constraints
    WHERE name = 'DF_FACTURA_FECHA_EMISION'
)
BEGIN
    ALTER TABLE dbo.FACTURA
    ADD CONSTRAINT DF_FACTURA_FECHA_EMISION
    DEFAULT (CONVERT(DATE, GETDATE())) FOR fecha_emision;
END;
GO

PRINT 'Restricciones DEFAULT configuradas correctamente.';
GO