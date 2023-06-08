package pt.ulusofona.aed.rockindeisi2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static ArrayList<Song> songs = new ArrayList<>();
    static ArrayList<Artist> artists = new ArrayList<>();
    static Statistics[] array_statistics = new Statistics[3];
    static HashMap<String, Song> songs_Map = new HashMap<>();
    static HashMap<String, Song> song_details_Map = new HashMap<>();
    static HashMap<String, Song> artists_Map = new HashMap<>();

    public enum TipoEntidade {
        TEMA, ARTISTA, INPUT_INVALIDO
    }

    public static ArrayList getObjects(TipoEntidade tipo) {
        switch (tipo) {
            case TEMA -> {
                return songs;
            }

        }
        return new ArrayList<>();
    }

    public static boolean loadFiles(File folder) {
        //TODO LoadFiles precisa fazer load de ARTISTS
        //Limpeza estruturas de dados
        for (Statistics statistic : array_statistics) {
            statistic = null;
        }
        songs.clear();
        artists.clear();
        songs_Map.clear();
        song_details_Map.clear();
        artists_Map.clear();
        //Leitura ficheiro songs.txt
        File songs_File = new File(folder, "songs.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(songs_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        int line_count = 0;
        for (Statistics statistic : array_statistics) {
            statistic = new Statistics();
        }
        array_statistics[0].nome_ficheiro = "songs.txt";
        while (scanner.hasNext()) {
            line_count++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            String song_ID = song_elements[0];
            String song_name = song_elements[1];
            int song_year = Integer.parseInt(song_elements[2]);
            if (song_elements.length != 3 || songs_Map.containsKey(song_ID)) {
                if (array_statistics[0].primeira_linha_nok == -1) {
                    array_statistics[0].primeira_linha_nok = line_count;
                }
                array_statistics[0].linhas_nok++;
                continue;
            }
            array_statistics[0].linhas_ok++;
            Song song = new Song(song_ID, song_name, song_year);
            songs_Map.put(song_ID, song);
            songs.add(song);
        }
        //Leitura ficheiro song_artists.txt
        File song_artists_File = new File(folder, "song_artists.txt");
        try {
            scanner = new Scanner(song_artists_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        line_count = 0;
        array_statistics[1].nome_ficheiro = "artists.txt";
        while (scanner.hasNext()) { //TODO alterar para array artists
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            String song_ID = song_elements[0];
            String song_name = song_elements[1];
            int song_year = Integer.parseInt(song_elements[2]);

            Song song = new Song(song_ID, song_name, song_year);
            songs.add(song);
        }

        File song_details_File = new File(folder, "song_details.txt");
        try {
            scanner = new Scanner(song_details_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        //Leitura ficheiro song_details.txt
        line_count = 0;
        array_statistics[2].nome_ficheiro = "song_details.txt";
        while (scanner.hasNext()) {
            line_count++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            String song_ID = song_elements[0];
            String duration_string = song_elements[1];
            String explicit_String = song_elements[2];
            String popularity_String = song_elements[3];
            String dance_rate_String = song_elements[4];
            String vivacity_String = song_elements[5];
            String mean_volume_String = song_elements[6];
            if (song_elements.length != 7 || !songs_Map.containsKey(song_ID)) {
                if (array_statistics[2].primeira_linha_nok == -1) {
                    array_statistics[2].primeira_linha_nok = line_count;
                }
                array_statistics[0].linhas_nok++;
                continue;
            }
            array_statistics[0].linhas_ok++;
            long duration_parse = Long.parseLong(duration_string);
            //String duration = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(duration_parse));
            long time_minutes = duration_parse / (1000 * 60);
            String[] duration_split = Long.toString(time_minutes).split("\\.");
            String minutes = duration_split[0];
            String seconds_String = duration_split[1];
            float seconds = Float.parseFloat(seconds_String) * 60 / 100;
            duration_string = minutes + ":" + (int) seconds;
            short explicit = Short.parseShort(explicit_String);
            int popularity = Integer.parseInt(popularity_String);
            BigDecimal dance_rate = new BigDecimal(dance_rate_String);
            BigDecimal vivacity = new BigDecimal(vivacity_String);
            BigDecimal mean_volume = new BigDecimal(mean_volume_String);
            Song song_to_add_Set = new Song();
            song_to_add_Set.song_ID = song_ID;
            song_to_add_Set.duration = duration_string;
            song_to_add_Set.explicit = explicit;
            song_to_add_Set.popularity = popularity;
            song_to_add_Set.dance_rate = dance_rate;
            song_to_add_Set.vivacity = vivacity;
            song_to_add_Set.mean_volume = mean_volume;
            song_details_Map.put(song_ID, song_to_add_Set);
        }
        for (Song song : songs) {
            if (!song_details_Map.containsKey(song.song_ID)) {
                continue;
            }
            Song details_to_add = song_details_Map.get(song.song_ID);
            song.duration = details_to_add.duration;
            song.explicit = details_to_add.explicit;
            song.popularity = details_to_add.popularity;
            song.dance_rate = details_to_add.dance_rate;
            song.vivacity = details_to_add.vivacity;
            song.mean_volume = details_to_add.mean_volume;
        }
        return true;
    }

    public static void main(String[] args) {
        loadFiles(new File("."));

    }
}