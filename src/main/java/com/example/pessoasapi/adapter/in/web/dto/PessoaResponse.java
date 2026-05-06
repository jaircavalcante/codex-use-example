package com.example.pessoasapi.adapter.in.web.dto;

import com.example.pessoasapi.domain.model.Pessoa;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PessoaResponse(
        UUID id,
        String nome,
        String email,
        OffsetDateTime criadaEm
) {
    public static PessoaResponse from(Pessoa pessoa) {
        return new PessoaResponse(
                pessoa.id(),
                pessoa.nome(),
                pessoa.email(),
                pessoa.criadaEm()
        );
    }
}
