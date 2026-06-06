package com.marmoraria.service;

import com.marmoraria.entity.Cliente;
import com.marmoraria.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repo;

    public List<Cliente> listarTodos() { return repo.findAllOrderByNome(); }
    public List<Cliente> buscarPorNome(String nome) { return repo.findByNomeContainingIgnoreCase(nome); }
    public Cliente buscarPorId(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id)); }
    @Transactional public Cliente salvar(Cliente c) { return repo.save(c); }
    @Transactional public void deletar(Long id) { repo.deleteById(id); }
    public long contarTotal() { return repo.count(); }
}
