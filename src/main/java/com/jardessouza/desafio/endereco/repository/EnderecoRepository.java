package com.jardessouza.desafio.endereco.repository;

import com.jardessouza.desafio.endereco.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
