package SpLib.hardware.motor_controller;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class ExtendedTalonFXConfiguration extends TalonFXConfiguration {
    
    public NeutralMode neutralMode = NeutralMode.Brake;
    
    public boolean enableVoltageCompensation = true;

    // See meaning & default value for each status https://docs.ctre-phoenix.com/en/stable/ch18_CommonAPI.html#motor-controllers 
    public int status1FramePeriodMs = -1;

    public int status2FramePeriodMs = -1;

    public int status8FramePeriodMs = -1;

    public int status10FramePeriodMs = -1;

    public int statusCurrentFramePeriodMs = -1;

}