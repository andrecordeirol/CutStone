package com.marmoraria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pecas_posicionadas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PecaPositionada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_corte_id", nullable = false)
    private PlanoCorte planoCorte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_pedido_id")
    private ItemPedido itemPedido;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double largura;

    @Column(nullable = false)
    private Double altura;

    private boolean rotacionada;
    private Integer numeroPeca;
    private String descricao;
}
