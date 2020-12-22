/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.EstadoCivil;
import modelo.Persona;


/**
 * @author Gabriela
 */
public class ListadoPersonas {
    BorderPane root;
    TableView tableView;
    ArrayList<Persona> personas;
    String filename="personas.ser";
    
    public Pane getRoot(){
        return root;
    }
    
    private void cargarPersonas(){
        personas= new ArrayList<>();
        Path path= Paths.get(filename);
        if(Files.exists(path)){
            ObjectInputStream in=null;
            try{
                in = new ObjectInputStream(new FileInputStream(filename));
                personas=(ArrayList<Persona>) in.readObject();
                in.close();
            }catch(FileNotFoundException ex){
                System.out.println(ex.getMessage());
            
            }catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            } finally{
                try{
                    in.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        Collections.sort(personas);
        System.out.println(personas);
    }
    
    private void createContent(){
        String[] datos={"Cedula","Nombres","Apellidos","EstadoCivil"};
        tableView=new TableView();
        for(String campo:datos){
            TableColumn<String, Persona>column= new TableColumn<>(campo);
            column.setCellValueFactory(new PropertyValueFactory<>(campo.toLowerCase()));
            column.prefWidthProperty().bind(tableView.widthProperty().multiply(0.24));
            tableView.getColumns().add(column);
        }
        for(Persona p:personas){
            tableView.getItems().add(p);
        }
        //HBox contTitulo= new HBox(10);
        //Label lbl=new Label("Listado de Personas");
        root =new BorderPane();
        try {
            Image imgl = new Image(new FileInputStream("src/imagen/bannerT.png"),850,150,true,false);
            ImageView imgView = new ImageView();
            imgView.setImage(imgl);
            root.setTop(imgView);    
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no existe");
        }
        
        //contTitulo.getChildren().add(lbl);
        //contTitulo.setAlignment(Pos.CENTER);
        
        HBox contBotones=new HBox(10);
        Button btnNuevo= new Button("Agregar");
        Button btnEliminar= new Button("Eliminar");
        Button btnEditar= new Button("Editar");

        btnNuevo.setOnAction(e->{
            Stage ventanaNueva = new Stage();
            NuevaPersona ventana= new NuevaPersona(this);
            Scene scene = new Scene(ventana.getRoot(),400,350);
            File f = new File("src/css/estilos.css");
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/"));
            ventanaNueva.setTitle("Nueva Persona");
            ventanaNueva.setScene(scene);
            ventanaNueva.show();
        });
        
        btnEliminar.setOnAction(e->{
            Persona persona = (Persona)tableView.getSelectionModel().getSelectedItem();
            System.out.println(persona);
            eliminarPersona(persona);
            Collections.sort(personas);
        });
        
        btnEditar.setOnAction(e->{
            Persona persona = (Persona)tableView.getSelectionModel().getSelectedItem();
            System.out.println(persona);
            crearContenidoDerecho(persona);
            Collections.sort(personas);
        });
        
        contBotones.getChildren().add(btnNuevo);
        contBotones.getChildren().add(btnEliminar);
        contBotones.getChildren().add(btnEditar);

        contBotones.setPadding(new Insets(10));
        contBotones.setAlignment(Pos.CENTER);
        
        //root= new BorderPane();
       // root.setTop(contTitulo);
        root.setCenter(tableView);
        root.setBottom(contBotones);

    }
    
    public ListadoPersonas(){
        cargarPersonas();
        createContent();
    }
    
    public void actualizarTableView(){
        tableView.getItems().clear();
        Collections.sort(personas);
        for(Persona p: personas){
            tableView.getItems().add(p);
        }
    }
    
    private void crearContenidoDerecho(Persona persona){
        if (persona!=null){
            VBox form1 = new VBox(5);
            //Ingreso nueva cedula
            Label titulo= new Label("Edicion de persona");
            titulo.setId("titulo");
            //de forma especifica para aplicar estilo a algo
            //titulo.setStyle("-fx-background-color:blue; -fx-text-fill:white");        
            GridPane cntDatos = new GridPane();            
            cntDatos.setPadding(new Insets(20));
            cntDatos.setHgap(25);
            cntDatos.setVgap(15);            
            Label lblCedula= new Label("Cedula");
            TextField txtCedula= new TextField(persona.getCedula());
            txtCedula.setDisable(true);
            cntDatos.add(lblCedula, 0, 0);
            cntDatos.add(txtCedula, 1, 0);                   
            //Ingreso new nombres
            Label lblNombres= new Label("Nombres");
            TextField txtNombres= new TextField(persona.getNombres());
            cntDatos.add(lblNombres, 0, 1);
            cntDatos.add(txtNombres, 1, 1);
            //Ingreso new apellidos        
            Label lblApellidos = new Label("Apellidos");
            TextField txtApellidos = new TextField(persona.getApellidos());
            cntDatos.add(lblApellidos, 0, 2);
            cntDatos.add(txtApellidos, 1, 2);            
            //estadocivil
            Label lblEstado = new Label("Estado Civil");
            ComboBox cmbEstado =new ComboBox();
            //cmbEstado.getItems().add("SOLTERO");
            //cmbEstado.getItems().add("cASADO");
            cmbEstado.getItems().setAll(EstadoCivil.values());
            cmbEstado.setValue(persona.getEstadocivil());
            cntDatos.add(lblEstado, 0, 3);
            cntDatos.add(cmbEstado, 1, 3);            
            //Botones
            HBox cntBotones = new HBox(5);
            Button btnGuardar= new Button("Guardar");
            btnGuardar.setOnAction(e->{
                String cedula = txtCedula.getText();
                String nombres = txtNombres.getText();
                String apellidos = txtApellidos.getText();
                EstadoCivil estado =  (EstadoCivil)cmbEstado.getValue();
                if (nombres.trim().length()>0 && apellidos.trim().length()>0){
                    //crear el objeto persona
                    //Persona persona = new Persona(cedula,nombres,apellidos)
                    Persona np = new Persona(cedula,nombres,apellidos,estado);
                    //Buscar a la persona en la lista y actualizar
                    int indice = personas.indexOf(persona);
                    System.out.println("El indice de la persona es: "+indice);
                    if (indice>=0){
                        personas.set(indice, np);
                    }   
                    actualizarArchivoPersonas();
                    //refrescar el listado
                    actualizarTableView();
                    //TODO: MOSTRAR ALERTA CONFIRMANDO ACTUALIZACION
                    //cerrar
                    root.setRight(new VBox());
                }else{
                //mostrar alerta
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Ingreso de persona");
                    alert.setContentText("Todos los campos son obligatorios");
                    alert.showAndWait();
                }
                //Collections.sort(personas);     
            });
            Button btnCerrar = new Button("Cerrar");
            btnCerrar.setOnAction(e->{
                root.setRight(new VBox());
                //Collections.sort(personas);
            });
            cntBotones.getChildren().addAll(btnGuardar,btnCerrar);
            cntBotones.setAlignment(Pos.CENTER);
            cntDatos.setAlignment(Pos.CENTER);
            form1.setAlignment(Pos.CENTER);
            form1.getChildren().addAll(titulo, cntDatos, cntBotones);
            root.setRight(form1);
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error de seleccion");
            alert.setHeaderText("Error");
            alert.setContentText("Elija a una persona para editar");
            alert.showAndWait();
        }
    }
    
    public void actualizarArchivoPersonas(){
        FileOutputStream fout = null;
       try{
            fout= new FileOutputStream(filename);
            ObjectOutputStream out= new ObjectOutputStream(fout);
            out.writeObject(personas);
            out.flush();
            fout.close();
       }catch(FileNotFoundException ex){
           System.out.println(ex.getMessage());
       } catch(IOException ex){
           System.out.println(ex.getMessage());
       }finally{
           try{
               fout.close();
           }catch(IOException ex){
               System.out.println(ex.getMessage());
           }
       }      
    }

    private void eliminarPersona(Persona persona) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Borrar persona");
        alert.setHeaderText("¿Esta seguro de que desea eliminar esta persona?");
      // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            System.out.println("Porfavor, elija que hacer");
        } else if (option.get() == ButtonType.OK) {
            TextField txtNombres= new TextField(persona.getNombres());
            TextField txtCedula= new TextField(persona.getCedula());
            TextField txtApellidos= new TextField(persona.getApellidos());
            ComboBox cmbEstado =new ComboBox();
            cmbEstado.setValue(persona.getEstadocivil());
            String cedula = txtCedula.getText();
            String nombres = txtNombres.getText();
            String apellidos = txtApellidos.getText();
            EstadoCivil estado =  (EstadoCivil)cmbEstado.getValue();
            if (nombres.trim().length()>0 && apellidos.trim().length()>0){
                int indice = personas.indexOf(persona);
                if (indice>=0){
                    personas.remove(indice);
                    actualizarArchivoPersonas();
                    actualizarTableView();
                }
                System.out.println("Persona borrada");}
        } else if (option.get() == ButtonType.CANCEL) {
            System.out.println("Accion cancelada");
        } else {
            System.out.println("-");
        }
    }   
}