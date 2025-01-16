package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.LaptopProductDTO;
import edu.ku.comp306.ecommerce.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: This class is just to show an example to how to use DTOs to return query results
@RestController
@RequiredArgsConstructor
public class LaptopController {

    private final LaptopService laptopService;

    @GetMapping("/laptops")
    public List<LaptopProductDTO> getLaptops(@RequestParam String resolution) {
        return laptopService.getLaptopsWithProductDetailsByResolution(resolution);
    }

}