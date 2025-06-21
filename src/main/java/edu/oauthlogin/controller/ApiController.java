package edu.oauthlogin.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * Endpoint para verificar el estado de autenticación
     * GET /api/auth/status
     */
    @GetMapping("/auth/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        if (principal != null) {
            response.put("authenticated", true);
            response.put("name", principal.getAttribute("name"));
            response.put("email", principal.getAttribute("email"));
            response.put("picture", principal.getAttribute("picture"));
            response.put("attributes", principal.getAttributes());
        } else {
            response.put("authenticated", false);
            response.put("message", "Usuario no autenticado");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener información del usuario autenticado
     * GET /api/user/profile
     */
    @GetMapping("/user/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        if (principal == null) {
            response.put("authenticated", false);
            response.put("error", "No autenticado");
            response.put("message", "Debe autenticarse primero con Google OAuth");
            response.put("loginUrl", "/oauth2/authorization/google");
            response.put("selectAccountUrl", "/auth/google/select-account");
            return ResponseEntity.status(200).body(response); // Cambiado a 200 para facilitar testing
        }

        response.put("authenticated", true);
        response.put("userInfo", Map.of(
                "id", principal.getAttribute("sub"),
                "name", principal.getAttribute("name"),
                "email", principal.getAttribute("email"),
                "picture", principal.getAttribute("picture"),
                "givenName", principal.getAttribute("given_name"),
                "familyName", principal.getAttribute("family_name"),
                "locale", principal.getAttribute("locale"),
                "emailVerified", principal.getAttribute("email_verified")
        ));

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para cerrar sesión
     * POST /api/auth/logout
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sesión cerrada exitosamente");
        response.put("redirectUrl", "/login");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint público para verificar que la API está funcionando
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "API funcionando correctamente");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener la URL de login personalizada
     * GET /api/auth/login-url
     */
    @GetMapping("/auth/login-url")
    public ResponseEntity<Map<String, String>> getLoginUrl() {
        Map<String, String> response = new HashMap<>();
        // URL que fuerza la selección de cuenta
        response.put("loginUrl", "/oauth2/authorization/google");
        response.put("selectAccountUrl", "/auth/google/select-account");
        response.put("logoutUrl", "/logout");
        response.put("completeLogoutUrl", "/auth/logout-complete");
        response.put("message", "URLs disponibles para autenticación");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para testing - información completa del sistema
     * GET /api/system/info
     */
    @GetMapping("/system/info")
    public ResponseEntity<Map<String, Object>> getSystemInfo(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        // Información del sistema
        response.put("serverTime", System.currentTimeMillis());
        response.put("serverPort", "8080");
        response.put("environment", "development");

        // Estado de autenticación
        Map<String, Object> authStatus = new HashMap<>();
        authStatus.put("isAuthenticated", principal != null);
        if (principal != null) {
            authStatus.put("username", principal.getAttribute("name"));
            authStatus.put("email", principal.getAttribute("email"));
        }
        response.put("authentication", authStatus);

        // Endpoints disponibles
        response.put("availableEndpoints", Map.of(
                "public", "/api/health, /api/auth/login-url, /api/system/info",
                "authentication", "/api/auth/status, /api/user/profile, /api/auth/logout",
                "oauth", "/oauth2/authorization/google, /auth/google/select-account"
        ));

        return ResponseEntity.ok(response);
    }
}