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
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.robbinsisimit.dao.Conexion;
import org.robbinsisimit.model.Cliente;
import org.robbinsisimit.model.DetalleFactura;
import org.robbinsisimit.model.Factura;
import org.robbinsisimit.model.TicketSoporte;
import org.robbinsisimit.system.Main;

/**
 * FXML Controller class
 *
 * @author robin
 */
public class MenuTicketSoporteController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    ComboBox cmbClientes, cmbEstatus,cmbFacturas;
    
    @FXML
    Button btnRegresar, btnGuardar,btnVaciar;  
    
    @FXML
    TableView tblTickets;    
    
    @FXML
    TableColumn colTicketId,colDescripcion,colEstatus,colCliente,colFacturaId;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    TextField tfTicketId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfTicketId.getText().equals("")){
                agregarTicket();
                cargarDatos();
            }else{
                editarTicket();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
    
    //VACIAR TODOS LOS CAMPOS PARA AGREGAR UN NUEVO TICKET
    public void vaciarCampos(){
        
        tfTicketId.clear();
        taDescripcion.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
        cmbFacturas.getSelectionModel().clearSelection();
    }
    // CARGAR DATOS ALA TABLA VIEW
    public void cargarDatos(){
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("cliente"));
        colFacturaId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("facturaId"));
        tblTickets.getSortOrder().add(colTicketId);
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        for(int i = 0; i >= cmbClientes.getItems().size() ; i++){
            String clienteCmb = cmbClientes.getItems().get(i).toString();
            String clienteTbl = ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getCliente();
            if(clienteCmb.equals(clienteTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexFactura(){
        int index = 0;
        for(int i = 0; i >= cmbFacturas.getItems().size(); i++){
            String facturaCmb = cmbFacturas.getItems().get(i).toString();
            String facturaTbl = ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getFactura();
            if(facturaCmb.equals(facturaTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void cargarDatosEditar(){
        TicketSoporte ts = (TicketSoporte)tblTickets.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));
            cmbEstatus.getSelectionModel().select(0);
            cmbClientes.getSelectionModel().select(obtenerIndexCliente());
            cmbFacturas.getSelectionModel().select(obtenerIndexFactura());
            taDescripcion.setText(ts.getDescripcionTicket());
        }
    }
    
    //Cargar el comboBox de clientes

    
    //CARGAR COMBOBOX DE ESTATUS
    public void cargarCmbEstatus(){
        cmbEstatus.getItems().add("En proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    // CONSULTA SP LISTAR TICKETS SOPORTO ALA BASE DE DATOS
    public ObservableList<TicketSoporte> listarTickets(){
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarTicketSoporte()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                String factura = resultSet.getString("facturaId");
                
                tickets.add(new TicketSoporte(ticketSoporteId,descripcion,estatus,cliente,factura));
                
            }
        }catch(SQLException e ){
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
        
        return FXCollections.observableList(tickets);
    }
    
    //LA CONSULTA DE LISTAR CLIENTES ALA BASE DE DATOS "SE UTILIZA PARA LLENAR EL COMBOBOX CLIENTES"
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarClientes()";
            statement  = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
    }
    
    public ObservableList<DetalleFactura> listarDetalleFacturas(){
        ArrayList<DetalleFactura> detalleFacturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                double total = resultSet.getDouble("total");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                String producto = resultSet.getString("producto");
                
                detalleFacturas.add(new DetalleFactura(producto,facturaId,fecha,hora,total,cliente,empleado));
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
        return FXCollections.observableList(detalleFacturas);
    }
    
    public void agregarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarTicketSoporte(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcion.getText());
            statement.setInt(2, ((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, ((Factura)cmbFacturas.getSelectionModel().getSelectedItem()).getFacturaId());
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
    
    public void editarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarTicketSoporte(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfTicketId.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, cmbEstatus.getSelectionModel().getSelectedItem().toString());
            statement.setInt(4, ((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setString(5,((DetalleFactura)cmbFacturas.getSelectionModel().getSelectedItem()).toString());
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCmbEstatus();
        cmbClientes.setItems(listarClientes());
        cmbFacturas.setItems(listarDetalleFacturas());
        cargarDatos();
    }    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
