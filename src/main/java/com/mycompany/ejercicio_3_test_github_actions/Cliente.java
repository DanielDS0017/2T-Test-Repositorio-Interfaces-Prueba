
package com.mycompany.ejercicio_3_test_github_actions;


public class Cliente {
    
    private String nombreCliente;
    private int añosAntiguedad;

    public Cliente(String nombreCliente, int añosAntiguedad) {
        this.nombreCliente = nombreCliente;
        this.añosAntiguedad = añosAntiguedad;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getAñosAntiguedad() {
        return añosAntiguedad;
    }

    public void setAñosAntiguedad(int añosAntiguedad) {
        this.añosAntiguedad = añosAntiguedad;
    }
    
}
