package com.example.movie_list_assessment;

public class MovieModelClass {

    String id;
    String name;
    String image;
    String overview;

    public MovieModelClass(String id, String name, String image, String overview) {

        this.id = id;
        this.name = name;
        this.image = image;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MovieModelClass() {


    }
}
