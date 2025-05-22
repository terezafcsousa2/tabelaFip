package br.com.alura.valorCarro.principal;

import br.com.alura.valorCarro.model.Dados;
import br.com.alura.valorCarro.model.Modelos;
import br.com.alura.valorCarro.model.Veiculo;
import br.com.alura.valorCarro.service.ConsumoApi;
import br.com.alura.valorCarro.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    public void exibeMenu(){

       var menu = """
                        OPÇÕES: 
               ------------------------
               Carro
               Moto
               Caminhão
               
               Digite uma das OPÇÔES:
               """;
        System.out.println(menu);
        var opcao = leitura.nextLine();

        String endereco;
        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        } else if  (opcao.toLowerCase().contains("mot")){
            endereco =URL_BASE + "motos/marcas";
        } else{
            endereco = URL_BASE + "caminhoes/marcas";
        }
        var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("Informe o código da marca para consulta ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("\nDigite um trecho do nome do carro aser buscado");
        var nomeveiculo = leitura.nextLine();
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeveiculo.toLowerCase()))
                        .collect(Collectors.toList());
        System.out.println("\nModelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);
        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação: ");
        var codigoModelo = leitura.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size() ; i++) {
            var enderecoAnos = endereco + "/"+ anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veículos filtrados com avaliação por ano: ");
        veiculos.forEach(System.out::println);






    }
}
