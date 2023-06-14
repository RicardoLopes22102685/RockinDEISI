package pt.ulusofona.aed.rockindeisi2023;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {
    public static ArrayList<String> process_Artists(String artists_String) {
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

    public static QueryResult execute(String command) {
        String[] elementos = command.split(" ");
        switch (elementos[0]) {
            case "COUNT_SONGS_YEAR" -> {
                return null;
            }
            case "COUNT_DUPLICATE_SONGS_YEAR" -> {
                return null;
            }
            case "GET_SONGS_BY_ARTIST" -> {
                return null;
            }
            case "GET_MOST_DANCEABLE" -> {
                return null;
            }
            case "GET_ARTISTS_ONE_SONG" -> {
                return null;
            }
            case "GET_TOP_ARTISTS_WITH_SONGS_BETWEEN" -> {
                return null;
            }
            case "MOST_FREQUENT_WORDS_IN_ARTIST_NAME" -> {
                return null;
            }
            case "GET_UNIQUE_TAGS" -> {
                return null;
            }
            case "GET_UNIQUE_TAGS_IN_BETWEEN_YEARS" -> {
                return null;
            }
            case "GET_RISING_STARS" -> {
                return null;
            }
            case "ADD_TAGS" -> {
                return null;
            }
            case "REMOVE_TAGS" -> {
                return null;
            }
            case "GET_ARTISTS_FOR_TAG" -> {
                return null;
            }
            default -> {
                return null;
            }

        }

    }
}
