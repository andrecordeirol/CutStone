package com.marmoraria.repository;
import com.marmoraria.entity.Pedido;
import com.marmoraria.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.status = :status")
    long countByStatus(StatusPedido status);
}
