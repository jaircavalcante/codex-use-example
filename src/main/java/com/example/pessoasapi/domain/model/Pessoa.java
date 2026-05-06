package com.example.pessoasapi.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Pessoa(
        UUID id,
        String nome,
        String email,
        String senha,
        OffsetDateTime criadaEm
) {
}
