package negocio;

public class Agrupacion {
    private String codigo;
    private String nombre;
    private int votos;

    public Agrupacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        votos = 0;

    }

    public String getCodigo() {
        return codigo;
    }


    public void sumarVotos(int cantidad)
    {
        votos += cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Agrupación: " + nombre +
                ", Codigo= '" + codigo + '\'' +
                ", Votos= " + votos ;
    }
}
