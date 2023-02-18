package com.jardessouza.desafio.funcionario.service;

import com.jardessouza.desafio.endereco.dto.EnderecoResponseDTO;
import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.endereco.mapper.EnderecoMapper;
import com.jardessouza.desafio.endereco.repository.EnderecoRepository;
import com.jardessouza.desafio.endereco.service.EnderecoService;
import com.jardessouza.desafio.funcionario.dto.*;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import com.jardessouza.desafio.funcionario.exception.FuncionarioAlreadyExists;
import com.jardessouza.desafio.funcionario.exception.FuncionarioNotFound;
import com.jardessouza.desafio.funcionario.mapper.FuncionarioMapper;
import com.jardessouza.desafio.funcionario.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final EnderecoService enderecoService;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public FuncionarioResponseDTO save(FuncionarioRequestDTO funcionarioRequestDTO) {
        checkIfFuncionarioExists(funcionarioRequestDTO.getNome());
        Funcionario fucionarioTobeCreated = getFuncionarioAndSetEndereco(funcionarioRequestDTO);
        Funcionario funcionarioCreated = this.funcionarioRepository.save(fucionarioTobeCreated);
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioCreated);
    }

    public FuncionarioResponseDTO findByIdFuncionario(FuncionarioIdRequestDTO funcionarioIdRequest) {
        Funcionario funcionarioFound = this.funcionarioRepository.findById(funcionarioIdRequest.getId())
                .orElseThrow(() -> new FuncionarioNotFound(funcionarioIdRequest.getId()));
        getFuncionarioAndSetEndereco(FuncionarioMapper.INSTANCE.toDTORequest(funcionarioFound));
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioFound);
    }

    public List<FuncionarioResponseDTO> getAllFuncionarios() {
        List<Funcionario> listAllFuncionarios = this.funcionarioRepository.findAll();
        return FuncionarioMapper.INSTANCE.toDTO(listAllFuncionarios);
    }

    public FuncionarioResponseDTO getFuncionarioByCep(FuncionarioCepRequestDTO funcionarioCepRequest) {
        Funcionario funcionarioFound = this.funcionarioRepository.findByCep(funcionarioCepRequest.getCep())
                .orElseThrow(() -> new FuncionarioNotFound(funcionarioCepRequest.getCep()));
        getFuncionarioAndSetEndereco(FuncionarioMapper.INSTANCE.toDTORequest(funcionarioFound));
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioFound);
    }

    public void updateFuncionario(FuncionarioUpdateRequestDTO funcionarioRequestDTO) {
        FuncionarioResponseDTO funcionarioFound = findAndCheckFuncionarioExists(funcionarioRequestDTO.getId());
        getAndUpdateFuncionario(funcionarioRequestDTO, funcionarioFound);
    }

    public void deleteFuncionario(FuncionarioIdRequestDTO funcionarioIdRequest) {
        FuncionarioResponseDTO funcionarioFound = findAndCheckFuncionarioExists(funcionarioIdRequest.getId());
        this.funcionarioRepository.delete(FuncionarioMapper.INSTANCE.toModel(funcionarioFound));
    }

    public void replaceFuncionario(FuncionarioPatchRequestDTO funcionarioPatchRequestDTO) {
        FuncionarioResponseDTO funcionarioFound = findAndCheckFuncionarioExists(funcionarioPatchRequestDTO.getId());
        Funcionario funcionarioToBeCreated = FuncionarioMapper.INSTANCE.toModel(funcionarioPatchRequestDTO);
        ValidatingAndSettingAttributesFuncionario(funcionarioFound, funcionarioToBeCreated);
        getAndSaveEndereco(funcionarioFound, funcionarioToBeCreated);
        this.funcionarioRepository.save(funcionarioToBeCreated);

    }

    private void getAndSaveEndereco(FuncionarioResponseDTO funcionarioFound, Funcionario funcionarioToBeCreated) {

        EnderecoResponseDTO enderecoToBeCreated = this.enderecoService.getEndereco(funcionarioToBeCreated.getCep());
        Endereco enderecoCreated = EnderecoMapper.INSTANCE.toModel(enderecoToBeCreated);
        funcionarioToBeCreated.setEndereco(enderecoCreated);
        funcionarioToBeCreated.getEndereco().setId(funcionarioFound.getEndereco().getId());
        funcionarioToBeCreated.setId(funcionarioFound.getId());
        this.enderecoRepository.save(enderecoCreated);
    }

    private static void ValidatingAndSettingAttributesFuncionario(FuncionarioResponseDTO funcionarioFound, Funcionario funcionarioToBeCreated) {
        if (funcionarioToBeCreated.getNome() == null) funcionarioToBeCreated.setNome(funcionarioFound.getNome());
        if (funcionarioToBeCreated.getSexo() == null) funcionarioToBeCreated.setSexo(funcionarioFound.getSexo());
        if (funcionarioToBeCreated.getIdade() == null) funcionarioToBeCreated.setIdade(funcionarioFound.getIdade());
        if (funcionarioToBeCreated.getCep() == null) funcionarioToBeCreated.setCep(funcionarioFound.getCep());
    }

    private Funcionario getFuncionarioAndSetEndereco(FuncionarioRequestDTO funcionarioRequestDTO) {
        EnderecoResponseDTO enderecoCreated = this.enderecoService.getEndereco(funcionarioRequestDTO.getCep());
        Funcionario fucionarioTobeCreated = FuncionarioMapper.INSTANCE.toModel(funcionarioRequestDTO);
        fucionarioTobeCreated.setEndereco(EnderecoMapper.INSTANCE.toModel(enderecoCreated));
        return fucionarioTobeCreated;
    }

    private void getAndUpdateFuncionario(FuncionarioUpdateRequestDTO funcionarioRequestDTO, FuncionarioResponseDTO
            funcionarioFound) {
        Funcionario funcionarioToBeCreated = getFuncionarioAndSetEndereco(
                FuncionarioMapper.INSTANCE.toDTORequest(funcionarioRequestDTO));
        funcionarioToBeCreated.setId(funcionarioFound.getId());
        funcionarioToBeCreated.getEndereco().setId(funcionarioFound.getEndereco().getId());
        this.enderecoRepository.save(funcionarioToBeCreated.getEndereco());
        this.funcionarioRepository.save(funcionarioToBeCreated);
    }

    public void checkIfFuncionarioExists(String nome) {
        this.funcionarioRepository.findByNome(nome)
                .ifPresent(funcionario -> {
                    throw new FuncionarioAlreadyExists(nome);
                });
    }

    public FuncionarioResponseDTO findAndCheckFuncionarioExists(Long id) {
        Funcionario funcionarioFound = this.funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFound(id));
        getFuncionarioAndSetEndereco(FuncionarioMapper.INSTANCE.toDTORequest(funcionarioFound));
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioFound);
    }

}