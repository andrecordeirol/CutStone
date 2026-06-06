package com.marmoraria.controller;

import com.marmoraria.entity.PlanoCorte;
import com.marmoraria.service.PlanoCorteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/planos")
@RequiredArgsConstructor
public class PlanoCorteController {
    private final PlanoCorteService service;

    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        PlanoCorte plano = service.buscarPorId(id);
        model.addAttribute("plano", plano);

        StringBuilder sb = new StringBuilder("[");
        var pecas = plano.getPecasPositionadas();
        for (int i = 0; i < pecas.size(); i++) {
            var p = pecas.get(i);
            if (i > 0) sb.append(",");
            sb.append(String.format(
                "{\"x\":%.2f,\"y\":%.2f,\"largura\":%.2f,\"altura\":%.2f,\"numero\":%d,\"descricao\":\"%s\",\"rotacionada\":%b}",
                p.getX(), p.getY(), p.getLargura(), p.getAltura(),
                p.getNumeroPeca(), p.getDescricao().replace("\"", "'"), p.isRotacionada()
            ));
        }
        sb.append("]");
        model.addAttribute("pecasJson", sb.toString());
        model.addAttribute("chapaLargura", plano.getChapa().getLargura());
        model.addAttribute("chapaAltura", plano.getChapa().getAltura());
        return "plano/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, @RequestParam Long pedidoId, RedirectAttributes ra) {
        service.deletar(id);
        ra.addFlashAttribute("sucesso", "Plano removido!");
        return "redirect:/pedidos/" + pedidoId;
    }
}
