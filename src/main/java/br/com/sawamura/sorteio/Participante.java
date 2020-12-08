
package br.com.sawamura.sorteio;

import java.util.Objects;

/**
 * @author rkendy
 */
public class Participante {
    private String nome;
    private String email;
    private Participante amigoOculto;

    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.amigoOculto = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Participante getAmigoOculto() {
        return amigoOculto;
    }

    public void setAmigoOculto(Participante amigoOculto) {
        this.amigoOculto = amigoOculto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participante other = (Participante) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.nome);
        hash = 71 * hash + Objects.hashCode(this.email);
        return hash;
    }

}
