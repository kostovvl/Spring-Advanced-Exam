package springadvanced.exam.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.product.domain.ProductUserView;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.user.service.UserEntityService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserEntityService userEntityService;
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository,
                          UserEntityService userEntityService, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.userEntityService = userEntityService;
        this.mapper = mapper;
    }

    public List<ProductUserView> findAllProductsHomepage (String username) {

        Double loggedUserDiscount = this.userEntityService.findByUsername(username).getPersonalDiscount();

        return this.productRepository.findAll().stream()
                .map(p -> {
                   ProductUserView productUserView = this.mapper.map(p, ProductUserView.class);
                   double totalDiscount = p.getAdminDiscount() + loggedUserDiscount;
                   if (totalDiscount > p.getMaxDiscountPercent()) {
                       totalDiscount = p.getMaxDiscountPercent();
                   }
                    BigDecimal discountedPrice = p.getPrice().multiply(new BigDecimal(totalDiscount/100));
                   productUserView.setDiscountedPrice(discountedPrice);

                   return productUserView;
                }).collect(Collectors.toList());

    }

    public List<ProductDto> findAllProductsAdmin() {
        return this.productRepository.findAll().stream()
                .map(p -> this.mapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }
}
