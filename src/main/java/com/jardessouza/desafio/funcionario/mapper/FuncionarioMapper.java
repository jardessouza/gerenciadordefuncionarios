package com.jardessouza.desafio.funcionario.mapper;

import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    public static final FuncionarioMapper INSTANCE = Mappers.getMapper(FuncionarioMapper.class);

    Funcionario toModel(FuncionarioRequestDTO funcionarioRequestDTO);
    FuncionarioResponseDTO toDTO(Funcionario funcionario);
}
