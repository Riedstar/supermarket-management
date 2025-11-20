package com.example.supermarket.controller;

import com.example.supermarket.entity.Product;
import com.example.supermarket.repository.CategoryRepository;
import com.example.supermarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Product> products = productRepository.findAllWithCategory();
        model.addAttribute("products", products != null ? products : List.of());  // Null-safe
        return "dashboard";
    }

    @GetMapping("/add-product")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/dashboard";
    }

    @GetMapping("/edit-product/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            model.addAttribute("categories", categoryRepository.findAll());
            return "edit-product";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productRepository.save(product);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        List<Product> lowStock = productRepository.findLowStock();
        model.addAttribute("lowStock", lowStock != null ? lowStock : List.of());
        return "reports";
    }

    // Cart methods
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Product> cart = (Map<Long, Product>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();
        double total = cart.values().stream().mapToDouble(Product::getPrice).sum();
        model.addAttribute("cart", cart.values());
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            @SuppressWarnings("unchecked")
            Map<Long, Product> cart = (Map<Long, Product>) session.getAttribute("cart");
            if (cart == null) cart = new HashMap<>();
            cart.put(id, productOpt.get());
            session.setAttribute("cart", cart);
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Product> cart = (Map<Long, Product>) session.getAttribute("cart");
        if (cart != null) cart.remove(id);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Product> cart = (Map<Long, Product>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/cart";
        double total = cart.values().stream().mapToDouble(Product::getPrice).sum();
        model.addAttribute("cart", cart.values());
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/dashboard";
    }
    @PostMapping("/clear-cart")
public String clearCart(HttpSession session) {
    session.removeAttribute("cart");
    return "redirect:/cart";
}               
}