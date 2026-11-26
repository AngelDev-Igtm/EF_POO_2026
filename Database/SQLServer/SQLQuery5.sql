SELECT
    OBJECT_NAME(parent_object_id) AS Tabla,
    name AS Restriccion,
    definition AS ValorPredeterminado
FROM sys.default_constraints
ORDER BY Tabla;