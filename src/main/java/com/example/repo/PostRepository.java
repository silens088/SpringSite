package com.example.repo;

import com.example.models.Post;
import org.springframework.data.repository.CrudRepository;

//extends CrudRepository - репозиторий который содержит методы которые могут добавлять, удалять, обновить, вытянть
public interface PostRepository extends CrudRepository<Post, Long> { //Long - тип данных униклаьного идентификатора
}
