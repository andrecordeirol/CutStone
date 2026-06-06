package com.marmoraria.repository;
import com.marmoraria.entity.Chapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChapaRepository extends JpaRepository<Chapa, Long> {
    List<Chapa> findByQuantidadeEstoqueGreaterThan(int quantidade);
    @Query("SELECT DISTINCT c.material FROM Chapa c ORDER BY c.material")
    List<String> findDistinctMateriais();
}
