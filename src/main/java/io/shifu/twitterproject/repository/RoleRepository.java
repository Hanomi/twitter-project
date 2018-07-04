package io.shifu.twitterproject.repository;

import io.shifu.twitterproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
