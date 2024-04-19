package com.datn.monolithic.repository;

import com.datn.monolithic.entity.Picture;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Picture, Long> {
}
