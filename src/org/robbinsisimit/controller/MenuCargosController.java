/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.dto.CargoDTO;
import org.robbinsisimit.model.Cargo;
import org.robbinsisimit.system.Main;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class MenuCargosController implements Initializable {

    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    
    @FXML
    TableView tblCargos;
    @FXML
    Button btnRegresar, btnAgregar, btnEditar,btnEliminar,btnBuscar;
    @FXML
    TableColumn colCargoId, colNombreCargo, colDescripcion;
    @FXML
    TextField tfCargoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formCargosView(1);
        }else if(event.getSource() == btnEditar){
            CargoDTO.getCargoDTO().setCargo((Cargo)tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargosView(2);
        }else if(event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            if(tfCargoId.getText().equals("")){
                cargarLista();
            }else{
                tblCargos.getItems().add(buscarCargo());
                colCargoId .setCellValueFactory(new PropertyValueFactory< Cargo, Integer>("cargoId"));
                colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("nombreCargo"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("descripcionCargo"));
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        cargarLista();
    }
    
    public void cargarLista(){
        tblCargos.setItems(listarCargo());
        colCargoId .setCellValueFactory(new PropertyValueFactory< Cargo, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("nombreCargo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("descripcionCargo"));
    }
    
    public ObservableList<Cargo> listarCargo(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
            }
            
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

            }
        }
        return FXCollections.observableList(cargos);
    }
    
    public Cargo buscarCargo(){
        Cargo cargo = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargo = (new Cargo(cargoId, nombreCargo, descripcionCargo));
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
        return cargo;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    
    
    
}
