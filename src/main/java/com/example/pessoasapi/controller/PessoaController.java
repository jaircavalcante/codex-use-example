package com.example.pessoasapi.controller;

import com.example.pessoasapi.dto.CadastroPessoaRequest;
import com.example.pessoasapi.dto.PessoaResponse;
import com.example.pessoasapi.model.Pessoa;
import com.example.pessoasapi.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> cadastrar(@Valid @RequestBody CadastroPessoaRequest request) {
        pessoaRepository.findByEmail(request.email()).ifPresent(pessoa -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail ja cadastrado");
        });

        Pessoa pessoa = new Pessoa(
                UUID.randomUUID(),
                request.nome(),
                request.email(),
                request.senha(),
                OffsetDateTime.now()
        );

        Pessoa pessoaCadastrada = pessoaRepository.save(pessoa);
        URI location = URI.create("/api/pessoas/" + pessoaCadastrada.id());
        return ResponseEntity.created(location).body(PessoaResponse.from(pessoaCadastrada));
    }
}
