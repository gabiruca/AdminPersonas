/*
 */
package ventanas;

import java.util.Collections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modelo.EstadoCivil;
import modelo.Persona;

/**
 *
 * @author Gabriela
 */
public class NuevaPersona {
    VBox root;
    TextField txtCedula;
    TextField txtNombres;
    TextField txtApellidos;
    ListadoPersonas ventListado;
    ComboBox cmbEstado;
    
    public NuevaPersona(ListadoPersonas ventana){
        ventListado=ventana;
        createContent();
    }
    public Pane getRoot(){
        return root;
    }

    private void createContent() {
        root = new VBox(5);
        //Ingreso nueva cedula
        
        Label titulo= new Label("Ingreso de persona");
        titulo.setId("titulo");
        //de forma especifica para aplicar estilo a algo
        //titulo.setStyle("-fx-background-color:blue; -fx-text-fill:white");
        
        GridPane cntDatos = new GridPane();
        
        cntDatos.setPadding(new Insets(20));
        cntDatos.setHgap(25);
        cntDatos.setVgap(15);
        
        Label lblCedula= new Label("Cedula");
        txtCedula= new TextField();
        
        cntDatos.add(lblCedula, 0, 0);
        cntDatos.add(txtCedula, 1, 0);
        
        
        //Ingreso new nombres
        Label lblNombres= new Label("Nombres");
        txtNombres= new TextField();
        cntDatos.add(lblNombres, 0, 1);
        cntDatos.add(txtNombres, 1, 1);
        //Ingreso new apellidos
        Label lblApellidos = new Label("Apellidos");
        txtApellidos = new TextField();
        cntDatos.add(lblApellidos, 0, 2);
        cntDatos.add(txtApellidos, 1, 2);
        
        //estadocivil
        Label lblEstado = new Label("Estado Civil");
        cmbEstado =new ComboBox();
        //cmbEstado.getItems().add("SOLTERO");
        //cmbEstado.getItems().add("cASADO");
        cmbEstado.getItems().setAll(EstadoCivil.values());
        cntDatos.add(lblEstado, 0, 3);
        cntDatos.add(cmbEstado, 1, 3);
        //Botones
        HBox cntBotones = new HBox(5);
        Button btnGuardar= new Button("Guardar");
        btnGuardar.setOnAction(e->{
            manejarFormulario();

        });
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(e->{btnCerrar.getScene().getWindow().hide();});
        cntBotones.getChildren().addAll(btnGuardar,btnCerrar);
        cntBotones.setAlignment(Pos.CENTER);
        cntDatos.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
    
        root.getChildren().addAll(titulo, cntDatos, cntBotones);
  
    }
    

    private void manejarFormulario() {
        String cedula = txtCedula.getText();
        String nombres = txtNombres.getText();
        String apellidos= txtApellidos.getText();
        String estado=String.valueOf(cmbEstado.getValue()) ;
        if(cedula.trim().length()>0 && nombres.trim().length()>0 & apellidos.trim().length()>0){
            Persona persona = new Persona(cedula, nombres, apellidos,EstadoCivil.valueOf(estado));
            ventListado.personas.add(persona);
            ventListado.actualizarArchivoPersonas();
            ventListado.actualizarTableView();
            txtCedula.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Ingreso de persona");
            alert.setContentText("Todos los campos son obligatorios");
            
            alert.showAndWait();
        }
    }
    
    
   
}
