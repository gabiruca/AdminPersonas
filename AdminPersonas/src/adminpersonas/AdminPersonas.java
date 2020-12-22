/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminpersonas;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ventanas.ListadoPersonas;

/**
 *
 * @author Gabriela
 */
public class AdminPersonas extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ListadoPersonas ventanaLista= new ListadoPersonas();
        Scene scene= new Scene(ventanaLista.getRoot(),850,460);
        File f = new File("src/css/estilos.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/"));
        primaryStage.setTitle("Listado Personas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
