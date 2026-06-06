package com.marmoraria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planos_corte")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanoCorte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chapa_id", nullable = false)
    private Chapa chapa;

    @Column(nullable = false)
    private Double aproveitamento = 0.0;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private Integer totalPecasEncaixadas = 0;
    private Integer totalPecasNaoEncaixadas = 0;

    @OneToMany(mappedBy = "planoCorte", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PecaPositionada> pecasPositionadas = new ArrayList<>();
}
