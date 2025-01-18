package edu.ku.comp306.ecommerce.controller;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomepageScreenController {

    private final ProductService productService;

    public HomepageScreenController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/homepage")
    public String getHomePage(Model model) {
        List<Product> products = productService.getRandomProducts(); // Fetch random 5 products
        model.addAttribute("products", products);
        return "homepageScreen";
    }

    // Search endpoint for home and product screens
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", keyword);
        model.addAttribute("isSearch", true);
        return "product-list";  // Reuse the product-list view
    }

    @GetMapping("/sort")
    public String sortProductsByPrice(@RequestParam("order") String order,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "category", required = false) String category,
                                      Model model) {
        List<Product> products;

        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchProducts(keyword);
            model.addAttribute("searchQuery", keyword);
            model.addAttribute("isSearch", true);
        } else if (category != null && !category.isEmpty()) {
            products = productService.getProductsByCategory(category);
            model.addAttribute("category", category);
            model.addAttribute("isSearch", false);
        } else {
            products = productService.getAllProducts();  // Fallback to all products
        }

        // Sort the filtered products
        products = products.stream()
                .sorted((p1, p2) -> "asc".equalsIgnoreCase(order)
                        ? Double.compare(p1.getPrice(), p2.getPrice())
                        : Double.compare(p2.getPrice(), p1.getPrice()))
                .toList();

        model.addAttribute("products", products);
        model.addAttribute("sortOrder", order);

        return "product-list";
    }
}
