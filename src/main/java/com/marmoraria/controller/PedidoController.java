package com.marmoraria.controller;

import com.marmoraria.entity.*;
import com.marmoraria.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService service;
    private final ClienteService clienteService;
    private final ChapaService chapaService;
    private final PlanoCorteService planoService;

    @GetMapping
    public String listar(@RequestParam(required = false) String status, Model model) {
        if (status != null && !status.isBlank()) {
            try { model.addAttribute("pedidos", service.listarPorStatus(StatusPedido.valueOf(status)));
            } catch (Exception e) { model.addAttribute("pedidos", service.listarTodos()); }
            model.addAttribute("filtroStatus", status);
        } else { model.addAttribute("pedidos", service.listarTodos()); }
        model.addAttribute("statusList", StatusPedido.values());
        return "pedidos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Novo Pedido");
        return "pedidos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", service.buscarPorId(id));
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Editar Pedido");
        return "pedidos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Pedido pedido, BindingResult result,
                         Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("titulo", pedido.getId() == null ? "Novo Pedido" : "Editar Pedido");
            return "pedidos/formulario";
        }
        service.salvar(pedido);
        ra.addFlashAttribute("sucesso", "Pedido salvo!");
        return "redirect:/pedidos";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        Pedido pedido = service.buscarPorId(id);
        model.addAttribute("pedido", pedido);
        model.addAttribute("novoItem", new ItemPedido());
        model.addAttribute("chapas", chapaService.listarComEstoque());
        model.addAttribute("planos", planoService.listarPorPedido(id));
        model.addAttribute("statusList", StatusPedido.values());
        return "pedidos/detalhe";
    }

    @PostMapping("/{id}/itens/adicionar")
    public String adicionarItem(@PathVariable Long id, @ModelAttribute ItemPedido item, RedirectAttributes ra) {
        try { service.adicionarItem(id, item); ra.addFlashAttribute("sucesso", "Peça adicionada!");
        } catch (Exception e) { ra.addFlashAttribute("erro", "Erro: " + e.getMessage()); }
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/itens/{itemId}/remover")
    public String removerItem(@PathVariable Long itemId, @RequestParam Long pedidoId, RedirectAttributes ra) {
        service.removerItem(itemId);
        ra.addFlashAttribute("sucesso", "Peça removida!");
        return "redirect:/pedidos/" + pedidoId;
    }

    @PostMapping("/{id}/status")
    public String status(@PathVariable Long id, @RequestParam String status, RedirectAttributes ra) {
        service.atualizarStatus(id, StatusPedido.valueOf(status));
        ra.addFlashAttribute("sucesso", "Status atualizado!");
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/{id}/gerar-plano")
    public String gerarPlano(@PathVariable Long id, @RequestParam Long chapaId, RedirectAttributes ra) {
        try {
            var plano = planoService.gerarPlano(id, chapaId);
            ra.addFlashAttribute("sucesso", String.format("Plano gerado! Aproveitamento: %.1f%%", plano.getAproveitamento()));
            return "redirect:/planos/" + plano.getId();
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao gerar plano: " + e.getMessage());
            return "redirect:/pedidos/" + id;
        }
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        try { service.deletar(id); ra.addFlashAttribute("sucesso", "Pedido removido!");
        } catch (Exception e) { ra.addFlashAttribute("erro", "Não foi possível remover."); }
        return "redirect:/pedidos";
    }
}
