package tp.integrador.dto;

public class CarreraDto {
    private String nombre;
    private Long cantidad;

    public CarreraDto() {
    }

    public CarreraDto(String nombre, Long cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "CarreraDto{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                '}' + "\n";
    }
}
