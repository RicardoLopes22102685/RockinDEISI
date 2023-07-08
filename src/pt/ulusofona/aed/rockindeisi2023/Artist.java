package pt.ulusofona.aed.rockindeisi2023;

public class Artist {
    String nomeArtista;
    int numMusicas;

    public Artist(String nomeArtista, int numMusicas) {
        this.nomeArtista = nomeArtista;
        this.numMusicas = numMusicas;
    }

    @Override
    public String toString() {
        if (nomeArtista.startsWith("A") || nomeArtista.startsWith("B") || nomeArtista.startsWith("C") || nomeArtista.startsWith("D")) {
            return String.format("Artista: [%s]", nomeArtista);
        } else {
            return String.format("Artista: [%s] | %d", nomeArtista, numMusicas);
        }
    }
}
