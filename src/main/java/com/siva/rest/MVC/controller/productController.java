package com.siva.rest.MVC.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siva.rest.MVC.Service.ProductService;
import com.siva.rest.MVC.model.Product;

@Controller
public class productController {

    @Autowired
    private ProductService productService;

    // 🔹 Show Add Product Page
    @GetMapping("/products")
    public String getProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    // 🔹 Save OR Update Product
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile file,
                              RedirectAttributes redirectAttributes)
            throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // If new image uploaded
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            file.transferTo(new File(uploadDir + fileName));
            product.setImageName(fileName);
        } 
        // If updating without new image
        else if (product.getId() != null) {
            Product existing = productService.getProductById(product.getId()).orElse(null);
            if (existing != null) {
                product.setImageName(existing.getImageName());
            }
        }

        productService.saveProduct(product);

        redirectAttributes.addFlashAttribute("message",
                "Product saved successfully!");

        return "redirect:/products/all";
    }

    // 🔹 Show All Products
    @GetMapping("/products/all")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "all-products";
    }

    // 🔹 Delete Product
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {

        productService.deleteProductById(id);

        redirectAttributes.addFlashAttribute("message",
                "Product deleted successfully!");

        return "redirect:/products/all";
    }

    // 🔹 Edit Product Page
    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "update-product";
        }

        return "redirect:/products/all";
    }

    // 🔹 Search
    @GetMapping("/products/search")
    public String showSearchPage() {
        return "search-product";
    }

    @GetMapping("/products/find")
    public String searchProduct(@RequestParam Long id, Model model) {

        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        } else {
            model.addAttribute("product", null);
            model.addAttribute("id", id);
        }

        return "product-details";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products/all";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}