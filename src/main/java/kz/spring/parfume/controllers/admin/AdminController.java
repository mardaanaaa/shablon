package kz.spring.parfume.controllers.admin;

import kz.spring.parfume.builders.ProductBuilder;
import kz.spring.parfume.entities.Brand;
import kz.spring.parfume.entities.Product;
import kz.spring.parfume.facades.StorageFacade;
import kz.spring.parfume.observers.AdminObservable;
import kz.spring.parfume.observers.AdminObserver;
import kz.spring.parfume.services.BrandService;
import kz.spring.parfume.services.ProductService;
import kz.spring.parfume.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
//@PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController implements AdminObserver {

    @Value("${products.images.path}")
    private String productImagesPath;

    @Value("${brands.images.path}")
    private String brandImagesPath;

    private final AdminObservable adminObservable;

    private final BrandService brandService;
    private final ProductService productService;
    private final StorageFacade storageFacade;

    @Override
    public void update() {
        System.out.println("Был создан продукт");
    }

    @GetMapping
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products/index";
    }

    @GetMapping("/brands")
    public String brandsPage(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "admin/brands/index";
    }


    // product func

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("brands", brandService.findAll());
        return "admin/products/create";
    }

    @PostMapping("products/store")
    public String store(@RequestParam(name = "name") String name,
                        @RequestParam(name = "price") Double price,
                        @RequestParam(name = "description") String description,
                        @RequestParam(name = "image") MultipartFile image,
                        @RequestParam(name = "brand_id") Long brandId) {

        Brand brand = brandService.findById(brandId);
        try{
            Product product = new ProductBuilder() {
                @Override
                protected Product createProduct() {
                    return null;
                }
            }
                    .setName(name)
                    .setPrice(price)
                    .setDescription(description)
                    .setBrand(brand)
                    .build();

            if (image != null && !image.isEmpty()) {
                String imagePath = storageFacade.saveFile(image, productImagesPath);
                product.setImage(imagePath);
            }

            product.setBrand(brandService.findById(brandId));

            product.setDescription(description);
            productService.save(product);

            adminObservable.notifyObservers();
            return "redirect:/admin/products";
        }
        catch (Exception e){
            return "redirect:/admin/products/create";
        }
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("brands", brandService.findAll());
        return "admin/products/edit";
    }

    @PostMapping("/products/update")
    public String updateProduct(@RequestParam(name = "name") String name,
                                 @RequestParam(name = "description") String description,
                                 @RequestParam(name = "price") double price,
                                 @RequestParam(name = "brand") Long brandId,
                                 @RequestParam(name = "image") MultipartFile image,
                                 @RequestParam(name = "id") Long id) {
        Product product = productService.findById(id);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setBrand(brandService.findById(brandId));

        if(storageFacade.exists(product.getImage())) {
            if (image != null && !image.isEmpty()) {
                try {
                    storageFacade.deleteFile(product.getImage());
                    product.setImage(storageFacade.saveFile(image, productImagesPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        productService.save(product);

        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if(storageFacade.exists(product.getImage())) {
            try {
                storageFacade.deleteFile(product.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.deleteById(product.getId());
        return "redirect:/admin/products";
    }

    // brand func

    @GetMapping("/brands/create")
    public String createBrand() {
        return "admin/brands/create";
    }

    @PostMapping("/brands/store")
    public String storeBrand(@RequestParam(name = "name") String name,
                             @RequestParam(name = "description") String description,
                             @RequestParam(name = "country") String country,
                             @RequestParam(name = "rating") double rating,
                             @RequestParam(name = "image") MultipartFile image) {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescription(description);
        brand.setCountry(country);
        brand.setRating(rating);

        try {
            brand.setImage(storageFacade.saveFile(image, brandImagesPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        brandService.save(brand);

        return "redirect:/admin/brands";
    }

    @GetMapping("/brands/edit/{id}")
    public String editBrand(Model model, @PathVariable("id") Long id) {
        model.addAttribute("brand", brandService.findById(id));
        return "admin/brands/edit";
    }

    @PostMapping("/brands/update")
    public String updateBrand(@RequestParam(name = "name") String name,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "country") String country,
                              @RequestParam(name = "rating") double rating,
                              @RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "id") Long id) {
        Brand brand = brandService.findById(id);
        brand.setName(name);
        brand.setDescription(description);
        brand.setCountry(country);
        brand.setRating(rating);

        if(storageFacade.exists(brand.getImage())) {
            if (image != null && !image.isEmpty()) {
                try {
                    storageFacade.deleteFile(brand.getImage());
                    brand.setImage(storageFacade.saveFile(image, brandImagesPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        brandService.save(brand);

        return "redirect:/admin/brands";
    }

    @PostMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id) {
        Brand brand = brandService.findById(id);
        if(storageFacade.exists(brand.getImage())) {
            try {
                storageFacade.deleteFile(brand.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        brandService.deleteById(brand.getId());
        return "redirect:/admin/brands";
    }

}
