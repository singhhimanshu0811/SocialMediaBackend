package com.social.repositories;

import com.social.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepsitory extends JpaRepository<Group, Long> {
}
