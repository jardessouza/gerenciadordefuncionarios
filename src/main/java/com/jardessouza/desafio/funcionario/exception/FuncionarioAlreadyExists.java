package com.jardessouza.desafio.funcionario.exception;

import javax.persistence.EntityExistsException;

public class FuncionarioAlreadyExists extends EntityExistsException {
    public FuncionarioAlreadyExists(String nome){
        super(String.format("Employee named %s already exists!", nome));
    }
}
