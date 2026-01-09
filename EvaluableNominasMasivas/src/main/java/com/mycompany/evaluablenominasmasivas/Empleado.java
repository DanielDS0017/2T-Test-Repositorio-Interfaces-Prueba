
package com.mycompany.evaluablenominasmasivas;

public class Empleado {
    private String dni;
    private String nombre;
    private double sueldoBrutoAnual;
    private int aniosAntiguedad;

    public Empleado(String lineaCSV) {
     //DNI;Nombre;SueldoBruto;Antiguedad
     
        String[] partes = lineaCSV.split(";");
        this.dni = partes[0];
        this.nombre = partes[1];
        this.sueldoBrutoAnual = Double.parseDouble(partes[2]);
        this.aniosAntiguedad = Integer.parseInt(partes[3]);
    }

    public double calcularSueldoNetoMensual() {
        // 1. Prorrateo 
        double brutoMensual = sueldoBrutoAnual / 12.0;

        // 2. Retención IRPF 
        double retencion;
        if (sueldoBrutoAnual < 20000) {
            retencion = 0.10; // 10%
        } else {
            retencion = 0.15; // 15%
        }
        
        double descuentoIRPF = brutoMensual * retencion;

        // 3. Plus Antigüedad (30€ por año) 
        double plusAntiguedad = aniosAntiguedad * 30.0;

        // Cálculo Final
        return (brutoMensual - descuentoIRPF) + plusAntiguedad;
    }

    // Getters
    public String toCSV() {
        return dni + ";" + nombre + ";" + sueldoBrutoAnual + ";" + aniosAntiguedad;
    }
    
    public String getNombre() { return nombre; }
}