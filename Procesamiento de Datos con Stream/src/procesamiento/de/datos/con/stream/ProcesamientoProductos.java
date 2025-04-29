/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesamiento.de.datos.con.stream;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author Jhony Espinoza
 */
public class ProcesamientoProductos {
 
    private String nombre;
    private double precio;
    private String categoria;

    public ProcesamientoProductos(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " (" + categoria + ")";
    }
     public static List<ProcesamientoProductos> obtenerProductosCaros(List<ProcesamientoProductos> productos) {
        return productos.stream()
                .filter(p -> p.getPrecio() > 100)
                .collect(Collectors.toList());
    }

    public static List<String> obtenerNombresProductos(List<ProcesamientoProductos> productos) {
        return productos.stream()
                .map(ProcesamientoProductos::getNombre)
                .collect(Collectors.toList());
    }

    public static double calcularPrecioTotal(List<ProcesamientoProductos> productos) {
        return productos.stream()
                .mapToDouble(ProcesamientoProductos::getPrecio)
                .sum();
    }

    public static Map<String, List<ProcesamientoProductos>> agruparPorCategoria(List<ProcesamientoProductos> productos) {
        return productos.stream()
                .collect(Collectors.groupingBy(ProcesamientoProductos::getCategoria));
    }

    public static ProcesamientoProductos encontrarProductoMasCaro(List<ProcesamientoProductos> productos) {
        return productos.stream()
                .max(Comparator.comparingDouble(ProcesamientoProductos::getPrecio))
                .orElse(null);
    }

    public static long contarProductosBaratos(List<ProcesamientoProductos> productos) {
        return productos.stream()
                .filter(p -> p.getPrecio() < 80)
                .count();
    }    
}

    

