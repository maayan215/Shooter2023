package SpLib.hardware.motor_controller;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;

public class TalonFXConstants {
    public int id;
    public TalonFXInvertType invertType;
    
    public TalonFXConstants(int id, TalonFXInvertType invertType) {
        this.id = id;
        this.invertType = invertType;
    }
}