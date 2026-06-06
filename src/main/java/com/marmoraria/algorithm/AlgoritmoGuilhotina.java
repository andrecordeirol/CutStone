package com.marmoraria.algorithm;

import com.marmoraria.entity.Chapa;
import com.marmoraria.entity.ItemPedido;
import com.marmoraria.entity.PecaPositionada;
import com.marmoraria.entity.PlanoCorte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgoritmoGuilhotina {

    record Retangulo(double x, double y, double largura, double altura) {
        boolean cabe(double w, double h) { return w <= largura && h <= altura; }
    }

    record PecaParaCortar(ItemPedido item, double largura, double altura, int numero) {}

    public static class Resultado {
        public List<PecaPositionada> encaixadas = new ArrayList<>();
        public List<PecaParaCortar> naoEncaixadas = new ArrayList<>();
        public double aproveitamento;
    }

    public static Resultado executar(PlanoCorte plano, Chapa chapa, List<ItemPedido> itens) {
        List<PecaParaCortar> pecas = new ArrayList<>();
        int num = 1;
        for (ItemPedido item : itens) {
            for (int i = 0; i < item.getQuantidade(); i++) {
                pecas.add(new PecaParaCortar(item, item.getLargura(), item.getAltura(), num++));
            }
        }
        pecas.sort(Comparator.comparingDouble(p -> -(p.largura() * p.altura())));

        List<Retangulo> livres = new ArrayList<>();
        livres.add(new Retangulo(0, 0, chapa.getLargura(), chapa.getAltura()));

        Resultado resultado = new Resultado();

        for (PecaParaCortar peca : pecas) {
            boolean encaixada = false;
            for (int i = 0; i < livres.size(); i++) {
                Retangulo esp = livres.get(i);
                if (esp.cabe(peca.largura(), peca.altura())) {
                    resultado.encaixadas.add(criarPeca(plano, peca, esp, false));
                    dividir(livres, i, esp, peca.largura(), peca.altura());
                    encaixada = true;
                    break;
                }
                if (esp.cabe(peca.altura(), peca.largura())) {
                    resultado.encaixadas.add(criarPeca(plano, peca, esp, true));
                    dividir(livres, i, esp, peca.altura(), peca.largura());
                    encaixada = true;
                    break;
                }
            }
            if (!encaixada) resultado.naoEncaixadas.add(peca);
        }

        double usada = resultado.encaixadas.stream().mapToDouble(p -> p.getLargura() * p.getAltura()).sum();
        double total = chapa.getLargura() * chapa.getAltura();
        resultado.aproveitamento = total > 0 ? Math.round((usada / total) * 1000.0) / 10.0 : 0;
        return resultado;
    }

    private static PecaPositionada criarPeca(PlanoCorte plano, PecaParaCortar p, Retangulo esp, boolean rot) {
        PecaPositionada pp = new PecaPositionada();
        pp.setPlanoCorte(plano);
        pp.setItemPedido(p.item());
        pp.setX(esp.x());
        pp.setY(esp.y());
        pp.setLargura(rot ? p.altura() : p.largura());
        pp.setAltura(rot ? p.largura() : p.altura());
        pp.setRotacionada(rot);
        pp.setNumeroPeca(p.numero());
        pp.setDescricao(p.item().getDescricao() + " #" + p.numero());
        return pp;
    }

    private static void dividir(List<Retangulo> livres, int idx, Retangulo esp, double pw, double ph) {
        livres.remove(idx);
        if (esp.largura() - pw > 0.5)
            livres.add(new Retangulo(esp.x() + pw, esp.y(), esp.largura() - pw, ph));
        if (esp.altura() - ph > 0.5)
            livres.add(new Retangulo(esp.x(), esp.y() + ph, esp.largura(), esp.altura() - ph));
    }
}
