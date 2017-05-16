package iss.nus.edu.medipalappln.activity;

public class dashboardcontent {
    private String name;
    private int numOfSongs;
    private int thumbnail;

    public dashboardcontent() {
    }

    public dashboardcontent(String name, int thumbnail) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
