package com.marmoraria.service;

import com.marmoraria.entity.Chapa;
import com.marmoraria.repository.ChapaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapaService {
    private final ChapaRepository repo;

    public List<Chapa> listarTodas() { return repo.findAll(); }
    public List<Chapa> listarComEstoque() { return repo.findByQuantidadeEstoqueGreaterThan(0); }
    public Chapa buscarPorId(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Chapa não encontrada: " + id)); }
    public List<String> listarMateriais() { return repo.findDistinctMateriais(); }
    @Transactional public Chapa salvar(Chapa c) { return repo.save(c); }
    @Transactional public void deletar(Long id) { repo.deleteById(id); }
    public long contarTotal() { return repo.count(); }
}
