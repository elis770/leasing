# 🏗️ Arquitectura del Proyecto - Service Layer Pattern

## ✅ ARQUITECTURA CORRECTA

```
Controller → Service → Repository → Database
```

**SIEMPRE usa Service como capa intermedia, incluso para CRUD básico.**

---

## 🎯 ¿Por qué SIEMPRE usar Service (incluso para CRUD)?

### **Problema: Controller → Repository directamente**

```java
@RestController
public class ClientController {
    private final IClientRepo clientRepo;  // ❌ Acoplamiento directo a BD
    
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Long id) {
        return clientRepo.findById(id)  // ❌ Difícil de testear
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

**Problemas:**
1. ❌ **Tests lentos**: Necesitas BD real o mockear JPA
2. ❌ **Acoplamiento**: Controller depende directamente de JPA
3. ❌ **Difícil agregar lógica**: Si necesitas validar algo después, rompes el Controller
4. ❌ **Inconsistencia**: Algunos endpoints usan Service, otros Repository

---

### **Solución: Controller → Service → Repository**

```java
@RestController
public class ClientController {
    private final ClientService clientService;  // ✅ Desacoplado de BD
    
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));  // ✅ Fácil de testear
    }
}
```

**Ventajas:**
1. ✅ **Tests rápidos**: Mockeas Service (sin BD)
2. ✅ **Desacoplamiento**: Controller no sabe nada de JPA
3. ✅ **Fácil evolucionar**: Agregas lógica en Service sin tocar Controller
4. ✅ **Consistencia**: Todo pasa por Service
5. ✅ **Manejo de excepciones centralizado**: Service lanza excepciones personalizadas

---

## 📝 Ejemplo Completo: ClientService

```java
@Service
@RequiredArgsConstructor
public class ClientService {

    private final IClientRepo clientRepo;
    private final IUserRepo userRepo;

    // ==========================================
    // CRUD BÁSICO (con manejo de excepciones)
    // ==========================================

    public ClientEntity findById(Long id) {
        // ✅ Service maneja la excepción (no Controller)
        return clientRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    public List<ClientEntity> findAll() {
        return clientRepo.findAll();
    }

    public ClientEntity save(ClientEntity client) {
        // ✅ Puedes agregar validaciones aquí después
        validateClient(client);
        return clientRepo.save(client);
    }

    public void deleteById(Long id) {
        // ✅ Service valida que existe antes de eliminar
        if (!clientRepo.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepo.deleteById(id);
    }

    // ==========================================
    // MÉTODOS ESPECÍFICOS (con lógica de negocio)
    // ==========================================

    public ClientEntity getClientByEmail(String email) {
        UserEntity user = userRepo.findByEmailWithClient(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));

        if (user.getRole() != UserRoleEnum.CLIENT) {
            throw new BusinessValidationException("User is not a client");
        }

        return user.getClient();
    }

    public ClientEntity update(Long id, ClientEntity updatedData) {
        ClientEntity existing = findById(id);
        
        // Lógica de actualización
        if (updatedData.getFirstName() != null) {
            existing.setFirstName(updatedData.getFirstName());
        }
        
        return clientRepo.save(existing);
    }

    // ==========================================
    // VALIDACIONES PRIVADAS
    // ==========================================

    private void validateClient(ClientEntity client) {
        if (client.getFirstName() == null || client.getFirstName().isEmpty()) {
            throw new BusinessValidationException("First name is required");
        }
    }
}
```

---

## 📝 Controller Simplificado

```java
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;  // ✅ Solo Service

    // CRUD básico - delega a Service
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping
    public ResponseEntity<ClientEntity> create(@RequestBody ClientEntity client) {
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> update(
            @PathVariable Long id,
            @RequestBody ClientEntity client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos específicos - delega a Service
    @GetMapping("/search")
    public ResponseEntity<ClientEntity> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }
}
```

**Ventaja:** Controller súper limpio, solo delega. Toda la lógica está en Service.

---

## 🧪 Testing: La Razón Principal

### ❌ **Sin Service (Controller → Repository):**

```java
@WebMvcTest(ClientController.class)
class ClientControllerTest {
    
    @MockBean
    private IClientRepo clientRepo;  // ❌ Mockear JPA es complicado
    
    @Test
    void testGetById() {
        // ❌ Necesitas configurar muchos mocks de JPA
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        
        // Test...
    }
}
```

**Problemas:**
- Mockear JPA es complejo
- Tests frágiles (si cambias JPA, rompes tests)
- Tests lentos si usas BD real

---

### ✅ **Con Service (Controller → Service):**

```java
@WebMvcTest(ClientController.class)
class ClientControllerTest {
    
    @MockBean
    private ClientService clientService;  // ✅ Mockear Service es fácil
    
    @Test
    void testGetById() {
        // ✅ Simple: Service devuelve un Client
        when(clientService.findById(1L)).thenReturn(client);
        
        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }
    
    @Test
    void testGetByIdNotFound() {
        // ✅ Fácil testear excepciones
        when(clientService.findById(999L))
                .thenThrow(new ResourceNotFoundException("Client", 999L));
        
        mockMvc.perform(get("/api/clients/999"))
                .andExpect(status().isNotFound());
    }
}
```

**Ventajas:**
- ✅ Test rápido (sin BD)
- ✅ Fácil de mockear
- ✅ Test unitario puro del Controller

---

## 📊 Comparación: Con vs Sin Service

| Aspecto | Controller → Repository | Controller → Service → Repository |
|---------|------------------------|-----------------------------------|
| **Acoplamiento** | ❌ Alto (JPA en Controller) | ✅ Bajo (Controller no sabe de BD) |
| **Testing** | ❌ Difícil (mockear JPA) | ✅ Fácil (mockear Service) |
| **Velocidad tests** | ❌ Lento (BD real o mocks complejos) | ✅ Rápido (sin BD) |
| **Agregar lógica** | ❌ Rompes Controller | ✅ Solo tocas Service |
| **Excepciones** | ❌ Manejo en Controller | ✅ Manejo en Service |
| **Reutilización** | ❌ Duplicas lógica | ✅ Reutilizas Service |
| **Código Controller** | ❌ Complejo | ✅ Simple (solo delega) |

---

## 🎯 Regla de Diseño

### **Service tiene DOS tipos de métodos:**

#### **1. CRUD con manejo de excepciones:**
```java
public ClientEntity findById(Long id) {
    return clientRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Client", id));
}
```
**Valor:** Manejo consistente de excepciones

#### **2. Lógica de negocio:**
```java
public ClientEntity getClientByEmail(String email) {
    // Busca User, valida role, extrae Client
    // Múltiples validaciones y transformaciones
}
```
**Valor:** Encapsula lógica compleja

---

## ✅ Resumen Final

### **SIEMPRE usa Service, incluso para CRUD básico porque:**

1. ✅ **Tests unitarios rápidos** (mockeas Service, no BD)
2. ✅ **Puedes agregar validaciones después** sin romper Controller
3. ✅ **Manejo de excepciones centralizado** (Service lanza, GlobalExceptionHandler captura)
4. ✅ **Controller más simple** (solo delega, sin lógica)
5. ✅ **Desacoplamiento** (Controller no sabe nada de JPA/BD)
6. ✅ **Consistencia** (todo pasa por Service, no mezclas enfoques)

### **Estructura de Service:**

```java
@Service
public class ClientService {
    
    // CRUD básico + manejo de excepciones
    public ClientEntity findById(Long id) { ... }
    public List<ClientEntity> findAll() { ... }
    public ClientEntity save(ClientEntity client) { ... }
    public void deleteById(Long id) { ... }
    
    // Métodos específicos con lógica de negocio
    public ClientEntity getClientByEmail(String email) { ... }
    public ClientEntity update(Long id, ClientEntity data) { ... }
    
    // Validaciones privadas
    private void validateClient(ClientEntity client) { ... }
}
```

---

**Última actualización:** Enero 2026
