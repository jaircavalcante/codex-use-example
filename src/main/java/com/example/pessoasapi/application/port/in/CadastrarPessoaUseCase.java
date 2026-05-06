package com.example.pessoasapi.application.port.in;

import com.example.pessoasapi.domain.model.Pessoa;

public interface CadastrarPessoaUseCase {

    Pessoa cadastrar(CadastrarPessoaCommand command);
}
