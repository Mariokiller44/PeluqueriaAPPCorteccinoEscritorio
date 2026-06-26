package com.alcorteccino.peluqueria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alcorteccino.peluqueria.model.Usuario;
import com.alcorteccino.peluqueria.service.UsuarioService;

@Controller
public class InicioController {

    private final UsuarioService usuarioService;

    public InicioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String inicio() {
        return "inicio";
    }

    @PostMapping("/login")
    public String login(@RequestParam String cuenta,
                        @RequestParam String contrasenia,
                        Model model) {
        Usuario usuario = usuarioService.autenticar(cuenta, contrasenia);

        if (usuario == null) {
            model.addAttribute("error", "Cuenta o contrasena incorrecta.");
            return "inicio";
        }

        model.addAttribute("usuario", usuario);
        return "menu";
    }

    @GetMapping("/menu")
    public String menu(@RequestParam(required = false) Integer id,
                       @RequestParam(required = false) String cuenta,
                       Model model) {
        model.addAttribute("usuario", resolverUsuario(id, cuenta));
        return "menu";
    }

    @GetMapping("/citas")
    public String citas(@RequestParam(required = false) Integer id,
                        @RequestParam(required = false) String cuenta,
                        Model model) {
        model.addAttribute("usuario", resolverUsuario(id, cuenta));
        return "citas";
    }

    @GetMapping("/servicios")
    public String servicios(@RequestParam(required = false) Integer id,
                            @RequestParam(required = false) String cuenta,
                            Model model) {
        model.addAttribute("usuario", resolverUsuario(id, cuenta));
        return "servicios";
    }

    @GetMapping("/perfil")
    public String perfil(@RequestParam(required = false) Integer id,
                         @RequestParam(required = false) String cuenta,
                         Model model) {
        model.addAttribute("usuario", resolverUsuario(id, cuenta));
        return "perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Usuario usuario,
                                RedirectAttributes ra) {
        boolean ok;

        if (usuario.getContrasenia() != null && !usuario.getContrasenia().isBlank()) {
            ok = usuarioService.actualizarUsuario(usuario);
        } else {
            ok = usuarioService.actualizarDatosSinContrasenia(usuario);
        }

        ra.addAttribute("id", usuario.getId());
        ra.addAttribute("cuenta", usuario.getCuenta());

        if (ok) {
            ra.addFlashAttribute("exito", "Perfil actualizado correctamente.");
        } else {
            ra.addFlashAttribute("error", "No se pudo actualizar el perfil. Intentalo de nuevo.");
        }

        return "redirect:/perfil";
    }

    private Usuario resolverUsuario(Integer id, String cuenta) {
        Usuario usuario = null;

        if (id != null) {
            usuario = usuarioService.buscarUsuarioPorId(id);
        }

        if (usuario == null && cuenta != null && !cuenta.isBlank()) {
            usuario = usuarioService.buscarUsuarioPorCuenta(cuenta);
        }

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setId(0);
            usuario.setNombre("Invitado");
            usuario.setApellidos("AlCorteccino");
            usuario.setEmail("bienvenido@alcorteccino.com");
            usuario.setTelefono("000-000-000");
            usuario.setCuenta("invitado");
        }

        return usuario;
    }
}
