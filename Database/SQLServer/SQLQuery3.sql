SELECT
    OBJECT_NAME(parent_object_id) AS Tabla,
    name AS Restriccion
FROM sys.check_constraints
ORDER BY Tabla, Restriccion;