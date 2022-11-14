package com.example.mungmatebackend.domain.post.repository;

import com.example.mungmatebackend.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  @Query(value = "select * from post where id < :id and is_active = 1 and location = :location order by id desc limit :size",
      nativeQuery = true)
  List<Post> findAllListNative(Long size, Long id, String location);
  @Query(value = "select * from post where id < :id and is_active = 1 and location = :location and category = :category order by id desc limit :size",
      nativeQuery = true)
  List<Post> findByListNative(Long size, Long id, String location, String category);
}
