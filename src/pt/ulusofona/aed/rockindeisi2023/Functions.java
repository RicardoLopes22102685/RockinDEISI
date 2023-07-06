package pt.ulusofona.aed.rockindeisi2023;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {


    public static String process_duration(String duration) {
        String result = "";
        long duration_to_long = Long.parseLong(duration);
        long total_seconds = duration_to_long / 1000;
        long minutes = total_seconds / 60;
        long seconds = total_seconds % 60;
        if (seconds < 10) {
            result = minutes + ":0" + seconds;
        } else {
            result = minutes + ":" + seconds;
        }
        return result;
    }

    public static QueryResult execute(String[] commands) {
        switch (commands[0]) {
            case "COUNT_SONGS_YEAR" -> {
                return new QueryResult("OLA", 10);
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
