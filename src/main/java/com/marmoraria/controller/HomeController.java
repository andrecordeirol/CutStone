package com.marmoraria.controller;

import com.marmoraria.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ClienteService clienteService;
    private final ChapaService chapaService;
    private final PedidoService pedidoService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalClientes", clienteService.contarTotal());
        model.addAttribute("totalChapas", chapaService.contarTotal());
        model.addAttribute("totalPedidos", pedidoService.contarTotal());
        model.addAttribute("pedidosPendentes", pedidoService.contarPendentes());
        model.addAttribute("pedidosEmProducao", pedidoService.contarEmProducao());
        model.addAttribute("ultimosPedidos", pedidoService.listarTodos().stream().limit(5).toList());
        return "index";
    }
}
