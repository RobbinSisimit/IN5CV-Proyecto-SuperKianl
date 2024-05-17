/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    Button btnGuardar,btnRegresar,btnEliminar,btnBuscar, btnVaciar;
    @FXML
    TableColumn colCompraId, colFechaCompra, colTotalCompra, colCantidad, colProductoId;
    @FXML
    TextField tfCompraId, tfFechaCompra, tfTotalCompra, tfCantidad, tfBuscarCompra;
    @FXML
    DatePicker dpFechaCompra;
    @FXML
    ComboBox cmbProductos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarListaCompras();
        cmbProductos.setItems(listarProductos());
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfCompraId.getText().isEmpty()){
                agregarCompra();
                cargarListaCompras();
            } else {
                editarCompra();
                cargarListaCompras();
            }
        }else if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
    public void vaciarCampos(){
        tfCompraId.clear();
        dpFechaCompra.setValue(null);
        tfCantidad.clear();
        tfTotalCompra.clear();
        cmbProductos.getSelectionModel().clearSelection();
        tfBuscarCompra.clear();
    }
    
    public void cargarDatosEditar(){
        DetalleCompra dc = (DetalleCompra)tblCompras.getSelectionModel().getSelectedItem();
        if(dc != null){
            tfCompraId.setText(Integer.toString(dc.getCompraId()));
            dpFechaCompra.setValue(dc.getFechaCompra().toLocalDate());
            tfCantidad.setText(Integer.toString(dc.getCantidadCompra()));
            tfTotalCompra.setText(Double.toString(dc.getTotalCompra()));
            cmbProductos.getSelectionModel().select(obtenerIndexProducto());
        }
    }
    
     public int obtenerIndexProducto(){
        int index = 0;
        for(int i = 0 ; i > cmbProductos.getItems().size() ; i++){
            String productoCmb = cmbProductos.getItems().get(i).toString();
            String productoTbl = ((DetalleCompra)tblCompras.getSelectionModel().getSelectedItem()).getProducto();
            if(productoCmb.equals(productoTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void cargarListaCompras(){
        tblCompras.setItems(ListarDetalleCompra());
        colCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("compraId"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("fechaCompra"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidadCompra"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("totalCompra"));
        colProductoId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("producto"));
    }
    
    public ObservableList<DetalleCompra> ListarDetalleCompra(){
        ArrayList<DetalleCompra> detalleCompra = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                double total = resultSet.getDouble("totalCompra");
                int cantidad = resultSet.getInt("cantidadCompra");
                String producto = resultSet.getString("producto");
                
                
                detalleCompra.add(new DetalleCompra(cantidad,producto,compraId,fecha,total));
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
        
        return FXCollections.observableList(detalleCompra);
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
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
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
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, date.valueOf(dpFechaCompra.getValue()));
            statement.setInt(2, Integer.parseInt(tfCantidad.getText()));
            statement.setInt(3, ((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();
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
                e.printStackTrace();
            }
        }
    }
     
     public void editarCompra(){
         try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDetalleCompra(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfCompraId.getText()));
            statement.setDate(2, Date.valueOf(dpFechaCompra.getValue()));
            statement.setDouble(3, Double.parseDouble(tfTotalCompra.getText()));
            statement.setInt(4, Integer.parseInt(tfCantidad.getText()));
            statement.setInt(5, ((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();
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
                e.printStackTrace();
            }
        }
     }
     
     public void buscarCompra(){
         
     }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
