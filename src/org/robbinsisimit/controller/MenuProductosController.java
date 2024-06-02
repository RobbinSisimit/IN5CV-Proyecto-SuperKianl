/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.model.Producto;
import org.robbinsisimit.system.Main;
import org.robbinsisimit.controller.FormProductosController;
import org.robbinsisimit.model.CategoriaProducto;
import org.robbinsisimit.model.Distribuidor;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class MenuProductosController implements Initializable {

    private Main stage;
    private List<File> files = null;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblProductos;
    @FXML
    TableColumn colProductoId,colNombre,colDescripcion, colCantidadStokc, colPrecioUnitario, colPrecionMayor, colPrecionVenta,
        colImage,colDistribuidoraId, colCategoriaProducto;
    @FXML
    Button btnRegresar, btnGuardar,btnVaciar, btnBuscar;
    @FXML
    TextField tfProductoId,tfStock,tfNombre,tfPrecioVentaUnitario,tfPrecioVentaMayor,tfPrecioCompra,tfBuscarProducto;
    @FXML
    TextArea taDescripcion;
    @FXML
    ComboBox cmbDistribuidor,cmbCategoria;
    @FXML
    ImageView imgSubir,imgMostrar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        cargarDatos();
        cmbDistribuidor.setItems(listarDistribuidores());
        cmbCategoria.setItems(listarCaregoria());
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        try{
            if(event.getSource() == btnRegresar){
                stage.menuPrincipalView();
            }else if(event.getSource() == btnGuardar){
                if(tfProductoId.getText().isEmpty()){
                    agregarProducto();
                    cargarDatos();
                }else {
                    editarProductos();
                    cargarDatos();
                }
            }else if(event.getSource() == btnVaciar){
                vaciarCampos();
            }else if(event.getSource() == btnBuscar){
                tblProductos.getItems().clear();
            if(tfBuscarProducto.getText().equals("")){
                cargarDatos();
                imgSubir.setImage(null);
            }else{
                try{
                    Producto producto = buscarImagen();
                    if(producto != null){
                        InputStream file = producto.getImagenProducto().getBinaryStream();
                        Image imagen = new Image(file);
                        imgSubir.setImage(imagen);
                    }
                    tblProductos.getItems().add(buscarProducto());
                    colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
                    colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
                    colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
                    colCantidadStokc.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
                    colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
                    colPrecionVenta.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
                    colPrecionVenta.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
                    colDistribuidoraId.setCellValueFactory(new PropertyValueFactory<Producto, String>("Distribuidores"));
                    colCategoriaProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("Categoria"));
                
                    
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
               }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleOnDrag(DragEvent event){
        // Habilita el Image View para que reciba archivos
        
        if(event.getDragboard().hasFiles()){ // Verifca que haya un archivo pasando sobre el para recibirlo
           event.acceptTransferModes(TransferMode.ANY);   
        }    
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();  //Almacena el archivo en la lista
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgSubir.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cargarDatosEditar(){
        Producto p = (Producto)tblProductos.getSelectionModel().getSelectedItem();
        if(p != null){
            tfProductoId.setText(Integer.toString(p.getProductoId()));
            tfNombre.setText(p.getNombreProducto());
            tfStock.setText(Integer.toString(p.getCantidadStock()));
            tfPrecioVentaUnitario.setText(Double.toString(p.getPrecioVentaUnitario()));
            tfPrecioVentaMayor.setText(Double.toString(p.getPrecioVentaMayor()));
            tfPrecioCompra.setText(Double.toString(p.getPrecioCompra()));
            taDescripcion.setText(p.getDescripcionProducto());
            cmbDistribuidor.getSelectionModel().select(obtenerIndexDistribuidor());
            cmbCategoria.getSelectionModel().select(obtenerIndexCategoria());
        }
    }
    
    public int obtenerIndexCategoria(){
        int index = 0;
        for(int i = 0 ; i <  cmbCategoria.getItems().size() ; i++){
         String categoriaCmb = cmbCategoria.getItems().get(i).toString();
         String categoriaTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCategoriaProducto();
         if(categoriaCmb.equals(categoriaTbl)){
             index = i;
             break;
         }
        }
        
        return index;
    }
    
    public int obtenerIndexDistribuidor(){
        int index = 0;
        for(int i = 0 ; i < cmbDistribuidor.getItems().size() ; i++){
            String distribuidorCmb = cmbDistribuidor.getItems().get(i).toString();
            String distribuidorTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getDistribuidor();
            if(distribuidorCmb.equals(distribuidorTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void vaciarCampos(){
        tfProductoId.clear();
        tfNombre.clear();
        tfStock.clear();
        tfPrecioVentaUnitario.clear();
        tfPrecioVentaMayor.clear();
        tfPrecioCompra.clear();
        tfBuscarProducto.clear();
        taDescripcion.clear();
        cmbDistribuidor.getSelectionModel().clearSelection();
        cmbCategoria.getSelectionModel().clearSelection();
        imgMostrar.setImage(null);
        imgSubir.setImage(null);
        
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
        colImage.setCellValueFactory(new PropertyValueFactory<Producto,Blob>("imagenProducto"));
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
    
    public ObservableList<Distribuidor> listarDistribuidores(){
        ArrayList<Distribuidor> distribuidores = new ArrayList();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("distribuidorId");
                String nombre = resultSet.getString("nombreDistribuidor");
                String direccion = resultSet.getString("direccionDistribuidor");
                String nit = resultSet.getString("nitDistribuidor");
                String telefono = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores.add(new Distribuidor(cargoId,nombre,direccion,nit,telefono,web));
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
        
        return FXCollections.observableList(distribuidores);
    }
    
    //Listar para ComboBox Categoria
    public ObservableList<CategoriaProducto> listarCaregoria(){
        ArrayList<CategoriaProducto> categoriaProducto = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProducto.add(new CategoriaProducto(categoriaId,nombreCategoria,descripcionCategoria));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            
        }
        
        
        return FXCollections.observableList(categoriaProducto);
    }
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfNombre.getText());
            statement.setString(2,taDescripcion.getText());
            statement.setString(3, tfStock.getText());
            statement.setDouble(4,Double.parseDouble(tfPrecioVentaUnitario.getText()));     
            statement.setDouble(5,Double.parseDouble(tfPrecioVentaMayor.getText()));
            statement.setDouble(6,Double.parseDouble(tfPrecioCompra.getText()));
            InputStream img = new FileInputStream(files.get(0)); // Convierte la foto a binario
            statement.setBinaryStream(7, img);  // Ya guarda la foto en sql
            statement.setInt(8,((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProducto)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
            
        }catch(Exception e){
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
                e.printStackTrace();
            }
        }
    }
    
    public void editarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarProducto(?,?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            statement.setString(2,tfNombre.getText());
            statement.setString(3,taDescripcion.getText());
            statement.setInt(4, Integer.parseInt(tfStock.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecioVentaUnitario.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioVentaMayor.getText()));
            statement.setDouble(7, Double.parseDouble(tfPrecioCompra.getText()));
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(8, img);
            statement.setInt(9, ((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProducto)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(Exception e){
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
    
    public Producto buscarProducto(){
        Producto producto = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfBuscarProducto.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int cantidad = resultSet.getInt("cantidadStock");
                double unitario = resultSet.getDouble("precioVentaUnitario");
                double mayor = resultSet.getDouble("precioVentaMayor");
                double precio = resultSet.getDouble("precioCompra");
                String distribuidor = resultSet.getString("Distribuidores");
                String categoria = resultSet.getString("Categoria");
                
                producto = (new Producto(productoId,nombre,descripcion,cantidad,unitario,mayor,precio,distribuidor,categoria));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
              System.out.println(e.getMessage());
            }
        }
        
        return producto;
    }
    
    public Producto buscarImagen(){
        Producto producto = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarImagen(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfBuscarProducto.getText()));
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Blob imagen = resultSet.getBlob("imagenProducto");
                
                producto = (new Producto(imagen));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
              System.out.println(e.getMessage());
            }
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
