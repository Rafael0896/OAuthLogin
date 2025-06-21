# 🎓 Spring Boot OAuth Login Demo

Una aplicación web desarrollada con **Spring Boot** que implementa autenticación OAuth 2.0 con Google. Proyecto universitario que demuestra la integración de autenticación social en aplicaciones Java.

## 🚀 Características

- ✅ **Autenticación OAuth 2.0** con Google
- ✅ **API RESTful** para testing con Postman
- ✅ **Selección forzada de cuenta** de Google
- ✅ **Interfaz moderna** y responsive
- ✅ **Endpoints públicos** para testing
- ✅ **Manejo de sesiones** seguro
- ✅ **Logout completo** (local + Google)

## 🛠️ Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Security 6**
- **OAuth2 Client**
- **Thymeleaf** (Template Engine)
- **Maven** (Gestión de dependencias)
- **HTML5/CSS3/JavaScript**

## 📋 Prerrequisitos

1. **Java 17** o superior
2. **Maven 3.6+**
3. **IntelliJ IDEA** (recomendado)
4. **Cuenta de Google Cloud Platform**
5. **Credenciales OAuth de Google**

## ⚙️ Configuración del Proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Rafael0896/OAuthLogin
cd spring-oauth-login-demo
```

### 2. Configurar Google OAuth

#### Paso 1: Google Cloud Console
1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Habilita la **Google+ API**

#### Paso 2: Crear credenciales OAuth
1. Ve a **APIs y servicios** → **Credenciales**
2. Clic en **Crear credenciales** → **ID de cliente OAuth 2.0**
3. Configura:
   - **Tipo de aplicación**: Aplicación web
   - **Nombre**: Spring Boot OAuth Demo
   - **URI de redirección autorizados**: 
     ```
     http://localhost:8080/login/oauth2/code/google
     ```

#### Paso 3: Configurar application.properties
Edita `src/main/resources/application.properties`:

```properties
# Reemplaza con tus credenciales reales
spring.security.oauth2.client.registration.google.client-id=TU_GOOGLE_CLIENT_ID_AQUI
spring.security.oauth2.client.registration.google.client-secret=TU_GOOGLE_CLIENT_SECRET_AQUI
```

### 3. Ejecutar la aplicación

#### Desde IntelliJ IDEA:
1. Importa el proyecto como proyecto Maven
2. Ejecuta la clase `OAuthLoginApplication`

#### Desde línea de comandos:
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 🌐 Endpoints Disponibles

### 📱 Interfaz Web
| Endpoint | Descripción |
|----------|-------------|
| `/` | Página principal |
| `/login` | Página de login |
| `/dashboard` | Panel de usuario (requiere autenticación) |

### 🔐 Autenticación OAuth
| Endpoint | Descripción |
|----------|-------------|
| `/oauth2/authorization/google` | Login estándar Google |
| `/auth/google/select-account` | **Forzar selección de cuenta** |
| `/logout` | Cerrar sesión local |
| `/auth/logout-complete` | Logout completo (local + Google) |

### 🚀 API REST (Para Postman)

#### Endpoints Públicos
```http
GET /api/health                 # Health check
GET /api/system/info            # Información del sistema
GET /api/auth/login-url         # URLs de autenticación disponibles
```

#### Endpoints de Autenticación
```http
GET /api/auth/status            # Estado de autenticación
GET /api/user/profile           # Perfil del usuario
POST /api/auth/logout           # Cerrar sesión via API
```

## 🧪 Testing con Postman

### Configuración Rápida

1. **Importa la colección** (opcional):
   ```json
   {
     "name": "Spring OAuth Demo",
     "requests": [
       {
         "name": "Health Check",
         "method": "GET",
         "url": "http://localhost:8080/api/health"
       },
       {
         "name": "System Info",
         "method": "GET", 
         "url": "http://localhost:8080/api/system/info"
       },
       {
         "name": "Auth Status",
         "method": "GET",
         "url": "http://localhost:8080/api/auth/status"
       },
       {
         "name": "User Profile",
         "method": "GET",
         "url": "http://localhost:8080/api/user/profile"
       }
     ]
   }
   ```

### Workflow de Testing

1. **Probar endpoints públicos**:
   ```bash
   GET http://localhost:8080/api/health
   GET http://localhost:8080/api/system/info
   ```

2. **Autenticarse en el navegador**:
   - Ve a `http://localhost:8080/login`
   - Inicia sesión con Google

3. **Probar endpoints autenticados**:
   ```bash
   GET http://localhost:8080/api/auth/status
   GET http://localhost:8080/api/user/profile
   ```

### 🔄 Solución: Cambiar de Cuenta Google

**Problema**: Google OAuth siempre usa la misma cuenta.

**Solución**: Usa el endpoint especial:
```
http://localhost:8080/auth/google/select-account
```

O haz clic en **"Elegir otra cuenta de Google"** en la página de login.

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/universidad/oauthlogin/
│   │   ├── OAuthLoginApplication.java      # Clase principal
│   │   ├── config/
│   │   │   └── SecurityConfig.java         # Configuración seguridad
│   │   └── controller/
│   │       ├── WebController.java          # Controlador web
│   │       ├── ApiController.java          # API REST
│   │       └── OAuthController.java        # OAuth personalizado
│   └── resources/
|       |──  static
|       |   ├──Styles.css 
│       ├── application.properties          # Configuración
│       └── templates/                      # Vistas HTML
│           ├── index.html
│           ├── login.html
│           └── dashboard.html
```

## 🔒 Configuración de Seguridad

### Endpoints Públicos (No requieren autenticación)
- Páginas estáticas (`/`, `/login`, `/error`)
- API de testing (`/api/health`, `/api/system/info`)
- Recursos estáticos (`/css/**`, `/js/**`, `/images/**`)

### Endpoints Protegidos
- Dashboard (`/dashboard`) - Solo accesible después del login

### Características de Seguridad
- **CSRF Protection** habilitado para formularios
- **Session Management** configurado
- **OAuth2 Login** con Google
- **Logout personalizado** con limpieza de sesión

## 🎨 Características de la Interfaz

- **Diseño moderno** con gradientes y animaciones
- **Responsive design** para móviles y desktop
- **Iconografía universitaria** 
- **Mensajes de estado** (error, éxito, info)
- **Información de debugging** para desarrolladores

## ⚠️ Problemas Comunes y Soluciones

### Error: "Client ID no válido"
```
Solución: Verifica que las credenciales en application.properties sean correctas
```

### Error: "Redirect URI mismatch"
```
Solución: Asegúrate que la URI en Google Cloud Console sea exactamente:
http://localhost:8080/login/oauth2/code/google
```

### No puedo cambiar de cuenta Google
```
Solución: Usa el enlace "Elegir otra cuenta de Google" o ve a:
http://localhost:8080/auth/google/select-account
```

### Endpoints no funcionan en Postman
```
Solución: Todos los endpoints API son públicos. Si no funcionan, verifica:
1. Que la aplicación esté corriendo en puerto 8080
2. Que uses las URLs correctas (http://localhost:8080/api/...)
```

## 🚀 Despliegue

### Para entorno de desarrollo:
```bash
mvn spring-boot:run
```

### Para generar JAR:
```bash
mvn clean package
java -jar target/oauth-login-demo-1.0.0.jar
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍🎓 Autor

**Proyecto Universitario** - Desarrollo de Software Full Stack
- Spring Boot + OAuth 2.0 Integration
- Desarrollado con fines educativos

## 📞 Soporte

Si tienes problemas o preguntas:

1. **Revisa la sección de problemas comunes** arriba
2. **Verifica la configuración** de Google OAuth
3. **Consulta los logs** de la aplicación para errores específicos
4. **Crea un issue** en GitHub si el problema persiste

---

⭐ **¡No olvides dar una estrella al proyecto si te fue útil!** ⭐
