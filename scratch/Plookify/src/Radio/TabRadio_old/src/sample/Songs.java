package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Salman on 16-Mar-16.
 */


public class Songs extends Main{
    private final SimpleStringProperty titleList;
    private final SimpleStringProperty artistList;
    private final SimpleStringProperty genreList;
    private final SimpleStringProperty albumList;

    public Songs(String titleL, String artistL, String genreL, String albumL){
        this.titleList = new SimpleStringProperty(titleL);
        this.artistList = new SimpleStringProperty(artistL);
        this.genreList = new SimpleStringProperty(genreL);
        this.albumList = new SimpleStringProperty(albumL);
    }

    public String getTitle(){
        return titleList.get();
    }

    public String getArtist(){
        return artistList.get();
    }

    public String getGenre(){
        return genreList.get();
    }

    public String getAlbum(){
        return albumList.get();
    }

    public void  setTitle(String titleL){
        titleList.set(titleL);
    }

    public void setArtist(String artistL){
        artistList.set(artistL);
    }

    public void setGenre(String genreL){
        genreList.set(genreL);
    }

    public void setAlbum(String albumL){
        albumList.set(albumL);
    }


}
