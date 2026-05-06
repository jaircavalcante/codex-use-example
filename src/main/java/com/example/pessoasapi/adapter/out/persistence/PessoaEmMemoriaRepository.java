package com.example.pessoasapi.adapter.out.persistence;

import com.example.pessoasapi.application.port.out.BuscarPessoaPorEmailPort;
import com.example.pessoasapi.application.port.out.SalvarPessoaPort;
import com.example.pessoasapi.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PessoaEmMemoriaRepository implements BuscarPessoaPorEmailPort, SalvarPessoaPort {

    private final Map<String, Pessoa> pessoasPorEmail = new ConcurrentHashMap<>();

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        pessoasPorEmail.put(pessoa.email(), pessoa);
        return pessoa;
    }

    @Override
    public Optional<Pessoa> buscarPorEmail(String email) {
        return Optional.ofNullable(pessoasPorEmail.get(email));
    }
}
