// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
//import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import SpLib.util.bool.filters.StableBoolean;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import SpLib.hardware.motor_controller.ExtendedTalonFXConfiguration;
//import SpLib.hardware.motor_controller.TalonFXFactory;
//import SpLib.util.conversions.EncoderConversions;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public static ShooterSubsystem m_instace;
  private TalonFX m_FlyWheel;
  private TalonFX m_FlywheelSlave;
  private TalonFX m_Hood;
  private final VictorSPX m_conveyor;
  public static Boolean readyToShoot= false;  
  Timer time = new Timer();
  private StableBoolean m_stableBoolean; 
  

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    
    m_FlyWheel = new TalonFX(Constants.ShooterConstants.FlywheelMasterId);
    m_FlywheelSlave = new TalonFX(Constants.ShooterConstants.FlywheelSlaveId);
    m_Hood = new TalonFX(Constants.ShooterConstants.HoodId);

    m_Hood.setNeutralMode(NeutralMode.Coast);
    m_FlyWheel.setNeutralMode(NeutralMode.Coast);
    m_FlywheelSlave.setNeutralMode(NeutralMode.Coast);
    
    TalonFX m_Hood = new TalonFX(62);

    m_conveyor = new VictorSPX(53);
    m_conveyor.setInverted(true);

    m_FlywheelSlave.follow(m_FlyWheel);

    m_FlyWheel.config_kP(0, Constants.ShooterConstants.shooter_pid_kp);
    m_FlyWheel.config_kI(0, Constants.ShooterConstants.shoter_pid_ki);
    m_FlyWheel.config_kD(0, Constants.ShooterConstants.shoter_pid_kd);
    m_FlyWheel.config_kF(0, Constants.ShooterConstants.shoter_pid_kf);
    m_Hood.config_kP(0, Constants.ShooterConstants.hood_pid_kp);
    m_Hood.config_kI(0, Constants.ShooterConstants.hood_pid_ki);
    m_Hood.config_kD(0, Constants.ShooterConstants.hood_pid_kd);
    m_Hood.config_kF(0, Constants.ShooterConstants.hood_pid_kf);

    m_stableBoolean = new StableBoolean(Constants.ShooterConstants.stableBoolTimeThreshold);    

  }

   @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  

  public double GetFlywheelRPM() {
    return SpLib.util.conversions.EncoderConversions.ticksPer100msToRPM(m_FlyWheel.getSelectedSensorVelocity(), Constants.ShooterConstants.k_FlywheelGearRatio, 2048); 
   }

  public double FlywheelTatgetRPM(){
    return m_FlyWheel.getClosedLoopTarget();
    
  }

  public void SetFlywheelVelocity(double velocity){
    m_FlyWheel.set(ControlMode.Velocity, velocity);
    //m_FlyWheel.set(TalonFXControlMode.Velocity, velocity);
  }



  public void SetHoodPresetOutput(double speed){
    m_Hood.set(ControlMode.PercentOutput, speed);
    System.out.println("setting hood precent output" + speed);
  }

  public double GetHoodCurrent(){
    return m_Hood.getBusVoltage();
  }

  public double GetFlywheelCurrent(){
    return m_FlyWheel.getBusVoltage();
  }

  public void ResetHood(){
    m_Hood.setSelectedSensorPosition(0);
  }

  public void SetHoodAngle(double angle){
    m_Hood.set(ControlMode.MotionMagic, angle);
    System.out.println("setting Hood angle: " + angle);
  }

  public double GetHoodAngle() {
    return m_Hood.getSelectedSensorPosition();
  }

  public double GetDistanceFromTarget(){ 
    return SmartDashboard.getNumber(getName(), 0); //TODO: add a normal way to enter a distance
  }

  public void Shoot(){
    time.reset();
    time.start();
    if (!time.hasElapsed(0.5)){
      m_conveyor.set(VictorSPXControlMode.PercentOutput, 0.3);
      System.out.println("shoot!");
    }else {
      m_conveyor.set(VictorSPXControlMode.PercentOutput, 0.0);
    }
    readyToShoot = false;

  }

  public boolean isFlyWheelAtTarget(double target){
    boolean isAtTarget = m_stableBoolean.get((GetFlywheelRPM() < target + Constants.ShooterConstants.AllowedRPMError) && (GetFlywheelRPM() > target - Constants.ShooterConstants.AllowedRPMError)); //TODO: check Target is same units as rpm
    return isAtTarget;

  }
  

  public static ShooterSubsystem GetInstance(){
    if (m_instace == null) {
      m_instace = new ShooterSubsystem();
    }
    return m_instace;
  }
}
