/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.robbinsisimit.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.dto.CargoDTO;
import org.robbinsisimit.model.Cargo;
import org.robbinsisimit.system.Main;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class FormCargosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCargoId, tfNombre, tfDescripcion;
    @FXML
    Button btnGuardar, btnCancelar;
    
    @Override
    public void initialize(URL locatiom, ResourceBundle resource) {
        if(CargoDTO.getCargoDTO().getCargo() != null){
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
        }
    } 
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                agregarCargo();
                stage.menuCargosView();
            }else if(op == 2){
                editarCargo();
                CargoDTO.getCargoDTO().setCargo(null);
                stage.menuCargosView();
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuCargosView();
            CargoDTO.getCargoDTO().setCargo(null);
        }
    }
    
    public void cargarDatos(Cargo cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombre.setText(cargo.getNombreCargo());
        tfDescripcion.setText(cargo.getDescripcionCargo());
    }
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfDescripcion.getText());
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
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfDescripcion.getText());
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

    public void setOp(int op) {
        this.op = op;
    }
    
    
    
}
