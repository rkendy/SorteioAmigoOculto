package br.com.sawamura.sorteio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author rkendy
 */
public class SorteioAmigoOculto {

    private List<Participante> participantes;

    public void realizaSorteio() {
        carregaParticipantes();
        mistura();
        defineAmigosOcultos();
        imprime();
        // enviaEmails();
    }

    private void enviaEmails() {
        String titulo = "Email do Sorteio Amigo Oculto 2020";
        String texto = "Olá %s!. Bem-vindo ao Sorteio do Amigo Oculto 2020!!" + "\n\n\nSeu amigo oculto será: %s."
                + "\nRegras: etc...";

        EmailService emailService = new EmailService(titulo);
        this.participantes.forEach((p) -> {
            String textoEmail = String.format(texto, p.getNome(), p.getAmigoOculto().getNome());
            emailService.envia(p.getEmail(), textoEmail);
        });
    }

    private void carregaParticipantes() {
        participantes = carregaArquivo() //
                .map(str -> str.split(":")) //
                .map(str -> criaParticipante(str[0], str[1])) //
                .collect(Collectors.toList());
    }

    private void mistura() {
        Collections.shuffle(this.participantes);
    }

    /**
     * O amigo oculto eh o proximo da lista. Para que seja aleatorio, a lista
     * precisa estar misturada.
     */
    private void defineAmigosOcultos() {
        int quantidade = this.participantes.size();
        for (int i = 0; i < quantidade; i++) {
            Participante sorteando = this.participantes.get(i);
            Participante sorteado = this.participantes.get((i + 1) % quantidade);
            sorteando.setAmigoOculto(sorteado);
        }
    }

    private Participante criaParticipante(String nome, String email) {
        return new Participante(nome, email);
    }

    private void imprime() {
        this.participantes.forEach((p) -> {
            System.out.println(
                    "Nome: " + p.getNome() + ". Email: " + p.getEmail() + ". Amigo: " + p.getAmigoOculto().getNome());
        });
    }

    private Stream<String> carregaArquivo() {
        try {
            return Files.lines(Paths.get("participantes.txt")).filter(line -> !line.startsWith("#"));
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            throw new RuntimeException("Erro ao ler arquivo");
        }
    }

}
