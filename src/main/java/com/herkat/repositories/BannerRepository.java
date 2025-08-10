package com.herkat.repositories;

import com.herkat.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Optional<Banner> findByNameIgnoreCase(String name);

}
