package soporte;

import negocio.Agrupacion;
import negocio.Region;
import negocio.Resultados;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFile {
    private File file;

    public TextFile(String path) {
        file = new File(path);
    }

    public TSB_OAHashtable identificarAgrupaciones() {
        String linea = "", campos[];
        TSB_OAHashtable table = new TSB_OAHashtable<>(10);
        Agrupacion agrupacion;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                //Filtramos votación para Presidente
                if (campos[0].compareTo("000100000000000") == 0) {
                    agrupacion = new Agrupacion(campos[2], campos[3]);
                    table.put(agrupacion.getCodigo(), agrupacion);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
        return table;
    }

    public Region identificarRegiones() {
        String linea = "", campos[], codigo, nombre;
        Region pais= new Region("00","Argentina");
        Region distrito, seccion;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                codigo= campos[0];
                nombre= campos[1];
                switch (codigo.length()){
                    case 2:
                        //Distrito
                        distrito=pais.getOrPutSubregion(codigo);
                        distrito.setNombre(nombre);
                        break;
                    case 5:
                        //Seccion
                        distrito=pais.getOrPutSubregion(codigo.substring(0,2));
                        seccion =distrito.getOrPutSubregion(codigo);
                        seccion.setNombre(nombre);
                        break;
                    case 11:
                        // Circuito
                        distrito=pais.getOrPutSubregion(codigo.substring(0,2));
                        seccion= distrito.getOrPutSubregion(codigo.substring(0,5));
                        seccion.agregarSubregion(new Region(codigo,nombre));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
        return pais;
    }
    public void sumarVotosPorRegion(Resultados resultados) {
        String linea = "", campos[];
        int votos;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                //Filtramos votación para Presidente
                if (campos[4].compareTo("000100000000000") == 0) {
                    votos = Integer.parseInt(campos[6]);
                    //acumulamos los votos del pais
                    resultados.sumarVotos("00",campos[5],votos);
                    //acumulamos los votos del distrito, seccion , circuito y mesas
                    for (int i = 0; i <4; i++) {
                        resultados.sumarVotos(campos[i],campos[5],votos);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
    }
    public Region getMesas(String codRegion)
    {
        Region circuito=new Region(codRegion,""), mesas;
        String linea = "", campos[],codigoMesa;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                codigoMesa = campos[3];
                if(campos[4].compareTo("000100000000000")==0 &&campos[2].compareTo(codRegion)==0)
                {
                    mesas=circuito.getOrPutSubregion(codigoMesa);
                    mesas.setNombre(codigoMesa.substring(7,10));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
        return circuito;
    }

}