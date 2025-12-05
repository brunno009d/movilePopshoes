# PopShoes – Aplicación Android

## Integrantes
* Amaro Cepeda
* Mauricio Lillo
* Brunno Rivas

## Funcionalidades
La aplicación cuenta con las siguientes características principales:

* Visualización de catálogo de calzados.
* Visualización de detalles de cada producto.
* Visualización de productos agregados al carrito.
* Registro de usuarios.
* Inicio de sesión.
* Visualización de información del usuario.


## Endpoints utilizados

### Calzados
* `GET /api/calzados`
* `GET /api/calzados/{id}`

### Usuarios
* `GET /api/usuarios/{id}`
* `POST /api/usuarios`
* `POST /api/usuarios/login`

### Compras
* "POST /api/compras"

## Pasos para ejecutar

La aplicación está configurada para conectarse automáticamente al backend desplegado en la nube.

1.  **Abrir el proyecto:**
    Clona el repositorio o descarga el código y ábrelo en Android Studio.

2.  **Iniciar:**
    Una vez que carguen las dependencias, selecciona tu emulador y presiona el botón **Run**. La app iniciará y se conectará sola.
