package interfaz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import negocio.*;

import java.io.File;

public class PrincipalController {
    public Label lblUbicacion;
    public ListView lvwResultados;
    public ComboBox cmbDistritos;
    public ComboBox cmbSecciones;
    public ComboBox cmbCircuitos;
    public Resultados resultados;
    public Button btnCargar;
    public AnchorPane ventana;
    public ComboBox cmbMesas;
    public Button btn_totales;
    public Button btnCambiar;
    private ObservableList ol;
    private Mesas mesas;
    private Regiones regiones;

    public void cambiarUbicacion(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Seleccione ubicación de los datos");
        File file =  dc.showDialog(null);
        if (file != null)
            lblUbicacion.setText(file.getPath());
    }

    public void cargarDatos(ActionEvent actionEvent) {
        cargarTotales();
        //Colocamos el cursor de nuevo en default
        ventana.setCursor(Cursor.DEFAULT);
        // habilitamos el combobox de distritos
        cmbDistritos.setDisable(false);
        // habilitamos el boton de conteos totales
        btn_totales.setDisable(false);

    }
    public void elegirDistrito(ActionEvent actionEvent) {
        //Generamos lista de secciones del distrito elegido
        if(cmbDistritos.getValue()!=null) {
            Region distrito = (Region) cmbDistritos.getValue();
            ol = FXCollections.observableArrayList(distrito.getSubregiones());
            cmbSecciones.setItems(ol);
            //Mostramos resultados del distrito
            ol = FXCollections.observableArrayList(resultados.getResultadosRegion(distrito.getCodigo()));
            lvwResultados.setItems(ol);
            // hablitamos el combobox de Secciones
            cmbSecciones.setDisable(false);
            cmbCircuitos.getItems().clear();;
            cmbMesas.getItems().clear();;
        }else
            cmbSecciones.getItems().clear();;

    }

    public void elegirSeccion(ActionEvent actionEvent) {
        //Generamos lista de circuitos de la seccion elegida
        if(cmbSecciones.getValue()!=null){
            //cmbCircuitos.setItems(ol);
            Region seccion = (Region) cmbSecciones.getValue();
            ol= FXCollections.observableArrayList(seccion.getSubregiones());
            cmbCircuitos.setItems(ol);
            //Mostramos resultados de la seccion
            ol= FXCollections.observableArrayList(resultados.getResultadosRegion(seccion.getCodigo()));
            lvwResultados.setItems(ol);
            // hablitamos el combobox de Circuitos
            cmbCircuitos.setDisable(false);
        }
        else
            cmbCircuitos.getItems().clear();;
    }
    public void elegirCircuito(ActionEvent actionEvent) {
        //Generamos lista de circuitos de la seccion elegida
        if(cmbCircuitos.getValue()!=null){
            Region circuito = (Region) cmbCircuitos.getValue();
            mesas= new Mesas(lblUbicacion.getText());
            ol= FXCollections.observableArrayList(mesas.getMesas(circuito.getCodigo()));
            cmbMesas.setItems(ol);
            //Mostramos los resultados del circuito
            ol= FXCollections.observableArrayList(resultados.getResultadosRegion(circuito.getCodigo()));
            lvwResultados.setItems(ol);
            cmbMesas.setDisable(false);
        }
        else
            cmbCircuitos.getItems().clear();;
    }
    public void elegirMesa(ActionEvent actionEvent) {
        //Generamos lista de Mesas del circuito elegido
        if(cmbMesas.getValue()!=null){
            Region mesa= (Region) cmbMesas.getValue();
            //Mostramos los resultados de la mesa
            ol= FXCollections.observableArrayList(resultados.getResultadosRegion(mesa.getCodigo()));
            lvwResultados.setItems(ol);
        }
        else
            cmbMesas.getItems().clear();
    }
    //Accion de cursor en espera
    public void cursorenEspera(MouseEvent mouseEvent) {
           ventana.setCursor(Cursor.WAIT);
    }
    public void borrarCombos()
    {
        cmbCircuitos.getItems().clear();
        cmbSecciones.getItems().clear();
        cmbDistritos.getItems().clear();
        cmbMesas.getItems().clear();
    }
    // metodo para  el boton de conteos totales.
    public void cargarResultadosTotales(ActionEvent actionEvent) {
        cargarTotales();
        //Colocamos el cursor de nuevo en default
        ventana.setCursor(Cursor.DEFAULT);
        // habilitamos el combobox de distritos
        cmbDistritos.setDisable(false);
        cmbCircuitos.setDisable(true);
        cmbSecciones.setDisable(true);
        cmbMesas.setDisable(true);

    }
    // metodo para agregar los totales
    private void cargarTotales()
    {
        // Limpiamos los combos
        borrarCombos();
        //Generamos lista de agrupaciones
        Agrupaciones.leerAgrupaciones(lblUbicacion.getText());
        //Generamos lista de distritos del pais
        regiones=new Regiones(lblUbicacion.getText());
        ol= FXCollections.observableArrayList(regiones.getDistritos());
        cmbDistritos.setItems(ol);
        //Procesamoslos totales por region
        resultados= new Resultados(lblUbicacion.getText());
        ol= FXCollections.observableArrayList(resultados.getResultadosRegion("00"));
        lvwResultados.setItems(ol);
    }
}
