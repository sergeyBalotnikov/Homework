package ru.mail.sergey_balotnikov.musicplayer.utils;

public class SongNameParser {
    public static String getParseSongName(String songName){
        String result = "";
        String [] nameWords = songName.split("[\\s\\., \\?]+");
        for (int i = 0; i<nameWords.length-1; i++){
                result+=nameWords[i].substring(0,1).toUpperCase()+
                        nameWords[i].substring(1, nameWords[i].length())+" ";
        }
        return result;
    }
    private SongNameParser() {
    }
}
