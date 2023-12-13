// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ShooterSubsystem;

//import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
//import SpLib.hardware.motor_controller.TalonFXConstants;

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

    public static final double PresetXVelocity = 2090;
    public static final double PresetXAngle = 26;
    public static final double PresetYVelocity = 3500;
    public static final double PresetYAngle = 30;
    public static final double PresetAAngle = 35;
    public static final double PresetAVelocity = 2000;
    public static final double PresetBAngle = 45;
    public static final double PresetBVelocity = 4500;
    
    
    public static final double HoodCurrentLimit = 9;
    public static final double FlywheelCurrentLimit = 0;

    //motor id
    public static final int FlywheelMasterId = 60;
    public static final int FlywheelSlaveId = 61;
    public static final int HoodId = 62;
    public static final int ConvyorId = 53;
    public static final double AllowedRPMError = 50;
    public static final double encoderResolution = 2048;
    public static final double hoodGearRatio = 48;

    public static Double k_FlywheelGearRatio = 1.0;
    public static double stableBoolTimeThreshold = 1;
  
    //========================
    // hood angel pid ===========
    public static double hood_pid_kp = 0.5;
    public static double hood_pid_ki= 0.0; 
    public static double hood_pid_kd=5.0;
    public static double hood_pid_kf = 0;

  }
}
