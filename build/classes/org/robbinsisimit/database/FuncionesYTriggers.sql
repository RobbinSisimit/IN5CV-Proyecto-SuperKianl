use superKinal;

-- total -----
DELIMITER $$
	create procedure sp_asignarTotal(in tot decimal(10,2),in factID int)
    begin
		update Facturas
			set total = tot
            where facturaID = factID;
    end$$
DELIMITER ;

-- funcion calcular total
DELIMITER $$
	create function fn_calcularTotal(factID int) returns decimal(10,2) deterministic
    begin
		declare total decimal(10,2) default 0.0;
        declare precio decimal(10,2);
        declare i int default 1;
        declare curFacturaID, curProductoID int;
        
        declare cursorDetalleFactura cursor for 
			select DF.facturaId, DF.productoId from DetalleFactura DF;
            
		open cursorDetalleFactura;
        
        totalLoop : loop
        
        fetch cursorDetalleFactura into curFacturaID,curProductoID;
        
        if (factID = curFacturaId)then
			set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
            set total = total + precio;
        end if;
        
        if i = 	(select count(*)from DetalleFactura)then
        leave totalLoop;
        end if;
        
        set i = i + 1;
        
        end loop totalLoop;
        
        call sp_asignarTotal(total,factID);
        
        return total;
    end$$
DELIMITER ;

select fn_calcularTotal(9);
 select * from facturas;
 
 DELIMITER $$
	create trigger tg_totalFactura
    after insert on DetalleFactura
    for each row
    begin
		declare total decimal(10,2);
        set total = fn_calcularTotal(new.facturaID);
    end$$
DELIMITER ;

DELIMITER $$
create trigger tg_restarStock
before insert on detalleFactura
for each row
begin
    if (select P.cantidadStock from productos P where productoId = NEW.productoId) = 0 then
		signal sqlstate'45000'
			set message_text="No hay existencias de ese producto, intentelo mas tarde";
    else
		update Productos p 
		set p.cantidadStock = (p.cantidadStock - 1) where productoId = NEW.productoId;
	end if;
 
end $$
DELIMITER ;



DELIMITER $$
create trigger fechaHoraFactura
before insert on DetalleFactura
for each row
begin
	Update Facturas
	set
			fecha = CURDATE(),
            hora = CURTIME()
			where facturaId = New.facturaId;
end $$
DELIMITER ;