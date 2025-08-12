package com.herkat.repositories;

import com.herkat.models.SocialMedia;
import com.herkat.models.SocialMediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Integer> {

    Optional<SocialMedia> findByType(SocialMediaType type);
}
