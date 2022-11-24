package com.example.mungmatebackend.domain.post.repository;

import com.example.mungmatebackend.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findTopByIsActive(Boolean isActive);
  List<Post> findAllByUserIdOrderByIdDesc(Long userId);

  @Query(value = "select * from post where id < :id and is_active = 1 and location = :location order by id desc limit :size",
      nativeQuery = true)
  List<Post> findAllListNative(Long size, Long id, String location);
  @Query(value = "select * from post where id < :id and is_active = 1 and location = :location and category = :category order by id desc limit :size",
      nativeQuery = true)
  List<Post> findByListNative(Long size, Long id, String location, String category);

  @Query(value = "select * from post where is_active = 1 and location = :location order by id desc limit :size",
      nativeQuery = true)
  List<Post> findAllListWithoutPostIdNative(Long size, String location);
  @Query(value = "select * from post where is_active = 1 and location = :location and category = :category order by id desc limit :size",
      nativeQuery = true)
  List<Post> findByListWithoutPostIdNative(Long size, String location, String category);


  @Query(value = "select * from post where id < :id and is_active = 1 order by id desc limit :size",
      nativeQuery = true)
  List<Post> findAll(Long size, Long id);

  @Query(value = "select * from post where is_active = 1 order by id desc limit :size",
      nativeQuery = true)
  List<Post> findAll(Long size);
}
