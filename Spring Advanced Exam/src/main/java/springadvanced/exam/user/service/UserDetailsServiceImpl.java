package springadvanced.exam.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.repository.UserEntityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper mapper;

    public UserDetailsServiceImpl(UserEntityRepository userEntityRepository, ModelMapper mapper) {
        this.userEntityRepository = userEntityRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            UserEntity userEntity = this.userEntityRepository.findByUsername(username)
                    .map(u -> this.mapper.map(u, UserEntity.class))
                    .orElseThrow(() -> new UsernameNotFoundException("shit"));


            String userNameDetail = userEntity.getUsername();
            String passwordDetail = userEntity.getPassword();
            List<GrantedAuthority> roles = userEntity.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
                    .collect(Collectors.toList());

            return new User(userNameDetail, passwordDetail, roles);
        }
    }

