package com.example.api_java.service.impl;

import com.example.api_java.exception.NotFoundException;
import com.example.api_java.model.RoleName;
import com.example.api_java.model.dto.*;
import com.example.api_java.model.entity.Role;
import com.example.api_java.model.entity.UserDetail;
import com.example.api_java.repository.IRoleRepository;
import com.example.api_java.repository.IUserDetailRepository;
import com.example.api_java.security.jwt.JwtUtils;
import com.example.api_java.security.services.UserDetailsImpl;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements IBaseService<UserDetailDTO, Long>, IModelMapper<UserDetailDTO, UserDetail> {
    private final IUserDetailRepository userDetailRepository;
    final private AuthenticationManager authenticationManager;
    final private IRoleRepository roleRepository;
    final private JwtUtils jwtUtils;
    final private PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public UserDetailServiceImpl(IUserDetailRepository userDetailRepository, AuthenticationManager authenticationManager, IRoleRepository roleRepository, JwtUtils jwtUtils, PasswordEncoder encoder, ModelMapper modelMapper) {
        this.userDetailRepository = userDetailRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<?> checkLogin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate jwt to return to client
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        UserDetail entity = this.getDetail(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                entity.getFullName(),
                entity.getPhoneNumber(),
                entity.getAddress(),
                entity.getTokeDevice(),
                roles));
    }

    public ResponseEntity<?> changePass(ChangePassRequest request) {
        if (!userDetailRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username not found!"));
        }
        UserDetail user = userDetailRepository.findByUsername(request.getUsername()).get();
        System.out.println(user.getPassword());
        System.out.println(encoder.encode(request.getOldPassword()));
        if (!encoder.matches(request.getOldPassword(), user.getPassword()))
            throw new NotFoundException("Username and old password not match");
        user.setPassword(encoder.encode(request.getPassword()));
        userDetailRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Change password successfully!"));
    }

    public ResponseEntity<?> changePassByEmail(ChangePassByEmailRequest request) {
        if (!userDetailRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email not found!"));
        }
        UserDetail user = userDetailRepository.findByEmail(request.getEmail()).get();
        user.setPassword(encoder.encode(request.getPassword()));
        userDetailRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Change password successfully!"));
    }

    public ResponseEntity<?> register(SignupRequest signUpRequest) {
        if (userDetailRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userDetailRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserDetailDTO dto = modelMapper.map(signUpRequest, UserDetailDTO.class);
        save(dto);
        return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.CREATED);
    }

    private Role getRole(String strRole) {
        if (strRole == null || strRole.equals("")) {
            return roleRepository.findByName(RoleName.ROLE_NV)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        } else {
            switch (strRole) {
                case "ROLE_QL":
                    return roleRepository.findByName(RoleName.ROLE_QL)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                default:
                    return roleRepository.findByName(RoleName.ROLE_NV)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
        }
    }

    private UserDetail getDetail(Long id) {
        UserDetail detail = userDetailRepository.findById(id)
                .orElse(new UserDetail(1L, "", "", "",""
                        ,"","","",null));
        return detail;
    }

    @Override
    public List<UserDetailDTO> findAll() {
        return createFromEntities(userDetailRepository.findAll());
    }

    @Override
    public UserDetailDTO findById(Long id) {
        Optional<UserDetail> userOptional = userDetailRepository.findById(id);
        return userOptional.map(this::createFromE)
                .orElseThrow(() -> new NotFoundException(UserDetail.class, id));
    }


    private void updateDetail(UserDetailDTO signUpRequest) {

        UserDetail entity = userDetailRepository.findByUsername(signUpRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("Username =" + signUpRequest.getUsername() + " not found!"));
        saveDetail(entity, signUpRequest);
    }
    private void saveDetail(UserDetail entity, UserDetailDTO signUpRequest) {
        UserDetail userDetail = new UserDetail(entity.getUserId()
                , signUpRequest.getEmail()
                , signUpRequest.getUsername()
                , entity.getPassword()
                ,signUpRequest.getFullName() == null ? "" : signUpRequest.getFullName()
                ,signUpRequest.getPhoneNumber() == null ? "" : signUpRequest.getPhoneNumber()
                ,signUpRequest.getAddress() == null ? "" : signUpRequest.getAddress()
                ,signUpRequest.getTokeDevice() == null ? "" : signUpRequest.getTokeDevice()
                , entity.getRole());
        userDetailRepository.save(userDetail);
    }
    @Override
    public UserDetailDTO save(UserDetailDTO dto) {
        try{
            UserDetail entity = createFromD(dto);
            UserDetail savedUserDetail = userDetailRepository.save(entity);
            updateDetail(dto);
            return createFromE(savedUserDetail);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public UserDetailDTO update(Long id, UserDetailDTO signupRequest) {
        UserDetail userEntity = userDetailRepository.findById(id).orElseThrow(() -> new NotFoundException(UserDetail.class, id));
        userEntity = updateEntity(userEntity, signupRequest);
        saveDetail(userEntity, signupRequest);
        return createFromE(userDetailRepository.save(userEntity));
    }
    @Override
    public UserDetailDTO delete(Long id) {
        Optional<UserDetail> userOptional = Optional.ofNullable(userDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserDetail.class, id)));
        System.out.println(userOptional.get().getUserId());
        try {
            userDetailRepository.delete(getDetail(id));
            userDetailRepository.delete(userOptional.get());
        } catch (Exception e) {
            return  null;
        }
        return createFromE(userOptional.get());
    }
    @Override
    public UserDetail createFromD(UserDetailDTO dto) {
        UserDetail user = modelMapper.map(dto, UserDetail.class);
        user.setRole(getRole(dto.getRoleName().name()));
        user.setPassword(encoder.encode(dto.getPassword()));
        return user;
    }
    @Override
    public UserDetailDTO createFromE(UserDetail entity) {
        UserDetailDTO dto = modelMapper.map(entity, UserDetailDTO.class);
        try {
            UserDetail detail = this.getDetail(entity.getUserId());
            dto.setAddress(detail.getAddress());
            dto.setRoleName(entity.getRole().getName());
            dto.setFullName(detail.getFullName());
        } catch (Exception e) {
        }
        dto.setPassword("");
        return dto;
    }
    @Override
    public UserDetail updateEntity(UserDetail entity, UserDetailDTO dto) {
        if (entity != null && dto != null) {
            entity.setRole(roleRepository.findByName(dto.getRoleName())
                    .orElseThrow(() -> new NotFoundException(UserDetail.class, dto.getUserId())));
            if (dto.getUsername() != null)
                entity.setUsername(dto.getUsername());
            entity.setEmail(dto.getEmail());
        }
        return entity;
    }

}
