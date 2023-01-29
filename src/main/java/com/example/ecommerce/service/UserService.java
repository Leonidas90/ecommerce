package com.example.ecommerce.service;

import com.example.ecommerce.converter.SignUpDtoToAddress;
import com.example.ecommerce.converter.SignUpDtoToEntityConverter;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    AddressService addressService;

    @PostConstruct
    private void initAdminAccount(){
        if (usersRepository.findByEmail("admin").isPresent()){
            return;
        }

        User admin = new User();
        admin.setEmail("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPassword("admin");
        admin.setType("admin");
        Address address = new Address();
        address.setAddress("34 Street");
        address.setCity("NY");
        address.setTelephone("123456789");
        address.setCountry("USA");
        admin.setAddress(address);
        usersRepository.save(admin);
    }

    private final UserRepository usersRepository;
    private final SignUpDtoToEntityConverter usersConverter;
    private final SignUpDtoToAddress addressConverter;

    public UserService(UserRepository usersRepository, SignUpDtoToEntityConverter usersConverter, SignUpDtoToAddress addressConverter) {
        this.usersRepository = usersRepository;
        this.usersConverter = usersConverter;
        this.addressConverter = addressConverter;
    }

    public SignInResponseDto signIn(SignInDto dto){
        User user = getUserByEmail((dto.email()));

        if (!user.getPassword().equals(dto.password())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong password");
        }

        return new SignInResponseDto(user.getId().toString(), user.getType());
    }

    public void signUp(SignUpDto dto){
        Optional<User> users = usersRepository.findByEmail(dto.email());
        if (users.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exist");
        }

        User user = usersConverter.convert(dto);
        user.setAddress(getAddress(dto));
        usersRepository.save(user);
    }

    public User getUser(String id){
        try {
            Long userId = Long.parseLong(id);
            return  usersRepository
                    .findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid product id");
        }
    }

    private Address getAddress(SignUpDto dto){
        Optional<Address> address = addressService.findAddress(dto.address().address(), dto.address().city(), dto.address().telephone());
        return (address.isPresent() ? address.get() : addressConverter.convert(dto));
    }

    private User getUserByEmail(String email){
        return usersRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

}
