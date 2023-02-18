package com.jardessouza.desafio.funcionario.exception;

import javax.persistence.EntityNotFoundException;

public class FuncionarioNotFound extends EntityNotFoundException {
    public FuncionarioNotFound(Long id){
        super(String.format("Employee with id %d not found!", id));
    }

    public FuncionarioNotFound(String cep){
        super(String.format("Employee with CEP %s not found!", cep));
    }

}
