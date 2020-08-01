package springadvanced.exam.unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;
import springadvanced.exam.user.repository.UserRoleRepository;
import springadvanced.exam.user.service.UserEntityService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserEntityService userEntityService;
    private UserEntity userEntity;

    @Mock
    UserEntityRepository userEntityRepository;
    @Mock
    UserRoleRepository userRoleRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void init() {
        userEntityService = new UserEntityService(userEntityRepository, userRoleRepository,
                modelMapper, passwordEncoder);
         this.userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setUsername("Pesho");
        userEntity.setPassword("123");
        userEntity.setEmail("pesho@pesho.com");
        userEntity.setPersonalDiscount(0.0);
        userEntity.setRoles(List.of(new UserRole("ROLE_USER")));
        userEntity.setCart(new Cart());
        userEntity.setTotalPurchases(0);
        userEntity.setRegisteredOn(LocalDateTime.now());
    }

    @Test
    public void should_Return_True_If_User_With_Such_Username_Exists() {
        //arrange
        when(userEntityRepository.findByUsername("Pesho")).thenReturn(Optional.of(userEntity));

        //act
        boolean result = this.userEntityService.userExists("Pesho");

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void should_Return_True_If_User_With_Such_Email_Exists() {
        //arrange
        when(userEntityRepository.findByEmail("pesho@pesho.com")).thenReturn(Optional.of(userEntity));

        //act
        boolean result = this.userEntityService.emailExists("pesho@pesho.com");

        //assert
        Assertions.assertTrue(result);
    }


    @Test
    public void should_Return_UserDto_By_Username() {
        //arrange
        when(userEntityRepository.findByUsername("Pesho")).thenReturn(Optional.of(userEntity));

        //act
        UserEntityDto expected = this.userEntityService.findByUsername("Pesho");

        //assert
        Assertions.assertEquals(expected.getId(), userEntity.getId());
        Assertions.assertEquals(expected.getUsername(), userEntity.getUsername());
    }

    @Test
    public void should_Return_User_Password() {
        //arrange
        when(userEntityRepository.findByUsername("Pesho")).thenReturn(Optional.of(userEntity));

        //act
        String password = this.userEntityService.getUserOldPassword("Pesho");

        //assert
        Assertions.assertEquals(password, userEntity.getPassword());
    }

    @Test
    public void should_Return_All_Users(){
        //arrange
        when(userEntityRepository.findAll()).thenReturn(List.of(userEntity));

        //act
        List<UserEntityDto> result = this.userEntityService.getAllUsers();

        //assert
        Assertions.assertEquals(1, result.size());
        UserEntityDto expected = result.get(0);
        Assertions.assertEquals(expected.getId(), userEntity.getId());
        Assertions.assertEquals(expected.getUsername(), userEntity.getUsername());

    }

}
