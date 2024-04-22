package com.datn.monolithic.repository;

import com.datn.monolithic.entity.Picture;
import com.datn.monolithic.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Long> {
    List<Picture> findByUserId(Long user);
}
