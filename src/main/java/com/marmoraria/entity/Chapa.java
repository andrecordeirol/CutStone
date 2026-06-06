package com.marmoraria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "chapas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Material é obrigatório")
    @Column(nullable = false)
    private String material;

    @NotBlank(message = "Cor é obrigatória")
    private String cor;

    @NotNull(message = "Largura é obrigatória")
    @Min(value = 1, message = "Largura deve ser maior que zero")
    @Column(nullable = false)
    private Double largura;

    @NotNull(message = "Altura é obrigatória")
    @Min(value = 1, message = "Altura deve ser maior que zero")
    @Column(nullable = false)
    private Double altura;

    private Double espessura;

    @Column(nullable = false)
    private Integer quantidadeEstoque = 0;

    private Double precoPorM2;

    @Column(length = 500)
    private String observacoes;

    public double getAreaM2() {
        return (largura / 100.0) * (altura / 100.0);
    }
}
