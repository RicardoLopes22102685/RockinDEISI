package pt.ulusofona.aed.rockindeisi2023;

public class Functions {
    public static String process_Artists(String artists_String) {
        if (artists_String.startsWith("['")) {
            return artists_String.replace("['", "").replace("']", "");
        }
        return "";
    }
}
