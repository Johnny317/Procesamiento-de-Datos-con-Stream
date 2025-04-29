/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package procesamiento.de.datos.con.stream;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jhony Espinoza
 */
public class ProcesamientoDeDatosConStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<ProcesamientoProductos> productos = new ArrayList<>();
        productos.add(new ProcesamientoProductos("Laptop", 1200, "Tecnologico"));
        productos.add(new ProcesamientoProductos("Camiseta", 25, "ROPA"));
        productos.add(new ProcesamientoProductos("Pan", 2, "ALIMENTACION"));
        productos.add(new ProcesamientoProductos("Silla", 150, "Muebles"));
        productos.add(new ProcesamientoProductos("Televisor", 800, "Tecnologico"));
        productos.add(new ProcesamientoProductos("Pantalon", 40, "ROPA"));

        System.out.println("Productos Caros (>100):");
        ProcesamientoProductos.obtenerProductosCaros(productos).forEach(System.out::println);
        System.out.println("________________________________________________________________________________________________________________-");

        System.out.println("\nNombres de los Productos:");
        ProcesamientoProductos.obtenerNombresProductos(productos).forEach(System.out::println);
        System.out.println("________________________________________________________________________________________________________________-");

        System.out.println("\nPrecio Total de Todos los Productos:");
        System.out.println(ProcesamientoProductos.calcularPrecioTotal(productos));
        System.out.println("________________________________________________________________________________________________________________-");

        System.out.println("\nProductos Agrupados por Categoria:");
        ProcesamientoProductos.agruparPorCategoria(productos).forEach((categoria, lista) -> {
            System.out.println(categoria + ": " + lista);
        });
        System.out.println("________________________________________________________________________________________________________________-");

        System.out.println("\nProducto Mas Caro:");
        System.out.println(ProcesamientoProductos.encontrarProductoMasCaro(productos));
        System.out.println("________________________________________________________________________________________________________________-");

        System.out.println("\nCantidad de Productos Baratos (<80):");
        System.out.println(ProcesamientoProductos.contarProductosBaratos(productos));
        System.out.println("________________________________________________________________________________________________________________-");

    }

}
