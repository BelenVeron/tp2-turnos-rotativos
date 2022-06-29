# Trabajo práctico 2: Turnos rotativos

Resolución del trabajo práctico donde se cargan jornadas de trabajo
para cada empleado con ciertas validaciones.
Se entrega la carpeta del desarrollo en eclipse basado en spring
boot y con un servidor remoto de mysql.
Tambien una coleccion de postman para pruebas.


## Base de datos
Está constituida por 3 tablas:
- empleado
- tipo de jornada
- jornada
 
Las relaciones son:
- Un empleado a muchas jornadas, una jornada a un empleado
- Un tipo de jornada a muchas jornadas, una jornada a un tipo de jornada

Empleados contiene los campos:
- id
- nombre
- apellido
- dni

Tipo de jornada contiene:
- id
- tipo

Jornada contiene:
- id
- fecha
- horaEntrada
- horaSalida
- tipo_jornada_id
- empleado_id


## Organización
La organización de las carpetas en el proyecto se encuentra dividido en:
- controllers: con la definicion del path
- entities: cada entidad
- enums: en este caso para los tipos de jornada, se considera que solo
se puede agregar las que se encuentran en el enum
- services: que une los controllers con la base de datos que se encuentra
definida para los servicesImpl que los implementa
- validators: incluye las validaciones, cada metodo y en su interior esta
documentado que se está validando
- requests y responses: para mapear los requests y responses necesarios
- utils: ciertos metodos utilitarios


## Usos
Primero cargar los empleados, tipos de jornada y luego las jornadas de
cada empleado, se encuentran en las carpetas correspondientes.
Luego se realizan las validaciones que se encuentran tambien en sus
carpetas correspondientes.
Se prefiere usar el dni en las búsquedas ya que es único para cada empleado
y es mas facil para el usuario que utilizar un id de base de datos, salvo en 
las jornadas que en teoría va a aparecer una lista con la posibilida de 
editarlos..


## Autor
Belen Veronn



