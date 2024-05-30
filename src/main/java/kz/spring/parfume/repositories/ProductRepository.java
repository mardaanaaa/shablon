package kz.spring.parfume.repositories;

import kz.spring.parfume.entities.Product;
import kz.spring.parfume.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByBrandId(Long id);
}
