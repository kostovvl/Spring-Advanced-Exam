package springadvanced.exam.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.product.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }
}
