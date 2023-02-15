package com.jardessouza.desafio.funcionario.entity;

import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.funcionario.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TB_FUNCIONARIOS")
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String nome;
    @Column(nullable = false)
    private Integer idade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id" )
    private Endereco endereco;
}
