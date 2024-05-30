package kz.spring.parfume.services;

import kz.spring.parfume.entities.Brand;
import kz.spring.parfume.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> findAll(){
        return brandRepository.findAll();
    }

    public Brand findById(Long id){
        return brandRepository.findById(id).orElse(null);
    }

    public Brand save(Brand brand){
        return brandRepository.save(brand);
    }

    public void deleteById(Long id){
        brandRepository.deleteById(id);
    }
}
