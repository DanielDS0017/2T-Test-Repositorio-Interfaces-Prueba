package com.mycompany.evaluablenominasmasivas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneradorDatos {
    public static void main(String[] args) {
        String archivo = "dataset.csv";
        int totalRegistros = 500000;
        
        System.out.println("Generando " + totalRegistros + " empleados...");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            Random rand = new Random();
            
            // DNI;Nombre;SueldoBruto;Antiguedad
            for (int i = 1; i <= totalRegistros; i++) {
                String dni = i + "A";
                String nombre = "Empleado_" + i;
                // Sueldo entre 15.000 y 60.000
                double sueldo = 15000 + (45000 * rand.nextDouble());
                // Antigüedad entre 0 y 20 años
                int antiguedad = rand.nextInt(21);
                
             
                bw.write(String.format(java.util.Locale.US, "%s;%s;%.2f;%d", dni, nombre, sueldo, antiguedad));
                bw.newLine();
            }
            System.out.println("¡Hecho! Archivo 'dataset.csv' creado correctamente.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}