import java.util.Scanner;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class Funciones {
    private Scanner scanner;

    public Funciones(Scanner scanner) {
        this.scanner = scanner;
    }

    public Cliente datosCliente() {
        System.out.print("\033[H\033[2J");
        System.out.println("Primero se recolectaran sus datos personales con el objetivo de brindar un mejor servicio\n");
        System.out.println("Datos personales\n");
        System.out.print("Ingresa solo tu(s) nombre(s): ");
        String nombre = scanner.nextLine();
        System.out.print("Ingresa tu apellido paterno: ");
        String paterno = scanner.nextLine();
        System.out.print("Ingresa tu apellido materno (En caso de no tener, solo poner un X): ");
        String materno = scanner.nextLine();
        System.out.print("Ingresa tu correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingresa tu celular: ");
        String celular = scanner.nextLine();
        System.out.println("\nDatos de ubicacion\n");
        System.out.print("Ingresa tu calle: ");
        String calle = scanner.nextLine();
        System.out.print("Ingresa tu numero exterior: ");
        String numero = scanner.nextLine();
        System.out.print("Ingresa tu codigo postal: ");
        String codigoPostal = scanner.nextLine();
        System.out.print("Ingresa tu municipio: ");
        String municipio = scanner.nextLine();
        System.out.print("Ingresa tu estado: ");
        String estado = scanner.nextLine();

        Ubicacion ubicacion = new Ubicacion(calle, numero, codigoPostal, municipio, estado);
        Cliente cliente = new Cliente(nombre, paterno, materno, ubicacion, correo, celular);

        return cliente;
    }

    public Citas datosCita() {
        System.out.println("\nDatos sobre la cita\n");

        System.out.print("Ingresa en que mes quisieras la cita: ");
        String mes = scanner.nextLine();
        while(Integer.parseInt(mes) > 12 || Integer.parseInt(mes) < 1) {
            System.out.print("Solo hay 12 meses :/");
            System.out.print("Ingresa en que mes quisieras la cita: ");
            mes = scanner.nextLine();
        }

        System.out.print("Ingresa en que dia quisieras la cita: ");
        String dia = scanner.nextLine();
        while(Integer.parseInt(dia) > 30 || Integer.parseInt(dia) < 1) {
            System.out.print("Supongamos que todos los meses tienen 30 dias");
            System.out.print("Ingresa en que dia quisieras la cita: ");
            dia = scanner.nextLine();
        }
        
        System.out.print("Ingresa en que hora quisieras la cita usa, el formato (HH:mm:ss): ");
        String hora = scanner.nextLine();
        String patron = "^([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(hora);
        while(!matcher.matches()) {
            System.out.print("El formato es HH:mm:ss");
            System.out.print("Ingresa en que hora quisieras la cita: ");
            hora = scanner.nextLine();
            matcher = pattern.matcher(hora);
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String anio = String.valueOf(year);
        
        String strFecha = anio + "-" + mes + "-" + dia + " " + hora;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha;
        Timestamp horario = null;
        try {
            fecha = sdf.parse(strFecha);
            horario = new Timestamp(fecha.getTime());
        } catch (Exception ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        }

        System.out.print("Explica brevemente el motivo de la cita: ");
        String servicio = scanner.nextLine();

        Citas cita = new Citas(horario, servicio);

        return cita;
    }

    public Autos datosAuto() {
        System.out.print("\033[H\033[2J");
        System.out.println("Una vez ingresados los datos personals, puede ahora escoger su auto\n");
        Integer modelo = 0;
        Integer version = 0;
        Integer uso = 0;
        Integer color = 0;
        String color_a = " ";
        String verison_a = " ";
        String uso_a = " ";
        ArrayList<String> modelos = new ArrayList<String>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try{
            conn = ConexionDB.getConexion();
            String sql = "SELECT * FROM modelos";
            statement = conn.prepareStatement(sql);
            set = statement.executeQuery();
            while(set.next()) {
                modelos.add(set.getString("nombre"));
            }
            // statement.close();
            // set.close();
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        } finally {
            if (set != null) {
                try {
                    set.close();
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

        while(true) {
            try {
                int i=1;
                System.out.println("Escoja un modelo:");
                for(String model : modelos) {
                    System.out.println("[" + i + "] " + model);
                    i++;
                }
                System.out.print("R= ");
                modelo = scanner.nextInt();
                scanner.nextLine();
                if(modelo<1 || modelo>modelos.size()) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + modelos.size() + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + modelos.size() + "\n");
            }
        }
        
        while(true) {
            try {
                System.out.print("Que transmision prefiere:\n[1] Automatica\n[2] Manual\nR=");
                version = scanner.nextInt();
                scanner.nextLine();
                if(version<1 || version>2) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar 1 o 2 " + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar 1 o 2 " + "\n");
            }
        }
        while(true) {
            try {
                System.out.print("Que uso prefiere:\n[1] Auto usado\n[2] Auto nuevo\nR=");
                uso = scanner.nextInt();
                scanner.nextLine();
                if(uso<1 || uso>2) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar 1 o 2 " + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar 1 o 2 " + "\n");
            }
        }
        while(true) {
            try {
                System.out.print("Que color prefiere:\n");
                for(int i = 0; i < Colores.values().length; i++) {
                    System.out.println("[" + (i+1) + "]" + " " + Colores.values()[i]);
                }
                System.out.print("R= ");
                color = scanner.nextInt();
                scanner.nextLine();
                if(color<1 || uso>Colores.values().length) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar de 1 a " + Colores.values().length + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar de 1 a " + Colores.values().length + "\n");
            }
        }
        if(version == 2) {
            verison_a = "ESTANDAR";
        } else {
            verison_a = "AUTOMATICO";
        }

        if(uso == 2) {
            uso_a = "NUEVO";
        } else {
            uso_a = "USADO";
        }

        Colores colores = Colores.values()[color-1];
        color_a = colores.name();

        
        Autos auto = new Autos(uso_a, verison_a, modelo, color_a);

        return auto;

    }

    public Cotizacion datosCotizaciones(String enganche) {
        ArrayList<String> financiamientos = new ArrayList<String>();
        financiamientos.add("CREDITO FORD");
        financiamientos.add("FINANCIAMIENTO SELECTIVITI");
        financiamientos.add("LEASING FORD");
        ArrayList<String> fiscales = new ArrayList<String>();
        fiscales.add("PERSONA FISICA");
        fiscales.add("PERSONA MORAL");
        fiscales.add("FISICA EMPRESARIAL");
        ArrayList<String> plazos = new ArrayList<String>();
        plazos.add("24");
        plazos.add("36");
        plazos.add("48");
        ArrayList<String> aseguradoras = new ArrayList<String>();
        aseguradoras.add("QUALITAS");
        aseguradoras.add("GNP");
        aseguradoras.add("CHUBB");
        ArrayList<String> pagos = new ArrayList<String>();
        pagos.add("CONTADO");
        pagos.add("FINANCIADO");

        System.out.print("\033[H\033[2J");
        System.out.println("A continuacion, cotizara su vehiculo.\n");

        Integer plan = 0;
        Integer persona = 0;
        Integer plazo = 0;
        Integer aseguradora = 0;
        Integer pago = 0;
        String strPlan = " ";
        String strPersona = " ";
        String strPlazo = " ";
        String strAseguradora = " ";
        String strPago = " ";

        while(true) {
            try {
                int i=1;
                System.out.println("Escoja un plan de financiamiento:");
                for(String financiamiento : financiamientos) {
                    System.out.println("[" + i + "] " + financiamiento);
                    i++;
                }
                System.out.print("R= ");
                plan = scanner.nextInt();
                scanner.nextLine();
                if(plan<1 || plan>financiamientos.size()) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + financiamientos.size() + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + financiamientos.size() + "\n");
            }
        }

        while(true) {
            try {
                int i=1;
                System.out.println("Escoja la personalidad fiscal:");
                for(String fiscal : fiscales) {
                    System.out.println("[" + i + "] " + fiscal);
                    i++;
                }
                System.out.print("R= ");
                persona = scanner.nextInt();
                scanner.nextLine();
                if(persona<1 || persona>fiscales.size()) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + fiscales.size() + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + fiscales.size() + "\n");
            }
        }

        while(true) {
            try {
                int i=1;
                System.out.println("Escoja un plazo para pagar:");
                for(String plaz : plazos) {
                    System.out.println("[" + i + "] " + plaz);
                    i++;
                }
                System.out.print("R= ");
                plazo = scanner.nextInt();
                scanner.nextLine();
                if(plazo<1 || plazo>plazos.size()) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + plazos.size() + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + plazos.size() + "\n");
            }
        }

        while(true) {
            try {
                int i=1;
                System.out.println("Escoja la aseguradora de su preferencia:");
                for(String ase : aseguradoras) {
                    System.out.println("[" + i + "] " + ase);
                    i++;
                }
                System.out.print("R= ");
                aseguradora = scanner.nextInt();
                scanner.nextLine();
                if(aseguradora<1 || aseguradora>aseguradoras.size()) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + aseguradoras.size() + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + aseguradoras.size() + "\n");
            }
        }

        while(true) {
            try {
                int i=1;
                System.out.println("Escoja el tipo de pago que usara:");
                for(String pag : pagos) {
                    System.out.println("[" + i + "] " + pag);
                    i++;
                }
                System.out.print("R= ");
                pago = scanner.nextInt();
                scanner.nextLine();
                if(pago<1 || pago>pagos.size()) {
                    throw new Exception();
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("\033[H\033[2J");
                scanner.next();
                System.out.println("Debes ingresar un numero entre 1 y " + pagos.size() + "\n");
            } catch(Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.println("Debes ingresar un numero entre 1 y " + pagos.size() + "\n");
            }
        }

        strPlan = financiamientos.get(plan-1);
        strPersona = fiscales.get(persona-1);
        strPlazo = plazos.get(plazo-1);
        strAseguradora = aseguradoras.get(aseguradora-1);
        strPago = pagos.get(pago-1);
        System.out.println(strPlan + " " + strPersona + " " + strPlazo + " " + strAseguradora + " " + strPago);
        Cotizacion cotizacion = new Cotizacion(strPlan, strPersona, enganche, strPlazo, strAseguradora, strPago);
        return cotizacion;
    }
}
