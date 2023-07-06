package pt.ulusofona.aed.rockindeisi2023;

import java.math.BigDecimal;

public class Song {
    String songID;
    String songName;
    int songYear;
    String duration;
    Short explicit;
    int popularity;
    BigDecimal danceRate;
    BigDecimal vivacity;
    BigDecimal meanVolume;
    int numArtists;

    public Song(String songID, String songName, int songYear) {
        this.songID = songID;
        this.songName = songName;
        this.songYear = songYear;
    }

    public Song(String songID, String duration, Short explicit, int popularity, BigDecimal danceRate, BigDecimal vivacity, BigDecimal meanVolume) {
        this.songID = songID;
        this.duration = duration;
        this.explicit = explicit;
        this.popularity = popularity;
        this.danceRate = danceRate;
        this.vivacity = vivacity;
        this.meanVolume = meanVolume;
    }

    @Override
    public String toString() {
        if (songYear < 1995) {
            return songID + " | " + songName + " | " + songYear;
        } else if (songYear < 2000) { //ATENÇÃO é implicito que song_year >=1995 neste ponto
            return songID + " | " + songName + " | " + songYear + " | " + duration + " | " + popularity;
        }
        return songID + " | " + songName + " | " + songYear + " | " + duration + " | " + popularity +  " | " + numArtists;
    }
}
