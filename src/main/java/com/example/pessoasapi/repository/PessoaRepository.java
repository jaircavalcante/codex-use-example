package com.example.pessoasapi.repository;

import com.example.pessoasapi.model.Pessoa;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PessoaRepository {

    private final Map<String, Pessoa> pessoasPorEmail = new ConcurrentHashMap<>();

    public Pessoa save(Pessoa pessoa) {
        pessoasPorEmail.put(pessoa.email(), pessoa);
        return pessoa;
    }

    public Optional<Pessoa> findByEmail(String email) {
        return Optional.ofNullable(pessoasPorEmail.get(email));
    }
}
