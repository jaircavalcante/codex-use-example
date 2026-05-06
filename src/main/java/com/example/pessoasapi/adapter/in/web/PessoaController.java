package com.example.pessoasapi.adapter.in.web;

import com.example.pessoasapi.adapter.in.web.dto.CadastroPessoaRequest;
import com.example.pessoasapi.adapter.in.web.dto.PessoaResponse;
import com.example.pessoasapi.application.port.in.CadastrarPessoaUseCase;
import com.example.pessoasapi.domain.model.Pessoa;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final CadastrarPessoaUseCase cadastrarPessoaUseCase;

    public PessoaController(CadastrarPessoaUseCase cadastrarPessoaUseCase) {
        this.cadastrarPessoaUseCase = cadastrarPessoaUseCase;
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> cadastrar(@Valid @RequestBody CadastroPessoaRequest request) {
        Pessoa pessoaCadastrada = cadastrarPessoaUseCase.cadastrar(request.toCommand());
        URI location = URI.create("/api/pessoas/" + pessoaCadastrada.id());
        return ResponseEntity.created(location).body(PessoaResponse.from(pessoaCadastrada));
    }
}
