-- Ejecutar con autenticacion de Windows y permisos administrativos.
-- Antes, crear el inicio de sesion SQL indigo_app desde SSMS con una clave propia.
-- No publicar la clave. Habilitar autenticacion mixta y TCP/IP si corresponde.
USE [IndigoLoftDB];
GO
IF SUSER_ID(N'indigo_app') IS NULL
    THROW 50001, 'Primero cree el inicio de sesion indigo_app en SSMS.', 1;
GO
IF SUSER_ID(N'indigo_app') IS NOT NULL
BEGIN
    IF DATABASE_PRINCIPAL_ID(N'indigo_app') IS NULL
        CREATE USER [indigo_app] FOR LOGIN [indigo_app] WITH DEFAULT_SCHEMA = [dbo];
    ELSE
        ALTER USER [indigo_app] WITH LOGIN = [indigo_app];
    IF IS_ROLEMEMBER(N'db_datareader', N'indigo_app') = 0
        ALTER ROLE [db_datareader] ADD MEMBER [indigo_app];
    IF IS_ROLEMEMBER(N'db_datawriter', N'indigo_app') = 0
        ALTER ROLE [db_datawriter] ADD MEMBER [indigo_app];
END;
GO
