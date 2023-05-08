import java.sql.Timestamp;

public class Citas {
    private Timestamp horario;
    private String servicio;

    public Citas(Timestamp horario, String servicio) {
        this.setHorario(horario);
        this.setServicio(servicio);
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Timestamp getHorario() {
        return this.horario;
    }
    public String getServicio() {
        return this.servicio;
    }
}
