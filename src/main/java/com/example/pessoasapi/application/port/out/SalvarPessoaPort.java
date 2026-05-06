package com.example.pessoasapi.application.port.out;

import com.example.pessoasapi.domain.model.Pessoa;

public interface SalvarPessoaPort {

    Pessoa salvar(Pessoa pessoa);
}
