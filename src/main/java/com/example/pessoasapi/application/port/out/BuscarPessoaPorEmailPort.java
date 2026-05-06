package com.example.pessoasapi.application.port.out;

import com.example.pessoasapi.domain.model.Pessoa;

import java.util.Optional;

public interface BuscarPessoaPorEmailPort {

    Optional<Pessoa> buscarPorEmail(String email);
}
