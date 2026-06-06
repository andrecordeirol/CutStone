package com.marmoraria.repository;
import com.marmoraria.entity.PlanoCorte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlanoCorteRepository extends JpaRepository<PlanoCorte, Long> {
    List<PlanoCorte> findByPedidoId(Long pedidoId);
}
