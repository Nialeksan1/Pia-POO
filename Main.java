import java.io.Console;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.lang.System;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        Scan scan = new Scan();
        Menu menu = new Menu(scan.getScanner());
        menu.desplegarMenu(1);
        scan.cerrarScanner();
    }
}

class ConexionDB {
    private static Connection conexion = null;

    public static Connection getConexion() throws SQLException {
        if(conexion == null || conexion.isClosed()) {
            String url = "jdbc:postgresql://localhost:5432/agencia_autos";
            Properties props = new Properties();
            props.setProperty("user", System.getenv("DB_USERNAME"));
            props.setProperty("password", System.getenv("DB_PASSWORD"));
            try {
                conexion = DriverManager.getConnection(url, props);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return conexion;
    }
}

class Menu
{
    private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    private interface Mostrar {
        void mostrar();
    }

    private void desplegarMenu(int opcMax, Mostrar menu, int num_menu) {
        Integer opcion;
        while(true) {
            try {
                menu.mostrar();
                opcion = scanner.nextInt();
                scanner.nextLine();
                if(opcion<1 || opcion>opcMax) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + opcMax + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + opcMax + "\n");
            }
        }
        switch(num_menu) {
            case 1:
                opciones_cliente(opcion);
                break;
            case 2:
                opciones_citas(opcion);
                break;
            case 3:
                opciones_admin(opcion);
                break;
            case 4:
                opciones_modificar(opcion);
                break;
        }
    }

    private void menu_cliente() {
        System.out.println("##############################################################");
        System.out.println("# Agencia de Autos                                           #");
        System.out.println("## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ##");
        System.out.println("# Ingresa el numero de la opcion a la que quisieras ingresar #");
        System.out.println("# 1) Citas                                                   #");
        System.out.println("# 2) Comprar Auto                                            #");
        System.out.println("# 3) Ingresar como admin                                     #");
        System.out.println("# 4) Salir                                                   #");
        System.out.println("##############################################################");
        System.out.print("R= ");
    }

    private void menu_citas() {
        System.out.println("##############################################################");
        System.out.println("# Agencia de Autos -> Citas                                  #");
        System.out.println("## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ##");
        System.out.println("# Ingresa el numero de la opcion a la que quisieras ingresar #");
        System.out.println("# 1) Agendar Cita                                            #");
        System.out.println("# 2) Cancelar Cita                                           #");
        System.out.println("# 3) Regresar                                                #");
        System.out.println("##############################################################");
        System.out.print("R= ");
    }

    private void menu_admin() {
        System.out.println("##############################################################");
        System.out.println("# Agencia de Autos -> ADMIN                                  #");
        System.out.println("## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ##");
        System.out.println("# Ingresa el numero de la opcion a la que quisieras ingresar #");
        System.out.println("# 1) Mostrar informacion de cliente                          #");
        System.out.println("# 2) Modificar informacion de cliente                        #");
        System.out.println("# 3) Checar citas                                            #");
        System.out.println("# 4) Volver                                                  #");
        System.out.println("##############################################################");
        System.out.print("R= ");
    }

    private void menu_modificar() {
        System.out.println("##############################################################");
        System.out.println("# Agencia de Autos -> ADMIN -> Modificar info. de cliente    #");
        System.out.println("## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ## # ##");
        System.out.println("# Ingresa el numero de la opcion a la que quisieras ingresar #");
        System.out.println("# 1) Datos del cliente                                       #");
        System.out.println("# 2) Ubicacion del cliente                                   #");
        System.out.println("# 3) Regresar                                                #");
        System.out.println("##############################################################");
        System.out.print("R= ");
    }

    public void desplegarMenu(int num_menu) {
        switch(num_menu) {
            case 1:
                desplegarMenu(4, () -> menu_cliente(), num_menu);
                break;
            case 2:
                desplegarMenu(3, () -> menu_citas(), num_menu);
                break;
            case 3:
                desplegarMenu(5, () -> menu_admin(), num_menu);
                break;
            case 4:
                desplegarMenu(3, () -> menu_modificar(), num_menu);
                break;
            default:
                desplegarMenu(4, () -> menu_cliente(), num_menu);
                break;
        }
    }

    public void opciones_cliente(Integer opcion) {
        Opciones_Cliente opciones = new Opciones_Cliente(scanner);
        switch (opcion) {
            case 1:
                opciones.citas_cliente();
                break;
            case 2:
                opciones.comprarAuto_cliente();
                break;
            case 3:
                opciones.ingresarAdmin_cliente();
                break;
            case 4:
                opciones.salir();
                break;
        }
        desplegarMenu(1);
    }

    public void opciones_citas(Integer opcion) {
        Opciones_Citas opciones = new Opciones_Citas(scanner);
        switch(opcion) {
            case 1:
                opciones.agendar_citas();
                break;
            case 2:
                opciones.cancelar_citas();
                break;
            case 3:
                opciones.regresar();
                break;
        }
        desplegarMenu(2);
    }

    public void opciones_admin(Integer opcion) {
        Opciones_Admin opciones = new Opciones_Admin(scanner);
        switch(opcion) {
            case 1:
                opciones.mostrar_admin();
                break;
            case 2:
                opciones.modificar_admin();
                break;
            case 3:
                opciones.checar_admin();
                break;
            case 4:
                opciones.regresar();
                break;
        }
        desplegarMenu(3);
    }

    public void opciones_modificar(Integer opcion) {
        Opciones_Modificar opciones = new Opciones_Modificar(scanner);
        switch(opcion) {
            case 1:
                opciones.datos_modificar();
                break;
            case 2:
                opciones.ubicacion_modificar();
                break;
            case 3:
                opciones.regresar();
                break;
        }
    }
}

class Scan {
    private Scanner scanner;

    public Scan() {
        scanner = new Scanner(System.in);
    }

    public void cerrarScanner() {
        scanner.close();
    }

    public Scanner getScanner() {
        return scanner;
    }
}

class Opciones_Cliente {
    private Scanner scanner;

    public Opciones_Cliente(Scanner scanner) {
        this.scanner = scanner;
    }

    public void citas_cliente() {
        System.out.print("\033[H\033[2J");
        Menu menu = new Menu(scanner);
        menu.desplegarMenu(2); 
    }

    public void comprarAuto_cliente() {
        System.out.println("Has ingresado a compra de auto.");
        Cliente cliente = new Funciones(scanner).datosCliente();
        Boolean confirmacion = false;
        int clienteid=0;

        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet set1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        ResultSet set2 = null;
        PreparedStatement statement4 = null;
        try {
            conn = ConexionDB.getConexion();

            String query1 = "SELECT * FROM clientes WHERE nombre = ? AND paterno = ? AND materno = ? AND correo = ? AND celular = ?";
            String query2 = "INSERT INTO clientes (nombre, paterno, materno, correo, celular) VALUES (?,?,?,?,?)";
            String query3 = "INSERT INTO ubicacion (clienteid, calle, numero, codigopostal, municipio, estado) VALUES (?,?,?,?,?,?)";

            statement1 = conn.prepareStatement(query1);
            statement1.setString(1, cliente.getNombre());
            statement1.setString(2, cliente.getPaterno());
            statement1.setString(3, cliente.getMaterno());
            statement1.setString(4, cliente.getCorreo());
            statement1.setString(5, cliente.getCelular());

            set1 = statement1.executeQuery();

            if(set1.next()) {
                clienteid = set1.getInt("clienteid");
            } else {
                confirmacion = true;
            }
            // statement1.close();
            // set1.close();

            if(confirmacion) {
                statement2 = conn.prepareStatement(query2);
                statement2.setString(1, cliente.getNombre());
                statement2.setString(2, cliente.getPaterno());
                statement2.setString(3, cliente.getMaterno());
                statement2.setString(4, cliente.getCorreo());
                statement2.setString(5, cliente.getCelular());
                statement2.executeUpdate();
                // statement2.close();
                System.out.println("\nDatos personales guardados");
                
                statement3 = conn.prepareStatement(query1);
                statement3.setString(1, cliente.getNombre());
                statement3.setString(2, cliente.getPaterno());
                statement3.setString(3, cliente.getMaterno());
                statement3.setString(4, cliente.getCorreo());
                statement3.setString(5, cliente.getCelular());
                set2 = statement3.executeQuery();
                set2.next();
                clienteid = set2.getInt("clienteid");
                // set2.close();
                // statement3.close();

                statement4 = conn.prepareStatement(query3);
                statement4.setInt(1, clienteid);
                statement4.setString(2, cliente.getUbicacion().getCalle());
                statement4.setString(3, cliente.getUbicacion().getNumero());
                statement4.setString(4, cliente.getUbicacion().getCodigoPostal());
                statement4.setString(5, cliente.getUbicacion().getMunicipio());
                statement4.setString(6, cliente.getUbicacion().getEstado());
                statement4.executeUpdate();
                // statement4.close();
                System.out.println("Ubicacion guardada");
            }
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (statement4 != null) {
                try {
                    statement4.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (set2 != null) {
                try {
                    set2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement3 != null) {
                try {
                    statement3.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (set1 != null) {
                try {
                    set1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        Autos auto = new Funciones(scanner).datosAuto();

        String sql = "SELECT * FROM autos WHERE uso = ? AND version = ? AND modeloid = ?";
        int autoid = 0;
        String aux = " ";
        int cantidad = 0;
        Connection connection = null;
        PreparedStatement stmn1 = null;
        ResultSet rs1 = null;
        try {
            connection = ConexionDB.getConexion();
            stmn1 = connection.prepareStatement(sql);
            stmn1.setString(1, auto.getUso());
            stmn1.setString(2, auto.getVersion());
            stmn1.setInt(3, auto.getModelo());
            rs1 = stmn1.executeQuery();
            rs1.next();
            autoid = rs1.getInt("autoid");
            aux = rs1.getString("precio");
            cantidad = rs1.getInt("cantidad");
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmn1 != null) {
                try {
                    stmn1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        BigDecimal costo = new BigDecimal(aux.replaceAll("[^\\d.]", ""));

        Cotizacion cotizacion = new Funciones(scanner).datosCotizaciones((costo.multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.HALF_UP)).toString());
        
        System.out.println("A continuacion se presenta la tabla del plazo segun los datos obtneidos:\n");
        generar_tabla(cotizacion, costo);
        
        Integer opcion;
        while(true) {
            try {
                System.out.print("Enviar solicitud de compra?\n[1] Si\n[Cualquier otro numero] NO\nR=");
                opcion = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debe ser un dato numerico");
            }
        }
        if(opcion == 1) {
            cantidad--;
            String query1 = "INSERT INTO auto_cliente (autoid, color, clienteid) VALUES (?,?,?)";
            String query2 = "SELECT auto_cliente_id FROM auto_cliente WHERE autoid = ? AND color = ? AND clienteid = ?";
            String query3 = "UPDATE autos SET cantidad = ? WHERE autoid = ?";
            String query4 = "INSERT INTO cotizaciones (auto_clienteid, planfinanciamiento, personalidadfiscal, enganche, plazo, aseguradora, pago, clienteid) VALUES (?,?,?,?,?,?,?,?)";
            int auto_clienteid = 0;
            
            Connection conexion = null;
            PreparedStatement stmn2 = null;
            PreparedStatement statement = null;
            ResultSet rs2 = null;
            PreparedStatement stmn3 = null;
            PreparedStatement stmn4 = null;
            try {
                conexion = ConexionDB.getConexion();
                stmn2 = conexion.prepareStatement(query1);
                stmn2.setInt(1, autoid);
                stmn2.setString(2, auto.getColor());
                stmn2.setInt(3, clienteid);
                int filasInsertadas = stmn2.executeUpdate();
                if(filasInsertadas>0) {
                    System.out.println("Se ha aniadido el auto con exito");
                }

                statement = conexion.prepareStatement(query2);
                statement.setInt(1, autoid);
                statement.setString(2, auto.getColor());
                statement.setInt(3, clienteid);
                rs2 = statement.executeQuery();
                rs2.next();
                auto_clienteid = rs2.getInt("auto_cliente_id");


                stmn3 = conexion.prepareStatement(query3);
                stmn3.setInt(1, cantidad);
                stmn3.setInt(2, autoid);
                filasInsertadas = stmn3.executeUpdate();
                if(filasInsertadas>0) {
                    System.out.println("Se ha actualizado la cantidad de autos con exito");
                }

                stmn4 = conexion.prepareStatement(query4);
                stmn4.setInt(1, auto_clienteid);
                stmn4.setString(2, cotizacion.getPlanFinanciamiento());
                stmn4.setString(3, cotizacion.getPersonalidadFiscal());
                stmn4.setBigDecimal(4, costo.multiply(new BigDecimal("0.15")));
                stmn4.setString(5, cotizacion.getPlazo());
                stmn4.setString(6, cotizacion.getAseguradora());
                stmn4.setString(7, cotizacion.getTipoPago());
                stmn4.setInt(8, clienteid);
                filasInsertadas = stmn4.executeUpdate();
                if(filasInsertadas>0) {
                    System.out.println("Se ha actualizado el registrp de cotizaciones con exito");
                }
            } catch (Exception e) {
                System.err.println("Error al conectar: " + e.getMessage());
            } finally {
                if (stmn4 != null) {
                    try {
                        stmn4.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmn3 != null) {
                    try {
                        stmn3.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs2 != null) {
                    try {
                        rs2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmn2 != null) {
                    try {
                        stmn2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void generar_tabla(Cotizacion cotizacion, BigDecimal costo) {
        double precio = costo.doubleValue();
        double enganche = Double.parseDouble(cotizacion.getEnganche());
        int plazo = Integer.parseInt(cotizacion.getPlazo());
        double mensualidad = (precio-enganche)/plazo;
        
        Map<String, Double> aseguradoras = new HashMap<>();
        aseguradoras.put("QUALITAS", 10957.00);
        aseguradoras.put("GNP", 13621.00);
        aseguradoras.put("CHUB", 16807.00);

        double menAseguradora = aseguradoras.get(cotizacion.getAseguradora())/plazo;

        precio = precio-enganche;
        double iva = menAseguradora*0.16;
        System.out.println("Pago  Saldo Auto          Mensualidad         Aseguradora IVA          Monto a pagar");
        for(int i = 1; i <= plazo; i++) {
            System.out.printf("%4d  $ %14.2f   $%10.2f          $%10.2f  $%.2f         $%10.2f\n",i,precio,mensualidad,menAseguradora,iva,mensualidad+menAseguradora+iva);
            precio-=mensualidad;
        }

    }

    public void ingresarAdmin_cliente() {
        Menu menu = new Menu(scanner);
        Console console = System.console();
        if(console == null) {
            System.out.print("Consola no disponible");
        }

        System.out.print("\033[H\033[2J");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        char[] pass = console.readPassword("Contrasenia: ");
        String contrasenia = new String(pass);

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;
        try {
            conn = ConexionDB.getConexion();
            String sql = "SELECT * FROM administrador WHERE admin = ? AND contrasenia = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, usuario);
            statement.setString(2, contrasenia);
            resultado = statement.executeQuery();

            if(resultado.next()) {
                System.out.println("Inicio de sesion exitoso!\n");
                menu.desplegarMenu(3);
            } else {
                System.out.println("Credenciales incrorrectas!\n");
                menu.desplegarMenu(1);
            }

            // resultado.close();
            // statement.close();
            // conn.close();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void salir() {
        System.out.println("Gracias por usar el programa\nHasta pronto!");
        System.exit(0);
    }
}

class Opciones_Citas {
    private Scanner scanner;

    public Opciones_Citas(Scanner scanner) {
        this.scanner = scanner;
    }

    public void agendar_citas() {
        System.out.println("Agendar cita");
        Cliente cliente = new Funciones(scanner).datosCliente();
        Boolean confirmacion = false;
        
        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet set1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        ResultSet set2 = null;
        PreparedStatement statement4 = null;
        PreparedStatement statement5 = null;
        PreparedStatement statement6 = null;
        ResultSet rs = null;
        try {
            conn = ConexionDB.getConexion();

            String query1 = "SELECT * FROM clientes WHERE nombre = ? AND paterno = ? AND materno = ? AND correo = ? AND celular = ?";
            String query2 = "INSERT INTO clientes (nombre, paterno, materno, correo, celular) VALUES (?,?,?,?,?)";
            String query3 = "INSERT INTO ubicacion (clienteid, calle, numero, codigopostal, municipio, estado) VALUES (?,?,?,?,?,?)";
            String query4 = "INSERT INTO citas (clienteid, horario, servicio) VALUES (?,?,?)";

            statement1 = conn.prepareStatement(query1);
            statement1.setString(1, cliente.getNombre());
            statement1.setString(2, cliente.getPaterno());
            statement1.setString(3, cliente.getMaterno());
            statement1.setString(4, cliente.getCorreo());
            statement1.setString(5, cliente.getCelular());

            set1 = statement1.executeQuery();

            int clienteid=0;
            if(set1.next()) {
                clienteid = set1.getInt("clienteid");
            } else {
                confirmacion = true;
            }
            // statement1.close();
            // set1.close();

            if(confirmacion) {
                statement2 = conn.prepareStatement(query2);
                statement2.setString(1, cliente.getNombre());
                statement2.setString(2, cliente.getPaterno());
                statement2.setString(3, cliente.getMaterno());
                statement2.setString(4, cliente.getCorreo());
                statement2.setString(5, cliente.getCelular());
                statement2.executeUpdate();
                // statement2.close();
                System.out.println("\nDatos personales guardados");
                
                statement3 = conn.prepareStatement(query1);
                statement3.setString(1, cliente.getNombre());
                statement3.setString(2, cliente.getPaterno());
                statement3.setString(3, cliente.getMaterno());
                statement3.setString(4, cliente.getCorreo());
                statement3.setString(5, cliente.getCelular());
                set2 = statement3.executeQuery();
                if(set2.next()) {
                    clienteid = set2.getInt("clienteid");
                }
                // set2.close();
                // statement3.close();

                statement4 = conn.prepareStatement(query3);
                statement4.setInt(1, clienteid);
                statement4.setString(2, cliente.getUbicacion().getCalle());
                statement4.setString(3, cliente.getUbicacion().getNumero());
                statement4.setString(4, cliente.getUbicacion().getCodigoPostal());
                statement4.setString(5, cliente.getUbicacion().getMunicipio());
                statement4.setString(6, cliente.getUbicacion().getEstado());
                statement4.executeUpdate();
                // statement4.close();
                System.out.println("Ubicacion guardada");
            }

            int citaid;
            Citas cita = new Funciones(scanner).datosCita();
            statement5 =  conn.prepareStatement(query4);
            statement5.setInt(1,clienteid);
            statement5.setTimestamp(2,cita.getHorario());
            statement5.setString(3,cita.getServicio());
            statement5.executeUpdate();
            // statement5.close();

            String sql = "SELECT citaid FROM citas WHERE clienteid = ? AND horario = ? AND servicio = ?";
            statement6 = conn.prepareStatement(sql);
            statement6.setInt(1, clienteid);
            statement6.setTimestamp(2, cita.getHorario());
            statement6.setString(3, cita.getServicio());
            rs = statement6.executeQuery();
            rs.next();
            citaid = rs.getInt("citaid");
            System.out.println("Cita guardada\nTu numero de cita es " + citaid);
            // statement6.close();
            // rs.close();
            
            // conn.close();
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement6 != null) {
                try {
                    statement6.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement5 != null) {
                try {
                    statement5.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement4 != null) {
                try {
                    statement4.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (set2 != null) {
                try {
                    set2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement3 != null) {
                try {
                    statement3.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (set1 != null) {
                try {
                    set1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cancelar_citas() {
        Boolean confirmacion = false;
        System.out.print("Ingresa el id de tu cita: ");
        int citaid = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingresa su nombre(s) (sin apellidos): ");
        String nombre = scanner.nextLine();
        String sql = "SELECT citas.citaid, clientes.nombre FROM citas INNER JOIN clientes ON citas.clienteid = clientes.clienteid WHERE citaid = ?";
        String sql2 = "DELETE FROM citas WHERE citaid = ?";
        
        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet rs1 = null;
        PreparedStatement statement2 = null;
        try{
            conn = ConexionDB.getConexion();
            statement1 = conn.prepareStatement(sql);
            statement1.setInt(1, citaid);
            rs1 = statement1.executeQuery();
            rs1.next();
            int citaid_a = rs1.getInt("citaid");
            String nombre_a = rs1.getString("nombre");
            if(citaid_a == citaid && nombre_a.equals(nombre)) {
                confirmacion = true;       
            } else {
                System.out.println("No hay relacion entre el numero de cita y tu nombre, por lo que no es posible borrar el dato");
            }
            // statement1.close();
            // rs1.close();
            if(confirmacion) {
                statement2 = conn.prepareStatement(sql2);
                statement2.setInt(1, citaid);
                int filasEliminada = statement2.executeUpdate();
                if(filasEliminada>0) {
                    System.out.println("La cita se ha eliminado con exito");
                }
            }
            // conn.close();
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void regresar() {
        System.out.print("\033[H\033[2J");
        Menu menu = new Menu(scanner);
        menu.desplegarMenu(1);
    }
}

class Opciones_Admin {
    private Scanner scanner;

    public Opciones_Admin(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar_admin() {
        String sql1 = "SELECT clienteid, nombre, paterno, materno FROM clientes";
        String sql2 = "SELECT clientes.*, ubicacion.* FROM clientes INNER JOIN ubicacion ON clientes.clienteid = ubicacion.clienteid WHERE clientes.clienteid = ?";
        System.out.println("Clientes en la base de datos\n");
        
        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet rs1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs2 = null;
        try {
            conn = ConexionDB.getConexion();
            statement1 = conn.prepareStatement(sql1);
            rs1 = statement1.executeQuery();
            while(rs1.next()) {
                System.out.println(rs1.getInt("clienteid") + " " + rs1.getString("nombre") + " " + rs1.getString("paterno") + " " + rs1.getString("materno"));
            }
            // statement1.close();
            // rs1.close();
            System.out.print("\nIngresa el id del usuario a mostrar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            statement2 = conn.prepareStatement(sql2);
            statement2.setInt(1, opcion);
            rs2 = statement2.executeQuery();
            rs2.next();
            System.out.println("\nNombre: \t" + rs2.getString("nombre"));
            System.out.println("Apellido Paterno: \t" + rs2.getString("paterno"));
            System.out.println("Apellido Materno: \t" + rs2.getString("materno"));
            System.out.println("Correo: \t" + rs2.getString("correo"));
            System.out.println("Celular: \t" + rs2.getString("celular"));
            System.out.println("Calle: \t" + rs2.getString("calle"));
            System.out.println("Numero: \t" + rs2.getString("numero"));
            System.out.println("Codigo Postal: \t" + rs2.getString("codigopostal"));
            System.out.println("Municipio: \t" + rs2.getString("municipio"));
            System.out.println("Estado: \t" + rs2.getString("estado"));
            // rs2.close();
            // statement2.close();
            // conn.close();
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\nPresiona Enter para continuar. . . ");
        scanner.nextLine();

    }

    public void modificar_admin() {
        System.out.print("\033[H\033[2J");
        Menu menu = new Menu(scanner);
        menu.desplegarMenu(4);
    }

    public void checar_admin() {
        String sql = "SELECT citas.citaid, clientes.nombre, citas.horario, citas.servicio FROM citas JOIN clientes ON citas.citaid = clientes.clienteid";
        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet rs1 = null;
        try {
            conn = ConexionDB.getConexion();
            statement1 = conn.prepareStatement(sql);
            rs1 = statement1.executeQuery();
            while(rs1.next()) {
                System.out.println(rs1.getInt("citaid") + "   " + rs1.getString("nombre") + "\t " + rs1.getTimestamp("horario") + "\t " + rs1.getString("servicio"));
            }
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\nPresiona Enter para continuar. . . ");
        scanner.nextLine();
    }

    public void regresar() {
        System.out.println("\033[H\033[2J");
        Menu menu = new Menu(scanner);
        menu.desplegarMenu(1);
    }
}

class Opciones_Modificar {
    private Scanner scanner;

    public Opciones_Modificar(Scanner scanner) {
        this.scanner = scanner;
    }

    public void datos_modificar() {
        String[] campos = {"nombre","paterno","materno","correo","celular"};
        System.out.println("Que campo quieres modificar?");
        int i = 1;
        for(String campo : campos) {
            System.out.println("["+i+"]" + " " + campo);
            i++;
        }
        System.out.print("R= ");
        int opc = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nCon que sera reemplazado: ");
        String cambio = scanner.nextLine();
        String sql1 = "SELECT clienteid, nombre, paterno, materno FROM clientes";
        String parte1 = "UPDATE clientes SET ";
        String parte2 = " = ? WHERE clienteid = ?";
        String sql2 = parte1 + campos[opc-1] + parte2;
        System.out.println("\nClientes en la base de datos\n");

        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet rs1 = null;
        PreparedStatement statement2 = null;
        try {
            conn = ConexionDB.getConexion();
            statement1 = conn.prepareStatement(sql1);
            rs1 = statement1.executeQuery();
            while(rs1.next()) {
                System.out.println(rs1.getInt("clienteid") + " " + rs1.getString("nombre") + " " + rs1.getString("paterno") + " " + rs1.getString("materno"));
            }
            //statement1.close();
            //rs1.close();
            System.out.print("\nIngresa el id del usuario a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, cambio);
            statement2.setInt(2, id);

            int filasAfectadas = statement2.executeUpdate();
            if(filasAfectadas>0) {
                System.out.println("El campo se ha cambiado con exito");
            }
            //statement2.close();
            //conn.close();
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\nPresiona Enter para continuar. . . ");
        scanner.nextLine();
    }

    public void ubicacion_modificar() {
        String[] campos = {"calle","numero","codigopostal","municipio","estado"};
        System.out.println("Que campo quieres modificar?");
        int i = 1;
        for(String campo : campos) {
            System.out.println("["+i+"]" + " " + campo);
            i++;
        }
        System.out.print("R= ");
        int opc = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nCon que sera reemplazado: ");
        String cambio = scanner.nextLine();
        String sql1 = "SELECT clienteid, nombre, paterno, materno FROM clientes";
        String parte1 = "UPDATE ubicacion SET ";
        String parte2 = " = ? WHERE clienteid = ?";
        String sql2 = parte1 + campos[opc-1] + parte2;
        System.out.println("\nClientes en la base de datos\n");

        Connection conn = null;
        PreparedStatement statement1 = null;
        ResultSet rs1 = null;
        PreparedStatement statement2 = null;
        try {
            conn = ConexionDB.getConexion();
            statement1 = conn.prepareStatement(sql1);
            rs1 = statement1.executeQuery();
            while(rs1.next()) {
                System.out.println(rs1.getInt("clienteid") + " " + rs1.getString("nombre") + " " + rs1.getString("paterno") + " " + rs1.getString("materno"));
            }
            //statement1.close();
            //rs1.close();
            System.out.print("\nIngresa el id del usuario a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, cambio);
            statement2.setInt(2, id);

            int filasAfectadas = statement2.executeUpdate();
            if(filasAfectadas>0) {
                System.out.println("El campo se ha cambiado con exito");
            }
            //statement2.close();
            //conn.close();
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\nPresiona Enter para continuar. . . ");
        scanner.nextLine();
    }

    public void regresar() {
        System.out.print("\033[H\033[2J");
        Menu menu = new Menu(scanner);
        menu.desplegarMenu(3);
    }
}