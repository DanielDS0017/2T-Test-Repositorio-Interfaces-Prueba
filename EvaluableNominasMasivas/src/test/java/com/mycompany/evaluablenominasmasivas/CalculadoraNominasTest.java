package com.mycompany.evaluablenominasmasivas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraNominasTest {

    //Sueldo Baj: 15k brutos, 0 años -> 10% IRPF
    @Test
    void testSueldoBajo() {
        //  DNI;Nombre;15000;0
        Empleado emp = new Empleado("1;Test;15000;0");
        double esperado = (15000.0 / 12) * 0.90; // 1250 - 10% = 1125
        assertEquals(esperado, emp.calcularSueldoNetoMensual(), 0.01);
    }

    //"Sueldo Alto": 30k brutos, 0 años -> 15% IRPF 
    @Test
    void testSueldoAlto() {
        
        // DNI;Nombre;30000;0
        Empleado emp = new Empleado("2;Test;30000;0");
        double esperado = (30000.0 / 12) * 0.85; // 2500 - 15% = 2125
        assertEquals(esperado, emp.calcularSueldoNetoMensual(), 0.01);
    }

    //"Antigüedad": Verificar suma de 30€ por año.
    
    @Test
    void testAntiguedad() {
        // 15k brutos (base 1125) + 2 años (60€) = 1185
        Empleado emp = new Empleado("3;Test;15000;2");
        double esperado = 1125 + 60; 
        assertEquals(esperado, emp.calcularSueldoNetoMensual(), 0.01);
    }
}