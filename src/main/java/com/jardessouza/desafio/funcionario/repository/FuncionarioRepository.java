package com.jardessouza.desafio.funcionario.repository;

import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findAll();
    Optional<Funcionario> findByCep(String cep);

    Optional<Funcionario> findByNome(String nome);
}
