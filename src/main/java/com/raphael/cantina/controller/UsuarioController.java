// package com.raphael.cantina.controller;

// import javax.validation.Valid;

// import com.raphael.cantina.model.Usuario;
// import com.raphael.cantina.service.UsuarioServiceImpl;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.ModelMap;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestController;

// import lombok.RequiredArgsConstructor;

// @Controller
// @RequestMapping(value = "/usuarios")
// public class UsuarioController {

//     @Value("${application.name}")
//     private String applicationName;

//     // private final UsuarioServiceImpl usuarioService;
//     // private final PasswordEncoder passwordEncoder;

//     @GetMapping(value = "/cadastrar")
//     public String usuario(ModelMap model){

//         model.addAttribute("applicationName", applicationName);
//         //return "/usuario/usuario";
//         return "/login";
//     }

//     // @PostMapping
//     // @ResponseStatus(HttpStatus.CREATED)
//     // public Usuario salvar( @RequestBody @Valid Usuario usuario ){
//     //     String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
//     //     usuario.setSenha(senhaCriptografada);
//     //     return usuarioService.salvar(usuario);
//     // }

// }