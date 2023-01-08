package negocio;

import soporte.TextFile;

import java.util.Collection;

public class Mesas {
    private TextFile fileMesas;
    private Region circuito;

    public Mesas(String path) {
        fileMesas = new TextFile(path + "\\mesas_totales.dsv");
    }

    public Collection getMesas(String codCircuito) {
        circuito= fileMesas.getMesas(codCircuito);
        return circuito.getSubregiones();
    }
}
