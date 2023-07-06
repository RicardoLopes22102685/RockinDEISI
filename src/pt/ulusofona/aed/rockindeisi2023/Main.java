package pt.ulusofona.aed.rockindeisi2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static ArrayList<Song> songs = new ArrayList<>();
    static ArrayList<Artist> artists = new ArrayList<>();
    static ArrayList<Statistics> array_statistics = new ArrayList<>();
    static HashMap<String, Song> songs_Map = new HashMap<>();
    static HashMap<String, Song> song_details_Map = new HashMap<>();
    static HashMap<String, Artist> artists_Map = new HashMap<>();
    static HashMap<String, Artist> artist_Map = new HashMap<>();


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

    static ArrayList<String> parseMultipleArtists(String artists_String) {
        /* Patter "monta" o padrão a pocurar com a sintaxe limitador(.*?)limitador
        Ou seja, tudo o que está dentro dos limitadores irá ser procurado
        Matcher faz a procura na string do padrão definido no Pattern
        Matcher group são os grupos de cada tipo de padrão procurado,
        group(1) pertence ao primeiro padrão e assim por diante
         */
        String clean_String = artists_String.trim().substring(1, artists_String.length() - 1);
        Pattern pattern = Pattern.compile("'(.*?)'|\"\"(.*?)\"\"");
        Matcher matcher = pattern.matcher(clean_String);
        ArrayList<String> artists = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Single quotes case
                String name = matcher.group(1);
                artists.add(name);
            } else if (matcher.group(2) != null) {
                // Double quotes case
                String name = matcher.group(2);
                artists.add(name);
            }
        }
        return artists;
    }

    static QueryResult execute(String command) {
        String[] elementos = command.split(" ");
        return Functions.execute(elementos);
    }

    static Query parseCommand(String command) {
        return null;
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
        int linhasOk = 0, linhasNok = 0, lineCount = 0;
        int primeira_linha_nok = -1;
        while (scanner.hasNext()) {
            lineCount++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            if (song_elements.length != 3 || songs_Map.containsKey(song_elements[0].trim())) {//Só pode haver uma música com o mesmo ID
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = lineCount;
                }
                linhasNok++;
                continue;
            }
            String songID = song_elements[0].trim();
            String song_name = song_elements[1].trim();
            int song_year = Integer.parseInt(song_elements[2].trim());
            linhasOk++;
            Song song = new Song(songID, song_name, song_year);
            songs_Map.put(songID, song);
            songs.add(song);
        }
        array_statistics.add(0, new Statistics("songs.txt", linhasOk, linhasNok, primeira_linha_nok));
        //Leitura song_details.txt
        File song_details_File = new File(folder, "song_details.txt");
        try {
            scanner = new Scanner(song_details_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        linhasOk = linhasNok = lineCount = 0;
        primeira_linha_nok = -1;
        while (scanner.hasNext()) {
            lineCount++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            if (song_elements.length != 7) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = lineCount;
                }
                linhasNok++;
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
                    primeira_linha_nok = lineCount;
                }
                linhasNok++;
                continue;
            }
            linhasOk++;
            duration_string = Functions.process_duration(duration_string);
            //Verificar se é necessário usar math para round up/down
            short explicit = Short.parseShort(explicit_String);
            int popularity = Integer.parseInt(popularity_String);
            BigDecimal dance_rate = new BigDecimal(dance_rate_String);
            BigDecimal vivacity = new BigDecimal(vivacity_String);
            BigDecimal mean_volume = new BigDecimal(mean_volume_String);
            Song song_retrieved = songs_Map.get(song_ID);
            Song song_dtl_to_Map = new Song(song_ID, duration_string, explicit, popularity, dance_rate, vivacity, mean_volume);
            song_dtl_to_Map.songName = song_retrieved.songName;
            song_dtl_to_Map.songYear = song_retrieved.songYear;
            songs_Map.replace(song_ID, song_dtl_to_Map);
        }
        array_statistics.add(1, new Statistics("song_details.txt", linhasOk, linhasNok, primeira_linha_nok));
        //Leitura ficheiro song_artists.txt
        File song_artists_File = new File(folder, "song_artists.txt");
        try {
            scanner = new Scanner(song_artists_File);
        } catch (FileNotFoundException e) {
            return false;
        }
        linhasOk = linhasNok = lineCount = 0;
        primeira_linha_nok = -1;
        while (scanner.hasNext()) {
            lineCount++;
            String linha = scanner.nextLine();
            String[] song_elements = linha.split("@");
            if (song_elements.length != 2 || !songs_Map.containsKey(song_elements[0].trim())) {
                if (primeira_linha_nok == -1) {
                    primeira_linha_nok = lineCount;
                }
                linhasNok++;
                continue;
            } //Song é valida e existe
            String song_ID = song_elements[0].trim();
            ArrayList<String> line_artists = parseMultipleArtists(song_elements[1]);
            int count = 0;
            for (String artist_to_add : line_artists) {
                Artist artist_temp = new Artist(artist_to_add, 1);
                if (artist_Map.putIfAbsent(artist_to_add, artist_temp) == null) {
                    artists.add(artist_temp);
                } else {
                    Artist artist_to_modify = artist_Map.get(artist_to_add);
                    artist_to_modify.numMusicas++;
                    artist_Map.replace(artist_to_add, artist_to_modify);
                }
                count++;
            }
            Song song_to_modify = songs_Map.get(song_ID);
            song_to_modify.numArtists = count;
            songs_Map.replace(song_ID, song_to_modify);
            linhasOk++;
        }
        array_statistics.add(2, new Statistics("song_artists.txt", linhasOk, linhasNok, primeira_linha_nok));
        songs.replaceAll(song -> songs_Map.get(song.songID));
        for (Artist artist_to_modify : artists) {
            artist_to_modify.numMusicas = artist_Map.get(artist_to_modify.nomeArtista).numMusicas;
        }
        return true;
    }

    public static void main(String[] args) {
        long time_ini = System.currentTimeMillis();
        if (!loadFiles(new File("."))) {
            return;
        }
        /*
        System.out.println(songs_Map.get("7GIMqa2FksLo2EXNacnIlJ"));
        for (Song song : songs) {
            if (song.song_ID.equals("7GIMqa2FksLo2EXNacnIlJ")) {
                System.out.println(song);
            }
        }
        System.out.println(System.currentTimeMillis() - time_ini); */
        Scanner input = new Scanner((System.in));
        String line = input.nextLine();
        while (line != null && !line.equals("EXIT")) {
            QueryResult executed = execute(line);
            if (executed == null) {
                System.out.println("Illegal command. Try again");
            } else {
                System.out.println(executed.result);
                System.out.println("(took " + executed.time + " ms)");
                System.out.println();
            }
            line = input.nextLine();

        }

    }
}