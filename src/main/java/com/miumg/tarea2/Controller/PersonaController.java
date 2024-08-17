package com.miumg.tarea2.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miumg.tarea2.Model.Persona;
import com.miumg.tarea2.Services.PersonaService;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.getAllPersonas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonaById(@PathVariable int id) {
        Persona persona = personaService.getPersonaById(id);
        if (persona != null) {
            return new ResponseEntity<>(persona, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Persona no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createPersona(@RequestBody Persona persona) {
        personaService.savePersona(persona);
        return new ResponseEntity<>("Persona creada exitosamente", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePersona(@PathVariable int id, @RequestBody Persona persona) {
        Persona existingPersona = personaService.getPersonaById(id);
        if (existingPersona != null) {
            persona.setId(id);
            personaService.updatePersona(persona);
            return new ResponseEntity<>("Persona actualizada exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Persona no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable int id) {
        Persona existingPersona = personaService.getPersonaById(id);
        if (existingPersona != null) {
            personaService.deletePersona(id);
            return new ResponseEntity<>("Persona eliminada exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Persona no encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
