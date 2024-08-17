package com.miumg.tarea2.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.miumg.tarea2.Model.Persona;


@Service
public class PersonaService {
    private static final String FILE_NAME = "persona.txt";
    


    public List<Persona> getAllPersonas() {
        List<Persona> personas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                personas.add(new Persona(
                    Integer.parseInt(data[0]), 
                    data[1], 
                    data[2], 
                    data[3], 
                    data[4]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public Persona getPersonaById(int id) {
        return getAllPersonas().stream()
                .filter(persona -> persona.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void savePersona(Persona persona) {
        List<Persona> personas = getAllPersonas();
        personas.add(persona);
        writeToFile(personas);
    }

    public void updatePersona(Persona persona) {
        List<Persona> personas = getAllPersonas();
        personas.removeIf(p -> p.getId() == persona.getId());
        personas.add(persona);
        writeToFile(personas);
    }

    public void deletePersona(int id) {
        List<Persona> personas = getAllPersonas();
        personas.removeIf(persona -> persona.getId() == id);
        writeToFile(personas);
    }

    private void writeToFile(List<Persona> personas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Persona persona : personas) {
                writer.write(
                    persona.getId() + "," + 
                    persona.getNombre() + "," + 
                    persona.getApellido() + "," + 
                    persona.getTelefono() + "," + 
                    persona.getDireccion()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
