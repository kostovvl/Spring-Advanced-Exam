package springadvanced.exam.cart.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.cart.domain.CartDto;
import springadvanced.exam.cart.repository.CartRepository;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.user.service.UserEntityService;

import java.util.Map;

@Service
public class CartService {

    private final ProductService productService;
    private final UserEntityService userEntityService;
    private final CartRepository cartRepository;
    private final ModelMapper mapper;

    public CartService(ProductService productService, UserEntityService userEntityService,
                       CartRepository cartRepository, ModelMapper mapper) {
        this.productService = productService;
        this.userEntityService = userEntityService;
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }

    public void addToCart(String id, String username) {

        CartDto cartDto = this.userEntityService.findByUsername(username).getCart();
        ProductDto productDto = this.productService.findById(id);
        cartDto.addProduct(productDto.getTitle());

        this.cartRepository.save(this.mapper.map(cartDto, Cart.class));

        this.productService.increaseProductSales(id);
        this.userEntityService.increasePurchases(username);

    }

    public int getTotalProductsInCart(String username) {
        CartDto cartDto = this.userEntityService.findByUsername(username).getCart();

        int result = 0;

        for (Map.Entry<String, Integer> productDtoIntegerEntry : cartDto.getProducts().entrySet()) {
            result += productDtoIntegerEntry.getValue();
        }

        return result;
    }
}
