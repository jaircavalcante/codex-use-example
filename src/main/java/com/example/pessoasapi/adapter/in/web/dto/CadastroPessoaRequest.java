package com.example.pessoasapi.adapter.in.web.dto;

import com.example.pessoasapi.application.port.in.CadastrarPessoaCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroPessoaRequest(
        @NotBlank(message = "Nome e obrigatorio")
        String nome,

        @NotBlank(message = "E-mail e obrigatorio")
        @Email(message = "E-mail deve ser valido")
        String email,

        @NotBlank(message = "Senha e obrigatoria")
        @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
        String senha
) {
    public CadastrarPessoaCommand toCommand() {
        return new CadastrarPessoaCommand(nome, email, senha);
    }
}
