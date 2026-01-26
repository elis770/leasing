package com.example.leasing.Repository;

import com.example.leasing.Entity.CarEntity;
import com.example.leasing.Entity.CarStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ICarRepo extends JpaRepository<CarEntity, Long> {
    
    // Buscar por marca exacta
    List<CarEntity> findByBrand(String brand);
    
    // Buscar por modelo (parcial, case-insensitive)
    List<CarEntity> findByModelContainingIgnoreCase(String model);
    
    // Buscar por estado del auto
    List<CarEntity> findByStatus(CarStatusEnum status);
    
    // Buscar autos disponibles (ACTIVE)
    @Query("SELECT c FROM CarEntity c WHERE c.status = 'ACTIVE'")
    List<CarEntity> findAvailableCars();
    
    // Buscar por marca O modelo (búsqueda flexible)
    @Query("SELECT c FROM CarEntity c WHERE " +
           "LOWER(c.brand) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(c.model) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<CarEntity> searchByBrandOrModel(@Param("searchTerm") String searchTerm);
    
    // Filtrar por rango de precios
    @Query("SELECT c FROM CarEntity c WHERE c.pricePerDay BETWEEN :minPrice AND :maxPrice")
    List<CarEntity> findByPriceRange(
        @Param("minPrice") BigDecimal minPrice, 
        @Param("maxPrice") BigDecimal maxPrice
    );
    
    // Buscar autos por dueño
    @Query("SELECT c FROM CarEntity c WHERE c.owner.id = :ownerId")
    List<CarEntity> findByOwnerId(@Param("ownerId") Long ownerId);
    
    // Buscar autos mejor valorados (promedio de rating >= 4)
    @Query("SELECT c FROM CarEntity c " +
           "LEFT JOIN c.reviews r " +
           "GROUP BY c.id " +
           "HAVING AVG(r.rating) >= 4 " +
           "ORDER BY AVG(r.rating) DESC")
    List<CarEntity> findTopRatedCars();
    
    // Buscar autos sin reservas activas (disponibles para reservar)
    @Query("SELECT c FROM CarEntity c WHERE c.id NOT IN (" +
           "SELECT r.car.id FROM ReservationEntity r " +
           "WHERE r.status = 'CONFIRMED' AND r.endDate >= CURRENT_DATE)")
    List<CarEntity> findCarsWithoutActiveReservations();
    
    // Búsqueda combinada: marca, modelo o rango de precio (cualquiera de los filtros)
    @Query("SELECT c FROM CarEntity c WHERE " +
           "(:brand IS NULL OR LOWER(c.brand) = LOWER(:brand)) AND " +
           "(:model IS NULL OR LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%'))) AND " +
           "(:minPrice IS NULL OR c.pricePerDay >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.pricePerDay <= :maxPrice) AND " +
           "(:status IS NULL OR c.status = :status)")
    List<CarEntity> searchCars(
        @Param("brand") String brand,
        @Param("model") String model,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("status") CarStatusEnum status
    );
}