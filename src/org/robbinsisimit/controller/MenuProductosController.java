/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.model.Producto;
import org.robbinsisimit.system.Main;
import org.robbinsisimit.controller.FormProductosController;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class MenuProductosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblProductos;
    @FXML
    TextField tfProductoIdBuscar;
    @FXML
    TableColumn colProductoId,colNombre,colDescripcion, colCantidadStokc, colPrecioUnitario, colPrecionMayor, colPrecionVenta,
        colImage,colDistribuidoraId, colCategoriaProducto;
    @FXML
    Button btnRegresar, btnAgregar,btnEditar, btnBuscar;
    @FXML
    ImageView imgMostrar;
    @FXML
    Label lblNombreProducto;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        cargarDatos();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        try{
            if(event.getSource() == btnRegresar){
                stage.menuPrincipalView();
            }else if(event.getSource() == btnAgregar){
                stage.formProductoView(1);
            }else if(event.getSource() == btnBuscar){
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cargarDatos(){
        tblProductos.setItems(listarProductos());
        colProductoId.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("productoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto,String>("descripcionProducto"));
        colCantidadStokc.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("cantidadStock"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioVentaUnitario"));
        colPrecionMayor.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioVentaMayor"));
        colPrecionVenta.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioCompra"));
        colImage.setCellValueFactory(new PropertyValueFactory<Producto,Byte>("imagenProducto"));
        colDistribuidoraId.setCellValueFactory(new PropertyValueFactory<Producto,String>("distribuidor"));
        colCategoriaProducto.setCellValueFactory(new PropertyValueFactory<Producto,String>("categoriaProductos"));
        tblProductos.getSortOrder().add(colProductoId);
        
    }
    
    
    public ObservableList<Producto>listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                byte[] imagenProducto = resultSet.getBytes("imagenProducto");
                String distribuidor = resultSet.getString("Distribuidores");
                String categoriaProductos = resultSet.getString("Categoria");
                productos.add(new Producto(productoId,nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoriaProductos));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableArrayList(productos);
    }
    
    
  // Profe no pude hacer producto por que no entendi su video :D y tambine el editar ignole solo sirve el agregar y que se miestren los valores em la tabla Me rindo :D
    public Producto buscarProducto(){
        Producto producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoIdBuscar.getText()));
            
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                byte[] imagenProducto = resultSet.getBytes("imagenProducto");
                String distribuidor = resultSet.getString("Distribuidores");
                String categoriaProductos = resultSet.getString("Categoria");
                
                producto = new Producto(productoId,nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoriaProductos);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return producto;
    }
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
