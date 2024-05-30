package kz.spring.parfume.repositories;

import kz.spring.parfume.entities.Brand;
import kz.spring.parfume.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    
}
