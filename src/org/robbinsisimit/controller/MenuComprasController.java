/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.model.Compra;
import org.robbinsisimit.model.Producto;
import org.robbinsisimit.system.Main;
import org.robbinsisimit.model.DetalleCompra;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class MenuComprasController implements Initializable {

    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId, colFechaComrpa, colTotalCompra, colProductoId, colCantidad;
    @FXML
    Button btnGuardar, btnRegresar, btnVaciar;
    @FXML
    TextField tfCompraId,tfCantidad,tfTotalCompra,tfFechaCompra;
    @FXML
    ComboBox cmbProductoId;
    @FXML
    DatePicker dpFechaCompra;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            
        }else if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnVaciar){
            varciarCampos();
        }
    }
    
    public void varciarCampos(){
        tfCompraId.clear();
        tfCantidad.clear();
        tfTotalCompra.clear();
        tfFechaCompra.clear();
        cmbProductoId.getSelectionModel().clearSelection();
    }

    public void cargarLista(){
        tblCompras.setItems(listarCompras());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("compraId"));
        colFechaComrpa.setCellValueFactory(new PropertyValueFactory<Compra, String>("fechaCompra"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compra, Double>("totalCompra"));
    }
    
    public int obtenerIdexProducto(){
        int index = 0;
        for(int i = 0; i >= cmbProductoId.getItems().size(); i++){
            String productoCmb = cmbProductoId.getItems().get(i).toString();
            String productoTbl = ((Producto)tblCompras.getSelectionModel().getSelectedItem()).getProducto();
            if(productoCmb.equals(productoTbl)){
                index = 1;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<Compra> listarCompras(){
        ArrayList<Compra> compras = new ArrayList();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date fechaCompra = resultSet.getDate("fechaCompra");
                Double totalCompra = resultSet.getDouble("totalCompra");
                
                compras.add(new Compra(compraId, fechaCompra, totalCompra));
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
        
        return FXCollections.observableList(compras);
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
    
    public void agregarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "";
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
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
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
