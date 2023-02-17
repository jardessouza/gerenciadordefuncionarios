package com.jardessouza.desafio.funcionario.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.funcionario.enums.Sexo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_FUNCIONARIOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false, length = 255)
    private String nome;
    @Column(nullable = false)
    private Integer idade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Column(nullable = false)
    private String cep;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
