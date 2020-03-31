package com.example.controllers;

import com.example.models.Post;
import com.example.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

//можем писать функции для отслеживания определенных юрл адресов

@Controller
public class BlogController {

    //нам нужна переменная которая будет ссылаться на наш интерфейс PostRepository
    @Autowired //необходимо для создания переменной которая будет ссылаться на репозиторий
    //описываем к какому репозиторию мы обращаемся PostRepository и называем переменную
    private PostRepository postRepository;

    //набираем какюй URL адрес мы хотим отслеживать
    @GetMapping("/blog")
    public String blogMain(Model model) {

        //массив данных который будет содержать все значения полученные из таблички
        //findAll(); - вытянет все данные из таблички пост
        Iterable<Post> posts = postRepository.findAll();
        //находим записи и передаем в шаблон
        // в сам шаблон будем передавать все найденные posts / по имени "posts" , а в качестве значения массив posts
        model.addAttribute("posts", posts);
        //возвращаем название шаблона который нам необходимо открыть
        return "blog-main";
    }

    //создадим новый контроллер для отслеживания юрл адреса
    //@GetMapping get - запрос это когда пользователь переходит по определенному адресу
    @GetMapping("/blog/add") //при переходе по этому адресу, будет вызываться функция blogAdd()
    public String blogAdd(Model model) {
        return "blog-add"; //вызывается вот этот шаблон. (но его нужно создать в templates
    }
    //пост, потому что в блог-адд в формочке указано пост
    //срабатывает когда нажимаем кнопку, добавить статью
    @PostMapping("/blog/add")
    //@RequestParam - получаем определенные параметры из формы
    //title - взято из поля блог-адд
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,@RequestParam String full_text, Model model) {
        //не забываем про конструктор из 3х передаваемых параметров при создании
        //создали обьект на основе класса
        Post post = new Post(title, anons, full_text);
        //обратились к репозитория и сохраняем обьект пост, в базу данных
        postRepository.save(post); //после выполнения, в табличку пост будет бодавляться статья, которую получили от пользователя.
        //какую страничку отображать? переодресуем пользователя на главную страницу.
        return "redirect:/blog";
    }


                //"/blog/{id}" - динамическое название для отслеживания
    @GetMapping("/blog/{id}") //при переходе по этому адресу, будет вызываться функция blogDetails()
                //@PathVariable(value = "id") - какой параметр из юрл получаем, тип и название для параметра
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {

        //исправляем некорректное отображение страницы статьи если такого номера нет.
        //existsById() - возвращает true если запись по ид найдена и фолс если не найдена.
        if (!postRepository.existsById(id)) { //если запись с таким id !!!не найдена, тогда отправляем на глав страницу
            return "redirect:/blog";
        }
                //получаем данные из базы данных и находим ИД , необходимо помещать запись в обьект Optional
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>(); //этот обьект res - содержит запись из базы данных
                //переводим из Optional в ArrayList (так удобнее)
        post.ifPresent(res::add);
        model.addAttribute("post", res); //полученны обьект res мы и будем передавать в шаблон.
        return "blog-details"; //вызывается этот шаблон. (но его нужно создать в templates)
    }

    //
    @GetMapping("/blog/{id}/edit") //при переходе по адресу вызывается функция
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit"; //вызывается этот шаблон. (но его нужно создать в templates)
    }


    //метод обрабатывающий данные из формы blog-edit
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons,@RequestParam String full_text, Model model) {
        //orElseThrow() - выбрасывает исключения если запись не найдена
        Post post = postRepository.findById(id).orElseThrow(RuntimeException::new); //находим существующий обьект и обновляем
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post); // post - передаем обьект который пытаемся сохранить
        return "redirect:/blog"; //переадресовываем на страницу блог
    }

    //нажимаем на кнопку, данные обрабатываются этим методом
    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(RuntimeException::new); //находим существующий обьект и обновляем
        postRepository.delete(post); //удаляем пост который нашли выше строчка
        return "redirect:/blog"; //переадресовываем на страницу блог
    }
}
