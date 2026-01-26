package com.example.leasing.controller;

import com.example.leasing.Entity.ClientEntity;
import com.example.leasing.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para Client
 * 
 * FLUJO DE TRABAJO:
 * 1. Login → GET /api/clients/by-email/{email} → Devuelve Client con ID
 * 2. Frontend guarda el Client ID
 * 3. Operaciones → Usan Client ID: /api/clients/{id}
 * 
 * TODO: Agregar seguridad OAuth2/JWT
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // ==========================================
    // BÚSQUEDA INICIAL (por email) - Solo para Login
    // ==========================================

    /**
     * GET /api/clients/by-email/john@example.com
     * Busca un client por email de su usuario
     * 
     * USO: Solo para login/búsqueda inicial
     * Respuesta incluye el Client ID que el frontend guardará
     * 
     * TODO: Proteger con OAuth2 - Solo usuarios autenticados
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<ClientEntity> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }

    // ==========================================
    // CRUD BÁSICO (por ID) - Operaciones normales
    // ==========================================

    /**
     * GET /api/clients/{id}
     * Obtiene un client por ID
     * 
     * TODO: Proteger con OAuth2 - Solo el propio cliente o admin
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    /**
     * GET /api/clients
     * Obtiene todos los clients
     * 
     * TODO: Proteger con OAuth2 - Solo ADMIN
     */
    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    /**
     * POST /api/clients
     * Crea un nuevo client
     * 
     * NOTA: En producción, esto debería estar en /api/auth/register
     * TODO: Proteger con OAuth2 - Solo durante registro
     */
    @PostMapping
    public ResponseEntity<ClientEntity> create(@RequestBody ClientEntity client) {
        ClientEntity created = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/clients/{id}
     * Actualiza un client existente
     * 
     * TODO: Proteger con OAuth2 - Solo el propio cliente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> update(
            @PathVariable Long id,
            @RequestBody ClientEntity client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    /**
     * DELETE /api/clients/{id}
     * Elimina un client por ID
     * 
     * TODO: Proteger con OAuth2 - Solo ADMIN
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
