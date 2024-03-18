package com.example.Surisuri_Masuri.container.repository;

import com.example.Surisuri_Masuri.container.model.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContainerRepository extends JpaRepository<Container, Integer> {

    Optional<Container> findContainerByContainerAddr(String containerAddr);

    Optional<Container> findContainerByContainerName(String containerName);

}
