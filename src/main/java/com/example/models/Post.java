package com.example.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//чтобы сказать что у нас не обычный класс, а модель, нужно прописать аннотацию @Entity
//эта модель создаст новую табличку, если такая не существует

//эта модель только создает!! табличку в базе данных
//!! мы не можем манипулировать этой конкретно! табличкой! для этого нужно создать интерфейс
//для каждой модели нужно создать новый репозиторий
//репозиторий - это интерфейс, обычно называют имя модели+реп PostRepository

//хранимый обьект бизнес логики
@Entity
public class Post {
    //каждая переменная внутри класса будет отвечать за поле внутри таблички
    //уникальный идентификатор обычно указывают тип лонг
    @Id //это наша аннотация уникальный идентификатор
    @GeneratedValue(strategy = GenerationType.AUTO) //при добавлении новой записи генерирует новое значение ( авто инкремент?)
    private Long id; //наш праймари кей

    //создаем поля
    private  String title, anons, full_text;
    private int views;

    //создаем геттеры сеттеры для полей
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Post(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }

    public Post() {
    }
}
