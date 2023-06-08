package pt.ulusofona.aed.rockindeisi2023;

import java.math.BigDecimal;

public class Song {
    String song_ID;
    String song_name;
    int song_year;
    String duration;
    Short explicit;
    int popularity;
    BigDecimal dance_rate;
    BigDecimal vivacity;
    BigDecimal mean_volume;

    short num_Artists;

    public Song(String song_ID, String song_name, int song_year) {
        this.song_ID = song_ID;
        this.song_name = song_name;
        this.song_year = song_year;
    }

    public Song() {
    }

    @Override
    public String toString() {
        if (song_year < 1995) {
            return song_ID + " | " + song_name + " | " + song_year;
        } else if (song_year < 2000) { //ATENÇÃO é implicito que song_year >=1995 neste ponto
            return song_ID + " | " + song_name + " | " + song_year + " | " + duration + " | " + popularity;
        }
        return song_ID + " | " + song_name + " | " + song_year + " | " + duration + " | " + popularity +  " | " + num_Artists;
    }
}
