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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.model.Compra;
import org.robbinsisimit.system.Main;

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
    TableColumn colCompraId, colFechaComrpa, colTotalCompra;
    @FXML
    Button btnAgregar, btnEditar, btnEliminar, btnReporte, btnBuscar, btnRegresar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnAgregar){
            stage.formComprasView(1);
        }else if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }
        
    }

    public void cargarLista(){
        tblCompras.setItems(listarCompras());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("compraId"));
        colFechaComrpa.setCellValueFactory(new PropertyValueFactory<Compra, String>("fechaCompra"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compra, Double>("totalCompra"));
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
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
