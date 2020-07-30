package springadvanced.exam.cart.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.cart.domain.CartDto;
import springadvanced.exam.cart.repository.CartRepository;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.domain.ProductCartView;
import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.user.service.UserEntityService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<ProductCartView> getAllProducts(String cartId, Double personalDiscount) {

        List<ProductCartView> result = new ArrayList<>();

        Cart cart = this.cartRepository.findById(cartId).orElse(null);
        for (Map.Entry<String, Integer> product : cart.getProducts().entrySet()) {

            ProductCartView productCartView = new ProductCartView();
            productCartView.setTitle(product.getKey());
            productCartView.setQuantity(product.getValue());
            ProductDto productDto = this.productService.findByTitle(product.getKey());

            System.out.println();

            double totalDiscount = personalDiscount + productDto.getAdminDiscount();
            if (totalDiscount > productDto.getMaxDiscountPercent()) {
                totalDiscount = productDto.getMaxDiscountPercent();
            }

            BigDecimal discount = productDto.getPrice().multiply(new BigDecimal(totalDiscount))
                    .divide(new BigDecimal("100"));

            BigDecimal price = productDto.getPrice().subtract(discount);


            productCartView.setTotalPrice(price.multiply(new BigDecimal(product.getValue())));
            result.add(productCartView);
        }

        return result;
    }

    public BigDecimal totalCartPrice(String cartId, Double personalDiscount) {
        List<ProductCartView> products = getAllProducts(cartId, personalDiscount);
        BigDecimal totalPrice = new BigDecimal("0");
        for (ProductCartView product : products) {
            totalPrice = totalPrice.add(product.getTotalPrice());
        }

        return totalPrice;
    }

    public void buyAllProducts(String cartId) {
        Cart cart = this.cartRepository.getOne(cartId);
        cart.setProducts(new HashMap<>());

        this.cartRepository.save(cart);
    }

    public void removeProduct(String cartId, String title) {

        Cart cart = this.cartRepository.getOne(cartId);
        Map<String, Integer> products = cart.getProducts();
        products.remove(title);
        cart.setProducts(products);

        this.cartRepository.save(cart);

    }
}
