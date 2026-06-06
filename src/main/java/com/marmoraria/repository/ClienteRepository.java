package com.marmoraria.repository;
import com.marmoraria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    @Query("SELECT c FROM Cliente c ORDER BY c.nome ASC")
    List<Cliente> findAllOrderByNome();
}
