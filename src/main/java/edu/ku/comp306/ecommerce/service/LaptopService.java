package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.LaptopProductDTO;
import edu.ku.comp306.ecommerce.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaptopService {

    private final LaptopRepository laptopRepository;

    public List<LaptopProductDTO> getLaptopsWithProductDetailsByResolution(String resolution) {
        return laptopRepository.findLaptopWithProductDetailsByResolution(resolution);
    }
}