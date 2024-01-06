/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jsbal
 */
public class Censo {
    private String id_Censo;
    private String motivo;
    private String fecha;
    private String estadoEmocional;
    

    public Censo() {
    }

    public Censo(String id_Censo, String motivo, String fecha, String estadoEmocional) {
        this.id_Censo = id_Censo;
        this.motivo = motivo;
        this.fecha = fecha;
        this.estadoEmocional = estadoEmocional;
    }

    public String getId_Censo() {
        return id_Censo;
    }

    public void setId_Censo(String id_Censo) {
        this.id_Censo = id_Censo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstadoEmocional() {
        return estadoEmocional;
    }

    public void setEstadoEmocional(String estadoEmocional) {
        this.estadoEmocional = estadoEmocional;
    }

   
    
}
