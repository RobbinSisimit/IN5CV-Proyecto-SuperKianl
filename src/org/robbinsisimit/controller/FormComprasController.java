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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.robbinsisimit.model.Compra;
import org.robbinsisimit.system.Main;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class FormComprasController implements Initializable {

    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCompraId, tfFechaCompra, tfTotalCompra;
    @FXML
    Button btnGuardar, btnCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }  
    
    public void cargarDatos(Compra compra){
        /*tfCompraId.setText(Integer.toString(compra.getCompraId()));
        tfFechaCompra.setText(Date.toString(compra.getFechaCompra()));
        tfTotalCompra.setText(Double.toString(compra.getTotalCompra()));*/
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
