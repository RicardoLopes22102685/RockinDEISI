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
    static HashMap<String, Artist> artist_Map = new HashMap<>();

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
            case INPUT_INVALIDO -> {
                return array_statistics;
            }
            default -> {
                return null;
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
            if (song_elements.length != 3 || songs_Map.containsKey(song_elements[0].trim())) {//Só pode haver uma música com o mesmo ID
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            }
            String song_ID = song_elements[0].trim();
            String song_name = song_elements[1].trim();
            int song_year = Integer.parseInt(song_elements[2].trim());
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
            if (seconds < 10) {
                duration_string = minutes + ":0" + seconds;
            } else {
                duration_string = minutes + ":" + seconds;
            }
            //Verificar se é necessário usar math para round up/down
            short explicit = Short.parseShort(explicit_String);
            int popularity = Integer.parseInt(popularity_String);
            BigDecimal dance_rate = new BigDecimal(dance_rate_String);
            BigDecimal vivacity = new BigDecimal(vivacity_String);
            BigDecimal mean_volume = new BigDecimal(mean_volume_String);
            Song song_retrieved = songs_Map.get(song_ID);
            Song song_dtl_to_Map = new Song(song_ID, duration_string, explicit, popularity, dance_rate, vivacity, mean_volume);
            song_dtl_to_Map.song_name = song_retrieved.song_name;
            song_dtl_to_Map.song_year = song_retrieved.song_year;
            songs_Map.replace(song_ID, song_dtl_to_Map);
        }
        array_statistics.add(1, new Statistics("song_details.txt", linhas_ok, linhas_nok, primeira_linha_nok));

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
            if (song_elements.length != 2 || !songs_Map.containsKey(song_elements[0].trim())) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = line_count;
                }
                linhas_nok++;
                continue;
            } //Song é valida e existe
            String song_ID = song_elements[0].trim();
            ArrayList<String> line_artists = Functions.parseMultipleArtists(song_elements[1]);
            int count = 0;
            for (String artist_to_add : line_artists) {
                Artist artist_temp = new Artist(artist_to_add, 1);
                if (artist_Map.putIfAbsent(artist_to_add, artist_temp) == null) {
                    artists.add(artist_temp);
                } else {
                    Artist artist_to_modify = artist_Map.get(artist_to_add);
                    artist_to_modify.num_Musicas++;
                    artist_Map.replace(artist_to_add, artist_to_modify);
                }
                count++;
            }
            Song song_to_modify = songs_Map.get(song_ID);
            song_to_modify.num_Artists = count;
            songs_Map.replace(song_ID, song_to_modify);
            linhas_ok++;
        }
        array_statistics.add(2, new Statistics("song_artists.txt", linhas_ok, linhas_nok, primeira_linha_nok));
        songs.replaceAll(song -> songs_Map.get(song.song_ID));
        for (Artist artist_to_modify : artists) {
            artist_to_modify.num_Musicas = artist_Map.get(artist_to_modify.nome_artista).num_Musicas;
        }
        return true;
    }

    public static void main(String[] args) {
        long time_ini = System.currentTimeMillis();
        loadFiles(new File("."));

        /*
        System.out.println(songs_Map.get("5EMKS6mLi4JprJxy8IGdhT"));
        for (Song song : songs) {
            if (song.song_ID.equals("5EMKS6mLi4JprJxy8IGdhT")) {
                System.out.println(song);
            }
        }
        for (Artist artist : artists) {
            if (artist.nome_artista.equals("Drake")) {
                System.out.println(artist);
            }
        }
        */
        System.out.println(System.currentTimeMillis()-time_ini);
        /*
        Scanner input = new Scanner((System.in));
        String line = input.nextLine();
        while (!line.equals("EXIT")) {
            QueryResult executed = Functions.execute(line);
            if (executed == null) {
                System.out.println("Illegal command. Try again");
            } else {
                System.out.println(executed.result);
                System.out.println("(took " + executed.time + " ms)");
                System.out.println();
            }
            line = input.nextLine();
        }*/
        //System.out.println(getObjects(TipoEntidade.TEMA).toString());
        /*for (Statistics objeto : array_statistics){
            System.out.println(objeto);
        }*/

    }
}