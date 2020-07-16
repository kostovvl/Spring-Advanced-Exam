package springadvanced.exam.cart.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.cart.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ModelMapper mapper;

    public CartService(CartRepository cartRepository, ModelMapper mapper) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }
}
