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
import org.robbinsisimit.dto.CategoriaProductoDTO;
import org.robbinsisimit.model.CategoriaProducto;
import org.robbinsisimit.system.Main;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class FormCategoriaProductosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCategoriaProductoId,tfNombreCategoria,tfDescripcionCategoria;
    @FXML
    Button btnGuardar, btnCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto() != null){
            cargarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto());
        }
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuCategoriaProductoView();
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                agregarCategoriaProducto();
                stage.menuCategoriaProductoView();
            }else if(op == 2){
                editarCategoriaProducto();
                CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
                stage.menuCategoriaProductoView();
                
            }
        }
    }

    public void cargarDatos(CategoriaProducto categoriaProducto){
        tfCategoriaProductoId.setText(Integer.toString(categoriaProducto.getCategoriaProductosId()));
        tfNombreCategoria.setText(categoriaProducto.getNombreCategoria());
        tfDescripcionCategoria.setText(categoriaProducto.getDescripcionCategoria());
    }
    
    public void agregarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoria.getText());
            statement.setString(2, tfDescripcionCategoria.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
    
    public void editarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));
            statement.setString(2, tfNombreCategoria.getText());
            statement.setString(3, tfDescripcionCategoria.getText());
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
