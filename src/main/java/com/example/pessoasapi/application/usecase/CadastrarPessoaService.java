package com.example.pessoasapi.application.usecase;

import com.example.pessoasapi.application.port.in.CadastrarPessoaCommand;
import com.example.pessoasapi.application.port.in.CadastrarPessoaUseCase;
import com.example.pessoasapi.application.port.out.BuscarPessoaPorEmailPort;
import com.example.pessoasapi.application.port.out.SalvarPessoaPort;
import com.example.pessoasapi.domain.exception.EmailJaCadastradoException;
import com.example.pessoasapi.domain.model.Pessoa;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CadastrarPessoaService implements CadastrarPessoaUseCase {

    private final BuscarPessoaPorEmailPort buscarPessoaPorEmailPort;
    private final SalvarPessoaPort salvarPessoaPort;

    public CadastrarPessoaService(
            BuscarPessoaPorEmailPort buscarPessoaPorEmailPort,
            SalvarPessoaPort salvarPessoaPort
    ) {
        this.buscarPessoaPorEmailPort = buscarPessoaPorEmailPort;
        this.salvarPessoaPort = salvarPessoaPort;
    }

    @Override
    public Pessoa cadastrar(CadastrarPessoaCommand command) {
        buscarPessoaPorEmailPort.buscarPorEmail(command.email()).ifPresent(pessoa -> {
            throw new EmailJaCadastradoException(command.email());
        });

        Pessoa pessoa = new Pessoa(
                UUID.randomUUID(),
                command.nome(),
                command.email(),
                command.senha(),
                OffsetDateTime.now()
        );

        return salvarPessoaPort.salvar(pessoa);
    }
}
