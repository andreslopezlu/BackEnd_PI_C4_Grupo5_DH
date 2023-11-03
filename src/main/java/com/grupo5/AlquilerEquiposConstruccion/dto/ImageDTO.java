package com.grupo5.AlquilerEquiposConstruccion.dto;

public class ImageDTO {
    private Integer id;
    private String title;
    private String url;

    public ImageDTO() {
    }

    public ImageDTO(Integer id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public ImageDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
