/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.robbinsisimit.controller.FormCargosController;
import org.robbinsisimit.controller.FormCategoriaProductosController;
import org.robbinsisimit.controller.FormClientesController;
import org.robbinsisimit.controller.FormDistribuidoresController;
import org.robbinsisimit.controller.FormProductosController;
import org.robbinsisimit.controller.MenuClientesController;
import org.robbinsisimit.controller.MenuPrincipalController;
import org.robbinsisimit.controller.MenuTicketSoporteController;
import org.robbinsisimit.controller.MenuCargosController;
import org.robbinsisimit.controller.MenuCategoriaProductosController;
import org.robbinsisimit.controller.MenuComprasController;
import org.robbinsisimit.controller.MenuDistribuidoresController;
import org.robbinsisimit.controller.MenuEmpleadosController;
import org.robbinsisimit.controller.MenuProductosController;
import org.robbinsisimit.controller.MenuPromocionesController;


/**
 *
 * @author robin
 */
public class Main extends Application {
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/robbinsisimit/view/";
    
    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("superKianl");
        menuPrincipalView();
        stage.show();
    }
    
    public Initializable switchScene (String fxmlName, int Width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new  FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane)loader.load(file), Width,height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 675,450);
            menuPrincipalView.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuClientesView(){
        try{
           MenuClientesController menuClientesView = (MenuClientesController)switchScene("MenuClientesView.fxml", 850,600); 
           menuClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuComprasView(){
        try{
            MenuComprasController menuCompraView = (MenuComprasController)switchScene("MenuComprasView.fxml", 1000 , 700);
            menuCompraView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosView = (MenuCargosController)switchScene("MenuCargosView.fxml", 950, 700);
            menuCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuDistribuidorView(){
        try{
            MenuDistribuidoresController menuDistribuidorView = (MenuDistribuidoresController)switchScene("MenuDistribuidoresView.fxml", 950, 600);
            menuDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuCategoriaProductoView(){
        try{
            MenuCategoriaProductosController menuCategoriaProductoView = (MenuCategoriaProductosController)switchScene("MenuCategoriaProductosView.fxml",800, 500);
            menuCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    } 
    public void formClientesView(int op){
        try{
            FormClientesController formClientesView = (FormClientesController)switchScene("FormClientesView.fxml", 500, 700);
            formClientesView.setOp(op);
            formClientesView.setStage(this);           
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formProductoView(int op){
        try{
            FormProductosController formProductoView = (FormProductosController)switchScene("FormProductosView.fxml", 900, 700);
            formProductoView.setOp(op);
            formProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void formDistribuidoresView(int op){
        try{
            FormDistribuidoresController formDistribuidoresView = (FormDistribuidoresController)switchScene("FormDistribuidoresView.fxml", 450, 600);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formCargosView(int op){
        try{
            FormCargosController formCargosView = (FormCargosController)switchScene("FormCargosView.fxml", 500, 600);
            formCargosView.setOp(op);
            formCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formCategoriaProductoView(int op){
        try{
            FormCategoriaProductosController formCategoriaProductoView = (FormCategoriaProductosController)switchScene("FormCategoriaProductosView.fxml", 400, 600);
            formCategoriaProductoView.setOp(op);
            formCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuTicketSoporteView(){
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml", 1000, 600);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuEmpleadoView(){
        try{
            MenuEmpleadosController menuEmpeladosController = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1100, 600);
            menuEmpeladosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuProductoView(){
        try{
            MenuProductosController menuProductoController = (MenuProductosController)switchScene("MenuProductosView.fxml",1300, 600);
            menuProductoController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuPromocionView(){
        try{
            MenuPromocionesController menuPromocionesView = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml" , 1000, 600);
            menuPromocionesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    

    
}

