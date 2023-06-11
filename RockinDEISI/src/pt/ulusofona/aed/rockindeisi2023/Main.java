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
    static ArrayList<Statistics> array_statistics = new ArrayList<>();
    static HashMap<String, Song> songs_Map = new HashMap<>();
    static HashMap<String, Song> song_details_Map = new HashMap<>();
    static HashMap<String, Artist> artists_Map = new HashMap<>();

    public enum TipoEntidade {
        TEMA, ARTISTA, INPUT_INVALIDO
    }

    public static ArrayList getObjects(TipoEntidade tipo) {
        switch (tipo) {
            case TEMA -> {
                return songs;
            }
            case ARTISTA -> {
                return artists;
            }
            default -> {
                return array_statistics;
            }
        }
    }

    public static boolean loadFiles(File folder) {
        //Limpeza estruturas de dados
        songs.clear();
        artists.clear();
        array_statistics.clear();
        songs_Map.clear();
        song_details_Map.clear();
        artists_Map.clear();
        String artists = "";
        //Leitura ficheiro songs.txt
        File songs_File = new File(folder, "songs.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(songs_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        int linhas_ok = 0, linhas_nok = 0, line_count = 0;
        int primeira_linha_nok = -1;
        while (scanner.hasNext()) {
            line_count++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            if (song_elements.length != 3) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }
            String song_ID = song_elements[0].trim();
            String song_name = song_elements[1].trim();
            int song_year = Integer.parseInt(song_elements[2].trim());
            if (songs_Map.containsKey(song_ID)) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }
            linhas_ok++;
            Song song = new Song(song_ID, song_name, song_year);
            songs_Map.put(song_ID, song);
            songs.add(song);
        }
        array_statistics.add(0, new Statistics("songs.txt", linhas_ok, linhas_nok, primeira_linha_nok));
        //Leitura song_details.txt
        File song_details_File = new File(folder, "song_details.txt");
        try {
            scanner = new Scanner(song_details_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        //Leitura ficheiro song_details.txt
        linhas_ok = linhas_nok = line_count = 0;
        primeira_linha_nok = -1;
        while (scanner.hasNext()) {
            line_count++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            if (song_elements.length != 7) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }
            String song_ID = song_elements[0].trim();
            String duration_string = song_elements[1].trim();
            String explicit_String = song_elements[2].trim();
            String popularity_String = song_elements[3].trim();
            String dance_rate_String = song_elements[4].trim();
            String vivacity_String = song_elements[5].trim();
            String mean_volume_String = song_elements[6].trim();
            if (!songs_Map.containsKey(song_ID)) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }
            linhas_ok++;
            long duration_parse = Long.parseLong(duration_string);
            //float time_minutes = (float) duration_parse / (1000 * 60);
            long total_seconds = duration_parse / 1000;
            long minutes = total_seconds / 60;
            long seconds = total_seconds % 60;
            /*String[] duration_split = Float.toString(time_minutes).split("\\.");
            String minutes = duration_split[0];
            String seconds_String = duration_split[1];
            float seconds = Float.parseFloat(seconds_String) * 60 / 100;*/
            duration_string = minutes + ":" + seconds; //Verificar se é necessário usar math para round up/down
            short explicit = Short.parseShort(explicit_String);
            int popularity = Integer.parseInt(popularity_String);
            BigDecimal dance_rate = new BigDecimal(dance_rate_String);
            BigDecimal vivacity = new BigDecimal(vivacity_String);
            BigDecimal mean_volume = new BigDecimal(mean_volume_String);
            Song song_to_add_Map = new Song();
            song_to_add_Map.song_ID = song_ID;
            song_to_add_Map.duration = duration_string;
            song_to_add_Map.explicit = explicit;
            song_to_add_Map.popularity = popularity;
            song_to_add_Map.dance_rate = dance_rate;
            song_to_add_Map.vivacity = vivacity;
            song_to_add_Map.mean_volume = mean_volume;
            song_details_Map.put(song_ID, song_to_add_Map);
        }
        array_statistics.add(1, new Statistics("song_details.txt", linhas_ok, linhas_nok, primeira_linha_nok));

        for (Song song_to_modify : songs) {
            if (!song_details_Map.containsKey(song_to_modify.song_ID)) {
                continue;
            }
            Song details_to_add = song_details_Map.get(song_to_modify.song_ID);
            song_to_modify.duration = details_to_add.duration;
            song_to_modify.explicit = details_to_add.explicit;
            song_to_modify.popularity = details_to_add.popularity;
            song_to_modify.dance_rate = details_to_add.dance_rate;
            song_to_modify.vivacity = details_to_add.vivacity;
            song_to_modify.mean_volume = details_to_add.mean_volume;
        }

        //Leitura ficheiro song_artists.txt
        File song_artists_File = new File(folder, "song_artists.txt");
        try {
            scanner = new Scanner(song_artists_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        linhas_ok = linhas_nok = line_count = 0;
        primeira_linha_nok = -1;
        while (scanner.hasNext()) {
            line_count++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            if (song_elements.length != 2){
                if (primeira_linha_nok == -1){
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }
            String song_ID = song_elements[0];
            artists = song_elements[1];

            if (!songs_Map.containsKey(song_ID)) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }

            //TODO
            /*Artist artist = new Artist(song_ID, artists, )
            songs_Map.put(song_ID, song);
            songs.add(song);*/

            linhas_ok ++;
        }
        int num_artistas =  Functions.process_Artists(artists).size();
        array_statistics.add(2, new Statistics("song_artists.txt", linhas_ok,linhas_nok,primeira_linha_nok));

        return true;
    }

    public static void main(String[] args) {
        long tempo1 = System.currentTimeMillis();
        loadFiles(new File("."));
        //System.out.println(getObjects(TipoEntidade.TEMA).toString());
        /*for (Statistics objeto : array_statistics){
            System.out.println(objeto);
        }*/
        String s = "\"['The Chenille, Sisters', \"\"James Dapogny's Chicago Jazz Band\"\"]\"";
        String s2 = "\"['Vanessa Bell Armstrong, Patti Austin, Bernie K.']\"";
        String s3 = "\"['Johnny Pacheco', 'Pete \"\"El Conde\"\" Rodriguez']\"";
        System.out.println(System.currentTimeMillis() - tempo1);

    }
}