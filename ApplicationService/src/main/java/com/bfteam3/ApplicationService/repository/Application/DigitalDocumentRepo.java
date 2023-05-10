package com.bfteam3.ApplicationService.repository.Application;
import com.bfteam3.ApplicationService.domain.entity.Application.DigitalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalDocumentRepo extends JpaRepository<DigitalDocument, Long> {
    List<DigitalDocument> findAll();
    DigitalDocument findDigitalDocumentById(String id);
    // save
}
