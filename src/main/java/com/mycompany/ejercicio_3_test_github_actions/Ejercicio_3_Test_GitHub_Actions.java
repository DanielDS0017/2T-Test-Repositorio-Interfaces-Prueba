/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/*

Una tienda online quiere implemetar un sistema de fidelización. Crear un proyecto con la siguiente estructura:

 - Clase Cliente.java (Modelo) Representa aun comprador ( con nombre, años de antiguiedad y todos sus métodos estándar).

 - Clase gestorDescuentos.java ( Lógica de negocio) Calcula que descuento merece el cliente. Tiene el método: public int clacularDescuento (cliente c)
 - Regla de negocio : Si la antigüedad del cliente es mayor de 5 años: Devuelve 10 ( 10% de descuento ). En caso contrario: Devuelve 0 ( Sin descuento)
 - Crear dos test: testDescuentoNuevo y testDecuentoVeterano.

Implementa GitHub Actions para que salten los test con cada push.

*/

package com.mycompany.ejercicio_3_test_github_actions;

/**
 *
 * @author PC227
 */
public class Ejercicio_3_Test_GitHub_Actions {

    public static void main(String[] args) {
    
        Cliente c1 = new Cliente ("Manolo" , 5);
        
    }
}
