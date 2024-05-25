package br.com.bugred.tabelafipe.TabelaFipe.pricipal;

import br.com.bugred.tabelafipe.TabelaFipe.model.Dados;
import br.com.bugred.tabelafipe.TabelaFipe.model.Modelos;
import br.com.bugred.tabelafipe.TabelaFipe.model.Veiculo;
import br.com.bugred.tabelafipe.TabelaFipe.service.ConsumoApi;
import br.com.bugred.tabelafipe.TabelaFipe.service.ConverterDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();

    public void exibeMenu() {
        System.out.println("Escolha entre as seguintes opções: ");
        System.out.println("Carros\nMotos\nCaminhões");
        String opcao = sc.nextLine();
        String endereco = null;


        if(opcao.toLowerCase().contains("carr")){
             endereco = URL_BASE + "carros/marcas";
        }else if(opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas";
        }else if(opcao.toLowerCase().contains("caminh")){
            endereco = URL_BASE + "caminhoes/marcas";
        }else{
            System.out.println("Opção incorreta! Tente novamente!");
        }

        var json = consumoApi.obterDados(endereco);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream().sorted(Comparator.comparing(Dados::codigo)).forEach(d -> System.out.println(
                "Código: " + d.codigo() +
                        ", Nome: " + d.nome()
        ));

        System.out.println("Selecione um código para consulta: ");
        var codigoMarca = sc.nextInt();
        sc.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(endereco);

        var marcasPorCodigo = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marcas: ");
        marcasPorCodigo.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(m -> System.out.println(
                "Código: " + m.codigo() +
                        ", Nome: " + m.nome()
        ));

        System.out.println("Digite o trecho do veiculo que deseja acessar: ");
        var trechoDeVeiculo = sc.nextLine();

        List<Dados> modelosFiltrados = marcasPorCodigo.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoDeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("digite o código do modelo do carro: ");
        var codigoModelo = sc.nextLine();

//        endereco = endereco + "/" + codigoMarca + "/anos";
//        json = consumoApi.obterDados(endereco);
//        List<Dados> anos = conversor.obterLista(json, Dados.class);
//        List<Veiculo> veiculos = new ArrayList<>();
//
//        for(int i =0; i < anos.size(); i++){
//            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
//            json = consumoApi.obterDados(enderecoAnos);
//            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
//            veiculos.add(veiculo);
//        }
//        System.out.println("\nTodos os veiculos filtrados om avaliações por ano: ");
//
//        veiculos.forEach(System.out::println);

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoApi.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);













    }
}
