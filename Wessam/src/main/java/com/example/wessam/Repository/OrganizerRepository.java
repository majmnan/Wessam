package com.example.wessam.Repository;

import com.example.wessam.Model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer,Integer> {
    Organizer findOrganizerById(Integer id);
}
