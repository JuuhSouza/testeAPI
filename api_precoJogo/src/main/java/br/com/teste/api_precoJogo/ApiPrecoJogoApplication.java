package br.com.teste.api_precoJogo;

import br.com.teste.api_precoJogo.model.GameInformations;
import br.com.teste.api_precoJogo.service.ConsumoApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ApiPrecoJogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiPrecoJogoApplication.class, args);
	}

	private final Scanner leitura = new Scanner(System.in);

	@Override
	public void run(String... args) {


		try {
			var consumoApi = new ConsumoApi();
			System.out.print("Digite o nome do jogo: ");
			String nome = leitura.nextLine();

			// Evita erro com espaços e acentuação
			String nomeCodificado = URLEncoder.encode(nome, StandardCharsets.UTF_8);

			String url = "https://www.cheapshark.com/api/1.0/games?title=" + nomeCodificado;
			var json = consumoApi.obterDados(url);
			ObjectMapper mapper = new ObjectMapper();
			List<GameInformations> jogos = mapper.readValue(json, new TypeReference<List<GameInformations>>() {});

			//Ordena pelo menor preço
			System.out.println("Preços mais em conta no mercado");
			jogos.sort(Comparator.comparingDouble(j -> Double.parseDouble(j.valor())));

			System.out.println("Jogos encontrados:");
			for (GameInformations jogo : jogos) {
				System.out.println("Título: " + jogo.titulo());
				System.out.println("Preço mais barato: $" + jogo.valor());
				System.out.println("Foto do jogo: " + jogo.foto());
				System.out.println("Id do jogo: " + jogo.idJogo());
				System.out.println("Id da Steam: " + jogo.idSteam());
				System.out.println("-----------------------------");
			}

		} catch (Exception e) {
			System.err.println("Erro ao buscar dados da API: " + e.getMessage());
		}
}
}

