package com.marmoraria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "itens_pedido")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Largura é obrigatória")
    @Min(value = 1, message = "Largura deve ser maior que zero")
    private Double largura;

    @NotNull(message = "Altura é obrigatória")
    @Min(value = 1, message = "Altura deve ser maior que zero")
    private Double altura;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Mínimo 1")
    private Integer quantidade;

    private String acabamento;

    @Column(length = 500)
    private String observacoes;

    public double getAreaM2() {
        return (largura * altura * quantidade) / 10000.0;
    }
}
