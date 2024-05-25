package br.com.bugred.tabelafipe.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo (@JsonAlias("Valor") String valor,
                       @JsonAlias("Marca") String marcas,
                       @JsonAlias("AnoModelo") Integer ano,
                       @JsonAlias("Combustivel") String tipoCombustivel){
}
