package com.alcorteccino.peluqueria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alcorteccino.peluqueria.model.*;
import com.alcorteccino.peluqueria.service.*;

@Controller
public class InicioController {

    private final UsuarioService usuarioServicio;
    private final PersonalService personalServicio;
    private final ClienteService clienteServicio;

    public InicioController(UsuarioService usuarioService,PersonalService personalService, ClienteService clienteService) {
        this.usuarioServicio = usuarioService;
		this.personalServicio = personalService;
		this.clienteServicio = clienteService;
    }

    @GetMapping("/")
    public String inicio() {
        return "inicio";
    }

    @PostMapping("/login")
    public String login(@RequestParam String cuenta,
                        @RequestParam String contrasenia,
                        Model model) {
        Usuario usuario = usuarioServicio.autenticar(cuenta, contrasenia);
        Personal personal= personalServicio.obtenerPersonalPorId(usuario.getId());
        Cliente cliente= clienteServicio.obtenerClientePorId(usuario.getId());
        model.addAttribute("usuario", usuario);
        
        if (usuario == null) {
            model.addAttribute("error", "Cuenta o contraseña incorrecta.");
            return "inicio";
        } else if (cliente!=null) {
        	if (cliente.getCategoria().equals("VIP")) {
        		model.addAttribute("vip", "true");
				return "menu-vip";
			}else {
				return "menu-cliente";				
			}
		}else {
			return "menu-personal";
		}
    }
    @GetMapping("/logout")
    public String cerrarSesion() {
    	return "inicio";
    }

    @GetMapping("/menu")
    public String menu(@RequestParam(required = false) Integer id,
                       @RequestParam(required = false) String cuenta,
                       Model model) {
        model.addAttribute("usuario", resolverUsuario(id, cuenta));
        Personal personal= personalServicio.obtenerPersonalPorId(id);
        Cliente cliente= clienteServicio.obtenerClientePorId(id);
        if (cliente !=null) {
        	if (cliente.getCategoria().equals("VIP")) {
        		model.addAttribute("vip", "true");
        		return "menu-vip";
        	}else {
        		return "menu-cliente";				
        	}
        }else {
        	return "menu-personal";
			
		}
    }

    @GetMapping("/citas")
    public String citas(@RequestParam(required = false) Integer id,
                        @RequestParam(required = false) String cuenta,
                        Model model) {
        model.addAttribute("usuario", resolverUsuario(id, cuenta));
        
        Personal personal= personalServicio.obtenerPersonalPorId(id);
        Cliente cliente= clienteServicio.obtenerClientePorId(id);
        if (cliente!=null) {
			model.addAttribute(cliente.getCategoria());
		}
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
            ok = usuarioServicio.actualizarUsuario(usuario);
        } else {
            ok = usuarioServicio.actualizarDatosSinContrasenia(usuario);
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
            usuario = usuarioServicio.buscarUsuarioPorId(id);
        }

        if (usuario == null && cuenta != null && !cuenta.isBlank()) {
            usuario = usuarioServicio.buscarUsuarioPorCuenta(cuenta);
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
