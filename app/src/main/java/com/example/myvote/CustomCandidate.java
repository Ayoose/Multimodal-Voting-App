package com.example.myvote;

public class CustomCandidate {

    private int id;
    private String ids;
    private String names;
    private String votes;
    private byte[] images;

    public CustomCandidate(String ids, String names, String votes, byte[] images, int id) {
        this.ids = ids;
        this.names = names;
        this.votes = votes;
        this.images = images;
        this.id = id;
    }

    public CustomCandidate(String ids, String names, byte[] images, int id) {
        this.ids = ids;
        this.names = names;
        this.images = images;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
