package kz.spring.parfume.services;

import kz.spring.parfume.entities.Product;
import kz.spring.parfume.repositories.BrandRepository;
import kz.spring.parfume.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements Iterable<Product> {

    private final ProductRepository productRepository;

    @Cacheable("allProducts")
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Cacheable(value = "productById", key = "#id")
    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public List<Product> findAllByBrandId(Long id){
        return productRepository.findAllByBrandId(id);
    }

    @Override
    public Iterator<Product> iterator() {
        return findAll().iterator();
    }
}
