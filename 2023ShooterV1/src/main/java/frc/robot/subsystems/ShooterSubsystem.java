// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import SpLib.util.bool.filters.StableBoolean;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
    //conveyor settings
    m_conveyor = new VictorSPX(53);
    m_conveyor.setInverted(true);
    
    //FlyWheel settings
    m_FlyWheel = new TalonFX(Constants.ShooterConstants.FlywheelMasterId);
    m_FlywheelSlave = new TalonFX(Constants.ShooterConstants.FlywheelSlaveId);
    m_Hood = new TalonFX(Constants.ShooterConstants.HoodId);
    
    motorConfig();
    pidConfig();
    
    SmartDashboard.putNumber("distance", 5.2);

    m_stableBoolean = new StableBoolean(Constants.ShooterConstants.stableBoolTimeThreshold);   
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("FlyWheel RPM", GetFlywheelRPM());
    SmartDashboard.putNumber("Hood angle", GetHoodAngle());
    SmartDashboard.putNumber("flywheel ticls/100ms", m_FlyWheel.getSelectedSensorVelocity());
    SmartDashboard.putNumber("ClosedLoopError", m_FlywheelSlave.getClosedLoopError());
    SmartDashboard.putBoolean("Shoot", readyToShoot);
    SmartDashboard.putNumber("autoAngle", CalculateAngleFromDistance());
    SmartDashboard.putNumber("autoRPM", CalculateRPMFromDistance());
  }

  public double GetFlywheelRPM() { 
    return SpLib.util.conversions.EncoderConversions.ticksPer100msToRPM(m_FlyWheel.getSelectedSensorVelocity(), Constants.ShooterConstants.k_FlywheelGearRatio, Constants.ShooterConstants.encoderResolution);
  }

  public double GetFlywheelTatgetRPM(){
    return m_FlyWheel.getClosedLoopTarget(); 
  }

  public void SetFlywheelRPM(double RPM){
    m_FlyWheel.set(ControlMode.Velocity, SpLib.util.conversions.EncoderConversions.RPMToTicksPer100ms(RPM, Constants.ShooterConstants.k_hoodGearRatio, Constants.ShooterConstants.encoderResolution));
  }

  public void SetFlyWheelPrecentOutput(double speed){
    m_FlyWheel.set(ControlMode.PercentOutput, speed);
  }  

  public void SetHoodPresetOutput(double speed){
    m_Hood.set(ControlMode.PercentOutput, speed);
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
    m_Hood.set(ControlMode.Position, SpLib.util.conversions.EncoderConversions.degreesToTicks(angle, Constants.ShooterConstants.k_FlywheelGearRatio, Constants.ShooterConstants.encoderResolution));
  }

  public double GetHoodAngle() {
    return SpLib.util.conversions.EncoderConversions.ticksToDegrees(m_Hood.getSelectedSensorPosition(), Constants.ShooterConstants.k_FlywheelGearRatio, Constants.ShooterConstants.encoderResolution);
  }

  public double GetDistanceFromTarget(){ 
    return SmartDashboard.getNumber("distance", 0); //TODO: add a normal way to enter a distance
  }

  public double CalculateRPMFromDistance(){
    double DistanceX = GetDistanceFromTarget();
    double V0x = DistanceX / 0.55;
    double V0 = Math.toDegrees(Math.atan(6.56 / V0x));
    double R = 0.1016;
    double r = 0.073025;
    double rpm = 60 * ((V0 / (Math.PI * (R + 2 * r))) * 1.2);
    return rpm;
  }

  public double CalculateAngleFromDistance(){
    double DistanceX = GetDistanceFromTarget();
    double angle = Math.tanh((2.7/DistanceX) + 14);
    return angle;
  }

  public void SetConveyorSpeed(double speed){
    m_conveyor.set(VictorSPXControlMode.PercentOutput, speed);
  }

  /**
   * 
   * @param target the target the system is approching
   * @return true when flywheel is at target
   */
  public boolean isFlyWheelAtTarget(double target){
    return m_stableBoolean.get((GetFlywheelRPM() < target + Constants.ShooterConstants.AllowedRPMError) && (GetFlywheelRPM() > target - Constants.ShooterConstants.AllowedRPMError));
  }

  //==========================================================================================================
  private void motorConfig(){
    m_Hood.setNeutralMode(NeutralMode.Coast);
    m_Hood.setInverted(TalonFXInvertType.Clockwise);
    m_Hood.enableVoltageCompensation(true);
    m_Hood.configVoltageCompSaturation(12);
    m_Hood.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
    m_Hood.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 15, 0, 0));
    m_Hood.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);
    m_Hood.configForwardSoftLimitThreshold(SpLib.util.conversions.EncoderConversions.ticksToDegrees(31, Constants.ShooterConstants.k_hoodGearRatio, Constants.ShooterConstants.encoderResolution));
    m_Hood.configReverseSoftLimitThreshold(SpLib.util.conversions.EncoderConversions.ticksToDegrees(-2, Constants.ShooterConstants.k_hoodGearRatio, Constants.ShooterConstants.encoderResolution));
    m_Hood.overrideSoftLimitsEnable(false);
    //==========================================================================================================
    // shooter config's=========================
    m_FlyWheel.setInverted(true);
    m_FlyWheel.enableVoltageCompensation(true);
    m_FlyWheel.configVoltageCompSaturation(12, 100);
    m_FlyWheel.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_2Ms, 1);
    m_FlyWheel.setStatusFramePeriod(StatusFrame.Status_1_General, 250, 0);
    m_FlyWheel.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);
    m_FlyWheel.configOpenloopRamp(0.25);
    m_FlyWheel.configPeakOutputForward(1);
    m_FlyWheel.configPeakOutputReverse(0);
    m_FlywheelSlave.set(ControlMode.Follower, m_FlyWheel.getDeviceID());
    m_FlywheelSlave.setInverted(TalonFXInvertType.OpposeMaster);
    m_FlywheelSlave.setStatusFramePeriod(StatusFrame.Status_1_General, 250, 0);
  //==========================================================================================================
}

public void pidConfig(){
  m_Hood.config_kP(0, Constants.ShooterConstants.hood_pid_kp);
    m_Hood.config_kI(0, Constants.ShooterConstants.hood_pid_ki);
    m_Hood.config_kD(0, Constants.ShooterConstants.hood_pid_kd);
    m_Hood.config_kF(0, Constants.ShooterConstants.hood_pid_kf);

    m_FlyWheel.config_kP(0, 0.2);
    m_FlyWheel.config_kI(0, 0.0004);
    m_FlyWheel.config_kD(0, 0.0);
}

  public static ShooterSubsystem GetInstance(){
    if (m_instace == null) {
      m_instace = new ShooterSubsystem();
    }
    return m_instace;
  }
}