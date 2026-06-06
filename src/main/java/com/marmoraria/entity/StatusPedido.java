package com.marmoraria.entity;

public enum StatusPedido {
    PENDENTE("Pendente"),
    EM_PRODUCAO("Em Produção"),
    PRONTO("Pronto"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;
    StatusPedido(String d) { this.descricao = d; }
    public String getDescricao() { return descricao; }
}
