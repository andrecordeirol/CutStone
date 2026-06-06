package com.marmoraria.service;

import com.marmoraria.entity.ItemPedido;
import com.marmoraria.entity.Pedido;
import com.marmoraria.entity.StatusPedido;
import com.marmoraria.repository.ItemPedidoRepository;
import com.marmoraria.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repo;
    private final ItemPedidoRepository itemRepo;

    public List<Pedido> listarTodos() { return repo.findAll(); }
    public List<Pedido> listarPorStatus(StatusPedido s) { return repo.findByStatus(s); }
    public Pedido buscarPorId(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id)); }

    @Transactional
    public Pedido salvar(Pedido p) { return repo.save(p); }

    @Transactional
    public void adicionarItem(Long pedidoId, ItemPedido item) {
        Pedido pedido = buscarPorId(pedidoId);
        item.setPedido(pedido);
        itemRepo.save(item);
    }

    @Transactional
    public void removerItem(Long itemId) { itemRepo.deleteById(itemId); }

    @Transactional
    public void atualizarStatus(Long id, StatusPedido status) {
        Pedido p = buscarPorId(id);
        p.setStatus(status);
        repo.save(p);
    }

    @Transactional public void deletar(Long id) { repo.deleteById(id); }
    public long contarPendentes() { return repo.countByStatus(StatusPedido.PENDENTE); }
    public long contarEmProducao() { return repo.countByStatus(StatusPedido.EM_PRODUCAO); }
    public long contarTotal() { return repo.count(); }
}
