package pt.ulusofona.aed.rockindeisi2023;

public class Artist {
    String song_ID;
    String nome_artista;
    int num_Musicas;

    public Artist(String song_ID, String nome_artista, int numMusicas) {
        this.song_ID = song_ID;
        this.nome_artista = nome_artista;
        num_Musicas = numMusicas;
    }

    @Override
    public String toString() {
        if (nome_artista.startsWith("A") || nome_artista.startsWith("B") || nome_artista.startsWith("C") || nome_artista.startsWith("D")) {
            return String.format("Artista: [%s]",nome_artista);
        } else {
            return String.format("Artista: [%s] | %d", nome_artista, num_Musicas);
        }


    }
}
