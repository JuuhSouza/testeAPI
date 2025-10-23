package br.com.teste.api_precoJogo.main;

import br.com.teste.api_precoJogo.model.GameInformations;
import br.com.teste.api_precoJogo.service.ConsumoApi;
import br.com.teste.api_precoJogo.service.ConverterDados;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;

public class Informations {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados converterDados = new ConverterDados();

    private final String ENDERECO = "https://www.cheapshark.com/api/1.0/games?title=";

    public void exibirMenu() {
        System.out.println("Digite um jogo para saber os melhores valores:");

        try {
        // Evita erro com espaços e acentuação
            String jogo = scanner.nextLine();
        String json = consumoApi.obterDados(ENDERECO + jogo.replace(" ", "+"));
        List<GameInformations> informationsGame = new ArrayList<>();
        System.out.println(informationsGame);

        //Ordena pelo menor preço
        System.out.println("Preços mais em conta no mercado:");
        informationsGame.sort(Comparator.comparingDouble(j -> Double.parseDouble(j.valor())));

        System.out.println("Jogos encontrados:");
            for (GameInformations game : informationsGame) {
                System.out.println("Título: " + game.titulo());
                System.out.println("Preço mais barato: $" + game.valor());
                System.out.println("Foto do jogo: " + game.foto());
                System.out.println("Id do jogo: " + game.idJogo());
                System.out.println("Id da Steam: " + game.idSteam());
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar dados da API: " + e.getMessage());
        }
}
}
