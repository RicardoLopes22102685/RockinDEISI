package pt.ulusofona.aed.rockindeisi2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.math.BigDecimal;

public class TestMain {
    @Test
    public void to_String_menor_que_1995() {
        Song song = new Song("06NUxS2XL3efRh0bloxkHm", "7:56", (short) 1, 38, new BigDecimal("0.5710000000000001"), new BigDecimal("0.092"), new BigDecimal("-6.943"));
        song.songName = "Broken Puppet - Original Mix";
        song.songYear = 1920;
        Assertions.assertEquals(song.toString(), "06NUxS2XL3efRh0bloxkHm | Broken Puppet - Original Mix | 1920", "Função toString() para musicas inferiores a 1995 não funciona corretamente");
    }

    @Test
    public void to_String_menor_que_2000() {
        Song song = new Song("2ck13qfgRZ1msyEJlDqzvk", "4:23", (short) 0, 48, new BigDecimal("0.588"), new BigDecimal("0.45399999999999996"), new BigDecimal("-6.182"));
        song.songName = "Where Have All the Cowboys Gone?";
        song.songYear = 1996;
        Assertions.assertEquals(song.toString(), "2ck13qfgRZ1msyEJlDqzvk | Where Have All the Cowboys Gone? | 1996 | 4:23 | 48", "Função toString() para musicas inferiores a 2000 não funciona corretamente");
    }

    @Test
    public void to_String_maior_que_2000() {
        Song song = new Song("3xpkvm9sTBtUhhlX2d8eAk", "4:40", (short) 1, 3, new BigDecimal("0.52"), new BigDecimal("0.14400000000000002"), new BigDecimal("-7.2570000000000014"));
        song.songName = "A Thousand Years (Tribute Twilight)";
        song.songYear = 2013;
        song.numArtists = 10;
        Assertions.assertEquals(song.toString(), "3xpkvm9sTBtUhhlX2d8eAk | A Thousand Years (Tribute Twilight) | 2013 | 4:40 | 3 | 10", "Função toString() para musicas superiores a 2000 não funciona corretamente");
    }

    @Test
    public void to_String_Artist() {
        Artist artist1 = new Artist("Drake", 15);
        Artist artist2 = new Artist("George Jones", 3);
        Assertions.assertEquals(artist1.toString(), "Artista: [Drake]", "Função toString() de Artists com iniciais ABCD não funciona corretamente");
        Assertions.assertEquals(artist2.toString(), "Artista: [George Jones] | 3", "Função toString() de Artists não funciona corretamente");
    }

    @Test
    public void testFilesNoErrors() {
        Assertions.assertTrue(Main.loadFiles(new File("test-files/testFilesNoErrors")));
        Assertions.assertEquals("songs.txt | 3 | 0 | -1", Main.array_statistics.get(0).toString(), "Estatisticas de songs.txt não estão corretas");
        Assertions.assertEquals("song_details.txt | 3 | 0 | -1", Main.array_statistics.get(1).toString(), "Estatisticas de details.txt não estão corretas");
        Assertions.assertEquals("song_artists.txt | 3 | 0 | -1", Main.array_statistics.get(2).toString(), "Estatisticas de artists.txt não estão corretas");
    }
    @Test
    public void testFilesWithErrors(){
        Assertions.assertTrue(Main.loadFiles(new File("test-files/testFilesWithErrors")));
        Assertions.assertEquals("songs.txt | 3 | 0 | -1", Main.array_statistics.get(0).toString(), "Estatisticas de songs.txt não estão corretas");
        Assertions.assertEquals("song_details.txt | 2 | 1 | 3", Main.array_statistics.get(1).toString(), "Estatisticas de song_details.txt não estão corretas");
        Assertions.assertEquals("song_artists.txt | 2 | 1 | 3", Main.array_statistics.get(2).toString(), "Estatisticas de song_artists.txt não estão corretas");
    }

}
