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
}
