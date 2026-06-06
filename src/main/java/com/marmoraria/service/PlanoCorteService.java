package com.marmoraria.service;

import com.marmoraria.algorithm.AlgoritmoGuilhotina;
import com.marmoraria.entity.*;
import com.marmoraria.repository.PlanoCorteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoCorteService {
    private final PlanoCorteRepository repo;
    private final PedidoService pedidoService;
    private final ChapaService chapaService;

    @Transactional
    public PlanoCorte gerarPlano(Long pedidoId, Long chapaId) {
        Pedido pedido = pedidoService.buscarPorId(pedidoId);
        Chapa chapa = chapaService.buscarPorId(chapaId);

        List<ItemPedido> itens = pedido.getItens();
        if (itens == null || itens.isEmpty())
            throw new RuntimeException("O pedido não possui itens para corte.");

        PlanoCorte plano = new PlanoCorte();
        plano.setPedido(pedido);
        plano.setChapa(chapa);
        plano = repo.save(plano);

        AlgoritmoGuilhotina.Resultado resultado = AlgoritmoGuilhotina.executar(plano, chapa, itens);

        PlanoCorte finalPlano = plano;
        resultado.encaixadas.forEach(p -> p.setPlanoCorte(finalPlano));

        plano.setPecasPositionadas(resultado.encaixadas);
        plano.setAproveitamento(resultado.aproveitamento);
        plano.setTotalPecasEncaixadas(resultado.encaixadas.size());
        plano.setTotalPecasNaoEncaixadas(resultado.naoEncaixadas.size());

        return repo.save(plano);
    }

    public PlanoCorte buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Plano não encontrado: " + id));
    }

    public List<PlanoCorte> listarPorPedido(Long pedidoId) { return repo.findByPedidoId(pedidoId); }

    @Transactional public void deletar(Long id) { repo.deleteById(id); }
}
