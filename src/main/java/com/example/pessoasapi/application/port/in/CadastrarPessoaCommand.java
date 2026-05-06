package com.example.pessoasapi.application.port.in;

public record CadastrarPessoaCommand(
        String nome,
        String email,
        String senha
) {
}
