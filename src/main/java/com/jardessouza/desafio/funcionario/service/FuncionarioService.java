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
    public FuncionarioResponseDTO save(FuncionarioRequestDTO request) {
        checkIfFuncionarioExists(request.getNome());
        Funcionario fucionarioTobeCreated = getFuncionarioAndSetEndereco(request);
        Funcionario funcionarioCreated = this.funcionarioRepository.save(fucionarioTobeCreated);
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioCreated);
    }

    public FuncionarioResponseDTO findByIdFuncionario(FuncionarioIdRequestDTO funcionarioRequest) {
        Funcionario funcionarioFound = this.funcionarioRepository.findById(funcionarioRequest.getId())
                .orElseThrow(() -> new FuncionarioNotFound(funcionarioRequest.getId()));
        getFuncionarioAndSetEndereco(FuncionarioMapper.INSTANCE.toDTORequest(funcionarioFound));
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioFound);
    }

    public List<FuncionarioResponseDTO> getAllFuncionarios() {
        List<Funcionario> listAllFuncionarios = this.funcionarioRepository.findAll();
        return FuncionarioMapper.INSTANCE.toDTO(listAllFuncionarios);
    }

    public FuncionarioResponseDTO getFuncionarioByCep(FuncionarioCepRequestDTO funcionarioRequest) {
        Funcionario funcionarioFound = this.funcionarioRepository.findByCep(funcionarioRequest.getCep())
                .orElseThrow(() -> new FuncionarioNotFound(funcionarioRequest.getCep()));
        getFuncionarioAndSetEndereco(FuncionarioMapper.INSTANCE.toDTORequest(funcionarioFound));
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioFound);
    }
    @Transactional
    public void updateFuncionario(FuncionarioUpdateRequestDTO funcionarioRequest) {
        FuncionarioResponseDTO funcionarioFound = findAndCheckFuncionarioExists(funcionarioRequest.getId());
        getAndUpdateFuncionario(funcionarioRequest, funcionarioFound);
    }

    public void deleteFuncionario(FuncionarioIdRequestDTO funcionarioRequest) {
        FuncionarioResponseDTO funcionarioFound = findAndCheckFuncionarioExists(funcionarioRequest.getId());
        this.funcionarioRepository.delete(FuncionarioMapper.INSTANCE.toModel(funcionarioFound));
    }

    public void replaceFuncionario(FuncionarioPatchRequestDTO funcionarioRequest) {
        FuncionarioResponseDTO funcionarioFound = findAndCheckFuncionarioExists(funcionarioRequest.getId());
        Funcionario funcionarioToBeCreated = FuncionarioMapper.INSTANCE.toModel(funcionarioRequest);
        ValidatingAndSettingAttributesFuncionario(funcionarioFound, funcionarioToBeCreated);
        getAndSaveEndereco(funcionarioFound, funcionarioToBeCreated);
        this.funcionarioRepository.save(funcionarioToBeCreated);
    }

    private void checkIfFuncionarioExists(String nome) {
        this.funcionarioRepository.findByNome(nome)
                .ifPresent(funcionario -> {
                    throw new FuncionarioAlreadyExists(nome);
                });
    }

    private FuncionarioResponseDTO findAndCheckFuncionarioExists(Long id) {
        Funcionario funcionarioFound = this.funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFound(id));
        getFuncionarioAndSetEndereco(FuncionarioMapper.INSTANCE.toDTORequest(funcionarioFound));
        return FuncionarioMapper.INSTANCE.toDTO(funcionarioFound);
    }

    private Funcionario getFuncionarioAndSetEndereco(FuncionarioRequestDTO funcionarioRequest) {
        EnderecoResponseDTO enderecoCreated = this.enderecoService.getEndereco(funcionarioRequest.getCep());
        Funcionario fucionarioTobeCreated = FuncionarioMapper.INSTANCE.toModel(funcionarioRequest);
        fucionarioTobeCreated.setEndereco(EnderecoMapper.INSTANCE.toModel(enderecoCreated));
        return fucionarioTobeCreated;
    }

    private void getAndSaveEndereco(FuncionarioResponseDTO funcionarioFound, Funcionario funcionario) {

        EnderecoResponseDTO enderecoToBeCreated = this.enderecoService.getEndereco(funcionario.getCep());
        Endereco enderecoCreated = EnderecoMapper.INSTANCE.toModel(enderecoToBeCreated);
        funcionario.setEndereco(enderecoCreated);
        funcionario.getEndereco().setId(funcionarioFound.getEndereco().getId());
        funcionario.setId(funcionarioFound.getId());
        this.enderecoRepository.save(enderecoCreated);
    }

    private void getAndUpdateFuncionario(FuncionarioUpdateRequestDTO funcionarioRequest, FuncionarioResponseDTO
            funcionarioFound) {
        Funcionario funcionarioToBeCreated = getFuncionarioAndSetEndereco(
                FuncionarioMapper.INSTANCE.toDTORequest(funcionarioRequest));
        funcionarioToBeCreated.setId(funcionarioFound.getId());
        funcionarioToBeCreated.getEndereco().setId(funcionarioFound.getEndereco().getId());
        this.enderecoRepository.save(funcionarioToBeCreated.getEndereco());
        this.funcionarioRepository.save(funcionarioToBeCreated);
    }

    private static void ValidatingAndSettingAttributesFuncionario(FuncionarioResponseDTO funcionarioFound, Funcionario funcionario) {
        if (funcionario.getNome() == null) funcionario.setNome(funcionarioFound.getNome());
        if (funcionario.getSexo() == null) funcionario.setSexo(funcionarioFound.getSexo());
        if (funcionario.getIdade() == null) funcionario.setIdade(funcionarioFound.getIdade());
        if (funcionario.getCep() == null) funcionario.setCep(funcionarioFound.getCep());
    }
}