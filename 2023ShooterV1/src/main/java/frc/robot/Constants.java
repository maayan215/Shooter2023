// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;

import SpLib.hardware.motor_controller.TalonFXConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class ShooterConstants{

    public static final double PresetXVelocity = 3000;
    public static final double PresetXAngle = 25;
    public static final double PresetYVelocity = 3500;
    public static final double PresetYAngle = 30;
    public static final double PresetAAngle = 35;
    public static final double PresetAVelocity = 4000;
    public static final double PresetBAngle = 45;
   public static final double PresetBVelocity = 4500;
    public static final double FlyWheelKp = 0.5;
    public static final double FlyWheelKi = 0.0;
    public static final double FlyWheelKd = 0.5;
    public static final double HoodKp = 0.2;
    public static final double HoodKi = 0.0;
    public static final double HoodKd = 0.0;

    public static final double HoodCurrentLimit = 0;
    public static final double FlywheelCurrentLimit = 0;

    //motor id
    public static final int FlywheelMasterId = 60;
    public static final int FlywheelSlaveId = 61;
    public static final int HoodId = 62;
    public static final int ConvyorId = 53;

    private static TalonFXInvertType k_ShooterInvertType;
    private static int k_ShooterControllerId;
    public static Double k_FlywheelGearRatio;
    public static double stableBoolTimeThreshold;
    

    
    //master
    public static final int k_flyWheelControllerId = 60;// TODO: update ID
    public static final TalonFXInvertType k_flyWheelInvertType = TalonFXInvertType.Clockwise;
    public static final TalonFXConstants flyWheelConstants = new TalonFXConstants(Constants.ShooterConstants.k_flyWheelControllerId,
    Constants.ShooterConstants.k_flyWheelInvertType);

    //slave
    public static final int k_flyWheelSlaveControllerId = 61;// TODO: update ID
    public static final TalonFXInvertType k_flyWheelSlaveInvertType = TalonFXInvertType.Clockwise;
    public static final TalonFXConstants flyWheelSlaveConstants = new TalonFXConstants(Constants.ShooterConstants.k_flyWheelSlaveControllerId,
    Constants.ShooterConstants.k_flyWheelSlaveInvertType);

    
    // shooter pid def======
    public static double shooter_pid_kp = 0.2; 
    public static double shoter_pid_ki = 0.0;
    public static double shoter_pid_kd = 0.0;
    public static double shoter_pid_kf=0.054;
    //========================
    // hood angel pid ===========
    public static double hood_pid_kp = 0.5;
    public static double hood_pid_ki= 0.0; 
    public static double hood_pid_kd=5.0;
    public static double hood_pid_kf = 0;

  }
}
