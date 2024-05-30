package kz.spring.parfume.controllers;

import kz.spring.parfume.builders.ProductBuilder;
import kz.spring.parfume.entities.Brand;
import kz.spring.parfume.entities.Product;
import kz.spring.parfume.facades.StorageFacade;
import kz.spring.parfume.services.BrandService;
import kz.spring.parfume.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final StorageFacade storageFacade;

    private final BrandService brandService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("products", productService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "products/index";
    }

    @GetMapping("/brand/{id}")
    public String productsByBrand(@PathVariable("id") Long brandId, Model model) {
        model.addAttribute("products", productService.findAllByBrandId(brandId));
        model.addAttribute("brands", brandService.findAll());
        return "products/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "id") Long id){
        List<Product> relatedProducts = productService.findAllByBrandId(productService.findById(id).getBrand().getId());
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("relatedProducts", relatedProducts);
        return "products/show";
    }
}
