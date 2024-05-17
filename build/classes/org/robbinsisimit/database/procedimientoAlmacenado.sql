use superKinal;

-- crud de clientes
-- agregar
DELIMITER $$
	create procedure sp_agregarCliente(nom varchar(30),ape varchar(30),tel varchar(15),dir varchar(150), nit varchar(30))
    begin
		insert into Clientes(nombre,apellido,telefono,direccion, nit) values
			(nom,ape,tel,dir, nit);
    end $$
DELIMITER ;
call sp_agregarCliente('Thaysa', 'Santos', '2364-5678','San cristobal', 'CF');

-- call sp_agregarCliente();
-- listar
delimiter $$
create procedure sp_listarClientes()
	begin
		select * from Clientes;
    end $$
delimiter ;

-- call sp_listarClientes();

-- eliminar
delimiter $$
create procedure sp_eliminarCliente(cliId int)
	begin
		delete from Clientes
		where clienteId = cliId;
    end $$
delimiter ;

-- call sp_eliminarCliente();
-- buscar
delimiter $$
create procedure sp_buscarClientes(cliId int)
	begin 
		select *from Clientes
        where clienteId = cliId;
    end $$
delimiter ;

-- call sp_buscarClientes(3);

-- editar
delimiter $$
create procedure sp_editarClientes(cliId int,nom varchar (30), ape varchar (30), tel varchar(15), dir varchar(150), nit varchar(30) )
	begin
        update Clientes
			set
            nombre = nom,
            apellido = ape,
            telefono = tel,
            direccion = dir,
            nit = nit
            where clienteId = cliId;			
    end $$
delimiter ;
-- call sp_editarClientes();

-- crud de cargos
-- agregar
DELIMITER $$
	create procedure sp_agregarCargo(nomCar varchar(30),desCar varchar(100))
    begin
		insert into Cargos(nombreCargo,descripcionCargo) values
			(nomCar, desCar);
    end $$
DELIMITER ;
call sp_agregarCargo('carnicero','Trabaja en el frio');
-- select * from Cargos;


-- listar
delimiter $$
create procedure sp_listarCargos()
	begin
		select * from Cargos;
    end $$
delimiter ;
-- call sp_listarCargos();

-- elimiar
delimiter $$
create procedure sp_eliminarCargo(carId int)
	begin
		delete from Cargos
		where cargoId = carId;
    end $$
delimiter ;

-- buscar
delimiter $$
create procedure sp_buscarCargo(carId int)
	begin 
		select *from Cargos
        where cargoId = carId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarCargos(carId int, nomCar varchar(30), desCar varchar(100)  )
	begin
        update Cargos
			set
            nombreCargo = nomCar,
            descripcionCargo = desCar
            where cargoId = carId;			
    end $$
delimiter ;

-- crud de empleados
-- agregar
DELIMITER $$
	create procedure sp_AgregarEmpleado(nom varchar(30), ape varchar(30), sue decimal (10,2), horE Time, horS time, carId int)
		begin
			insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) values 
				(nom, ape, sue, horE, horS, carId);
        end $$
DELIMITER ;
 
call  sp_AgregarEmpleado('Sergio', 'Santos', 150.50, '9:8:10', '10:10:10', 1);
 -- Listar
DELIMITER $$
create procedure sp_ListarEmpleados()
	begin 
		select *
			from Empleados;
    end $$
DELIMITER ;
 
-- call sp_ListarEmpleados();
-- Editar
DELIMITER $$
create procedure sp_EditarEmpleado(empId int, nom varchar(30), ape varchar(30), sue decimal (10,2), horE Time, horS time, carId int)
	begin
		update Empleados
			set nombreEmpleado = nom,
				apellidoEmpleado = ape,
                sueldo = sue,
                horaEntrada = horE,
                horaSalida = horS,
                cargoId = carId
                where empId = empleadoId;
    end $$
DELIMITER ;
 
-- call  sp_EditarEmpleado(1,'Cristian', 'agustin', 220.5, '10:10:10', '10:10:10', 1);
-- select * from Empleados;
-- Eliminar 
DELIMITER $$
create procedure sp_EliminarEmpleado(empId int)
	begin
		delete from Empleados
			where empId = empleadoId;
    end $$
DELIMITER ;
 
-- call sp_EliminarEmpleado(2);
-- Buscar 
DELIMITER $$
create procedure sp_BuscarEmpleado(empId int)
	begin
		select *
			from Empleados
            where empleadoId = empId;
    end $$
DELIMITER ;
 
-- call sp_BuscarEmpleado(1);
-- crud de Distribuidores
-- agregar
DELIMITER $$
	create procedure sp_agregarDistribuidores(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15), web varchar(50))
    begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis, dirDis, nitDis, telDis, web);
    end $$
DELIMITER ;
call sp_agregarDistribuidores('pastel','ciudad','321654','333-333','www');
-- select * from Distribuidores;

-- listar
delimiter $$
create procedure sp_listarDistribuidores()
	begin
		select * from Distribuidores;
    end $$
delimiter ;

-- eliminar
delimiter $$
create procedure sp_eliminarDistribuidores(disId int)
	begin
		delete from Distribuidores
		where distribuidorId = disId;
    end $$
delimiter ;
-- call sp_eliminarDistribuidores(2);
-- buscar
delimiter $$
create procedure sp_buscarDistribuidores(disId int)
	begin 
		select *from Distribuidores
        where distribuidorId = disId;
    end $$
delimiter ;
-- call sp_buscarDistribuidores(1);
-- editar
delimiter $$
create procedure sp_editarDistribuidores(disId int,nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15), web varchar(50))
	begin
        update Distribuidores
			set
            nombreDistribuidor = nomDis,
            direccionDistribuidor = dirDis,
            nitDistribuidor = nitDis,
            telefonoDistribuidor = telDis,
            web = web
            where distribuidorId = disId;			
    end $$
delimiter ;
-- call sp_editarDistribuidores(1, 'zzz','Ciudad','CF','not','not');
-- select * from Distribuidores;
-- crud de CategoriaProductos
-- agregar
DELIMITER $$
	create procedure sp_agregarCategoriaProducto(nomCat varchar(30), desCat varchar(100))
    begin
		insert into CategoriaProductos(nombreCategoria, descripcionCategoria) values
			(nomCat, desCat);
    end $$
DELIMITER ;
call sp_agregarCategoriaProducto('Cocina',' banquet');

-- listar
delimiter $$
create procedure sp_listarCategoriaProductos()
	begin
		select * from CategoriaProductos;
    end $$
delimiter ;
-- call sp_listarCategoriaProductos();
-- eliminar
delimiter $$
create procedure sp_eliminarCategoriaProducto(catId int)
	begin
		delete from CategoriaProductos
		where categoriaProductosId = catId;
    end $$
delimiter ;
-- call sp_eliminarCategoriaProducto(2);
-- select * from CategoriaProductos;

-- buscar
delimiter $$
create procedure sp_buscarCategoriaProducto(catId int)
	begin 
		select *from CategoriaProductos
        where categoriaProductosId = catId;
    end $$
delimiter ;
-- call sp_buscarCategoriaProducto(1);
-- Editar
delimiter $$
create procedure sp_editarCategoriaProductos(catId int,nomCat varchar(30), desCat varchar(100))
	begin
        update CategoriaProductos
			set
            nombreCategoria = nomCat,
            descripcionCategoria = desCat
            where categoriaProductosId = catId;			
    end $$
delimiter ;
-- all sp_editarCategoriaProductos(1,'zzz','xd');
-- select * from CategoriaProductos;



-- crud de compras
-- agregar
DELIMITER $$
create procedure sp_agregarCompra(in fecCom date, in can int, in proId int)
	begin 
		declare nuevaCompraId int;
		insert into Compras (fechaCompra) values
			(fecCom);
            
		set nuevaCompraId = last_insert_id();
    end $$
DELIMITER ;
call sp_agregarDetalleCompra(can,proId,nuevaCompraId);
 
 -- listar
DELIMITER $$
	create procedure sp_ListarCompra()
    begin
		select C.compraId, C.fechaCompra, DE.cantidadCompra,
		concat(P.nombreProducto, ' | ', P.precioCompra) as 'producto',
        C.totalCompra from DetalleCompra DE
        join Compras C on C.compraId = DE.compraId
        left join Productos P on P.productoId = DE.productoId;
        
    end $$
DELIMITER ;
call sp_ListarCompra();
 
DELIMITER $$
	create procedure sp_EliminarCompra(comId int)
    begin
		delete from Comprars
        where compraId = comId;
    end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_buscarCompras(comId int)
	BEGIN
		select * 
			from Compras
            where compraId = comId;
	END $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_editarCompra(comId int, fec date, tot decimal(10,2))
	BEGIN
		update Compras
			set
				fechaCompra = fec,
                totalCompra = tot
					where compraId = comId;
	END $$
DELIMITER ;

-- ----------- Facturas ----------------------
-- Agregar
DELIMITER $$
create procedure sp_AgregarFactura(fech date, hor time, cliId int(11), empId int(11), tot decimal(10,2))
begin
	insert into Facturas(fecha,hora,clienteId,empleadoId,total)values
		(fech,hor,cliId,empId,tot);
end $$
DELIMITER ;
call sp_AgregarFactura('12-4-12','12:34:55',1,1,'1330');
-- Listar
DELIMITER $$
create procedure sp_ListarFacturas()
begin
	select * from Facturas;
end $$
DELIMITER ;

-- Eliminar
DELIMITER $$
create procedure sp_EliminarFactura(facId int)
begin
	delete from Facturas where facturaId = facId;
end $$
DELIMITER ;

-- Buscar
DELIMITER $$ 
create procedure sp_BuscarFactura(facId int)
begin
	select * from Facturas where facturaId = facId;

end $$
DELIMITER ;

-- Editar
DELIMITER $$
create procedure sp_EditarFactura(facId int,fech date, hor time, cliId int(11), empId int(11), tot decimal(10,2))
begin
	Update Facturas
		set
			fecha = fech,
            hora = hor,
            clienteId = cliId,
            empleadoId = empId,
            total = tot
				where facturaId = facId;
end $$
DELIMITER ; 

-- ticketSoporte
 
DELIMITER $$
	create procedure sp_AgregarTicketSoporte(des varchar(250), cliId int, facId int)
		begin
			insert into TicketSoporte (descripcionTicket, estatus, clienteId, facturaId) values 
				(des, 'Recién Creado', cliId, facId);
        end $$
DELIMITER ;
call sp_AgregarTicketSoporte('error',1,1);
 
DELIMITER $$
create procedure sp_ListarTicketSoporte()
	begin 
		select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus, 
				Concat(C.clienteId, C.nombre, C.apellido) AS 'Clientes', 
                TS.facturaId from TicketSoporte TS
        join Clientes C on TS.clienteId = C.clienteId;

    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_EditarTicketSoporte(ticId int, des varchar(250), est varchar(30), cliId int, facId int)
	begin
		update TicketSoporte
			set descripcionTicket = des,
				estatus = est,
                clienteId = cliId,
                facturaId = facId
                where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_EliminarTicketSoporte(ticId int)
	begin
		delete from TicketSoporte
			where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_BuscarTicketSoporte(ticId int)
	begin
		select *
			from TicketSoporte
            where ticId = ticketSoporteId;
    end $$
DELIMITER ;

-- ------------ Productos ---------------------------------------------------------------------
-- Agregar
DELIMITER $$
create procedure sp_AgregarProducto(in nomP varchar(50),in desP varchar(100),in canS int(11),in preVU decimal(10,2), in preVM decimal(10,2), in preC decimal(10,2),in imag LONGBLOB,in disId int(11),in catPId int(11))
begin
	insert into Productos(nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidorId,categoriaProductosId) values
		(nomP,desP,canS,preVU,preVM,preC,imag,disId,catPId);
	
end $$
DELIMITER ;
-- call sp_AgregarProducto('Pizza','Se come',12,7,5,4,1,1);
select * from Productos;

-- Listar
DELIMITER $$
create procedure sp_ListarProducto()
begin
	select p.productoId,p.nombreProducto,p.descripcionProducto,p.cantidadStock,p.precioVentaUnitario,p.precioVentaMayor,p.precioCompra,p.imagenProducto,
    CONCAT('ID: ',p.distribuidorId, ' | ','Nombre: ',d.nombreDistribuidor) as 'Distribuidores',
    CONCAT('ID: ',cp.categoriaProductosId, ' | ',cp.nombreCategoria) as 'Categoria'from Productos p
    join Distribuidores d on p.distribuidorId = d.distribuidorId
    join CategoriaProductos cp on p.categoriaProductosId = cp.categoriaProductosId;
end $$
DELIMITER ;
call sp_ListarProducto();


-- Eliminar
DELIMITER $$
create procedure sp_EliminarProducto(in proId int)
begin
	delete from Productos where productoId = proId; 
end $$
DELIMITER ;

-- Buscar
DELIMITER $$
create procedure sp_buscarProducto(in proId int)
	begin 
		select P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario,
        P.precioVentaMayor, P.precioCompra, P.imagenProducto,
        concat(D.nombreDistribuidor, ' | ', D.telefonoDistribuidor) as 'Distribuidores', 
        concat('ID: ',CP.categoriaProductoId, ' ', CP.nombreCategoria) as 'Categoria' from Productos P
        join Distribuidores D on D.distribuidorId = P.distribuidorId
        left join CategoriaProductos CP on CP.categoriaProductoId = P.categoriaProductoId
        where productoId = proId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_buscarImagen(in proId int)
begin
	select imagenProducto from Productos
    where productoId = proId;
end $$
DELIMITER ;

--  Editar	
DELIMITER $$
create procedure sp_EditarProducto(proId int,in nomP varchar(50),in desP varchar(100),in canS int(11),in preVU decimal(10,2), in preVM decimal(10,2), in preC decimal(10,2),in imgP longblob,in disId int(11),in catPId int(11))
begin
	Update Productos
		set
			nombreProducto = nomP,
            descripcionProducto = desP,
            cantidadStock = canS,
            precioVentaUnitario = preVU,
            precioVentaMayor = preVM,
            precioCompra = preC,
            imagenProducto = imaP,
            distribuidorId = disId,
            categoriaProductoId = catPId
				where productoId = proId;
	
end $$
DELIMITER ;
-- call sp_EditarProducto();
select * from Productos;


-- --------------------- Promociones ------------------------------------------
 
-- Agregar
delimiter $$
create procedure sp_agregarPromociones(in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		insert into Promociones (precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId) values
			(prePro, descPro, feIni, feFina, proId);
    end $$
delimiter ;
call sp_agregarPromociones(1200.50, 'descuento en carne de res', '2023-4-2', '2023-5-9', 1);
-- select * from Promociones;
 
-- listar
delimiter $$
create procedure sp_listarPromociones()
	begin
		select * from Promociones;
    end $$
delimiter ;
call sp_listarPromociones();
 
 
-- buscar
delimiter $$
create procedure sp_buscarPromociones(in promoId int)
	begin
		select * from Promociones
			where promocionId = promoId;
    end $$
delimiter ;
 
 
-- eliminar
delimiter $$
create procedure sp_eliminarPromociones(in promoId int)
	begin
		delete 
			from Promociones
				where promocionId = promoId;
    end $$
delimiter ;
 
-- editar
delimiter $$
create procedure sp_editarPromociones(in promoId int, in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		update Promociones
			set 
            precioPromocion = prePro,
            descripcionPromocion = descPro,
            fechaInicio = feIni,
            fechaFinalizacion = feFina,
            productoId = proId
            where promocionId = promoId;
    end $$
delimiter 
-- call sp_editarPromociones(1, 120.50,'ZZZ', '2023-03-02','2023-05-09', 1);
-- select * from Promociones;

-- ---------------- detalleCompra
 
DELIMITER $$
	create procedure sp_AgregarDetalleCompra(cantC int, proId int,comId int)
    begin
		insert into DetalleCompra(cantidadCompra,productoId,compraId) values
			(cantC,proId,comId);
    end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_ListarDetalleCompra()
    begin
		select* from DetalleCompra;
    end$$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_EliminarDetalleCompra(dtId int)
    begin
		delete from DetalleCompra
        where detalleCompraId = dtId;
    end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_buscarDetalleCompra(dtId int)
	BEGIN
		select * 
			from DetalleCompra 
            where detalleCompraId = dtId;
	END $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_editarDetalleCompra(in comId int, in fecha date,in total decimal(10,2),in cantidad int, in proId int)
	begin 
		start transaction;
		update Compras
			set
				fechaCompra = fecha,
                totalCompra =  total
                where compraId = comId;
    
		update DetalleCompra
			set 
				cantidadCompra = cantidad,
                productoId = proId
                where compraId = comId;
		commit;
    end $$
DELIMITER ;
-- ------------------ DetalleFacturas
-- Agregar
DELIMITER $$
create procedure sp_AgregarDetalleFactura(facId int(11), proId int(11))
begin
	insert into DetalleFactura(facturaId, productoId) values (facId, proId);
end $$
DELIMITER ;


-- Listar
DELIMITER $$
create procedure sp_ListarDetalleFactura()
begin
	select * from DetalleFactura;
end $$
DELIMITER ;

-- Eliminar
DELIMITER $$
create procedure sp_EliminarDetalleFactura(detFId int(11))
begin
	delete from DetalleFactura where detalleFacturaId = detFId;
end $$
DELIMITER ;

-- Buscar
DELIMITER $$
create procedure sp_BuscarDetalleFactura(detFId int(11))
begin
	select * from DetalleFactura where detalleFacturaId = detFId;
end $$
DELIMITER ;

-- Editar
DELIMITER $$
create procedure sp_EditarDetalleFactura(detFId int(11),facId int(11), proId int(11))
begin
	Update DetalleFactura
		set
			facturaId = facId,
            productoId = proId
				where detalleFacturaId = detFId;
end $$
DELIMITER ;

-- call sp_AgregarDetalleFactura(1,1);

-- asignar encargado 
DELIMITER $$
create procedure sp_AsignarEncargado(empId int, encId int)
	begin
    Update Empleados
		set encargadoId = encId 
			where empId = empleadoId;
    end $$
DELIMITER ;
-- call sp_AsignarEncargado(2,1);
 

 -- select * from Empleados;
select * from Productos; 
-- select * from Clientes;
-- select * from Distribuidores;