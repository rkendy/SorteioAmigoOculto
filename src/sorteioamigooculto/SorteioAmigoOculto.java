/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sorteioamigooculto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rkendy
 */
public class SorteioAmigoOculto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SorteioAmigoOculto sorteio = new SorteioAmigoOculto();
        sorteio.realizaSorteio();
    }
    
    private List<Participante> participantes;
    
    public void realizaSorteio() {
        carregaParticipantes();
        mistura();
        defineAmigosOcultos();
        //imprime();
        enviaEmails();
    }
    
    private void enviaEmails() {
        String titulo = "Email do Sorteio Amigo Oculto 2018";
        String texto = "Olá %s!. Bem-vindo ao Sorteio do Amigo Oculto 2018!!"
                   + "\n\n\nSeu amigo oculto será: %s."
                   + "\nRegras: etc...";
        
        Email email = new Email(titulo);
        this.participantes.forEach((p) -> {
            String textoEmail = String.format(texto, p.getNome(), p.getAmigoOculto().getNome());
            email.envia(p.getEmail(), textoEmail);
        });
    }
    
    private void carregaParticipantes() {
        this.participantes = new ArrayList<Participante>();
        this.participantes.add(criaParticipante("Nome do participante 1", "email01@gmail.com"));
        this.participantes.add(criaParticipante("Nome do participante 2", "email02@gmail.com"));
    }
    
    private void mistura() {
        Collections.shuffle(this.participantes);        
    }
    
    /**
     * O amigo oculto eh o proximo da lista.
     * Para que seja aleatorio, a lista precisa estar misturada.
     */
    private void defineAmigosOcultos() {
        int quantidade = this.participantes.size();
        for(int i=0 ; i<quantidade ; i++) {
            Participante sorteando = this.participantes.get(i);
            Participante sorteado = this.participantes.get((i+1) % quantidade);
            sorteando.setAmigoOculto(sorteado);
        }
    }
    
    private Participante criaParticipante(String nome, String email) {
        return new Participante(nome, email);
    }
    
    private void imprime() {
        this.participantes.forEach((p) -> {
            System.out.println("Nome: " + p.getNome()
                    + " Email: " + p.getEmail() 
                    + " Amigo: " + p.getAmigoOculto().getNome());
        });
    }
    
}
