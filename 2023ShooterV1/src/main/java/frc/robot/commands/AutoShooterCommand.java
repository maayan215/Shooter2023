// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import SpLib.util.bool.filters.StableBoolean;
import SpLib.util.conversions.UnitsConversions;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShooterCommand extends CommandBase {
  private StableBoolean m_stableBoolean; 
  private ShooterSubsystem m_shooter; 
  private double targetRPM;
  /** Creates a new ShooterCommand. */
  public AutoShooterCommand(ShooterSubsystem shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    addRequirements(m_shooter);
    m_stableBoolean = new StableBoolean(Constants.ShooterConstants.stableBoolTimeThreshold);    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stableBoolean.reset();
  } 

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double rpm = m_shooter.CalculateRPMFromDistance();
    double angle = m_shooter.CalculateAngleFromDistance();
    
    // m_shooter.SetFlywheelRPM(rpm);
    //m_shooter.SetHoodAngle(angle);  TODO: add back motors

    SmartDashboard.putNumber("targetRPM", rpm);
    SmartDashboard.putNumber("angleRPM", angle);

    // if (m_shooter.isFlyWheelAtTarget(rpm)){
    //   m_shooter.SetConveyorSpeed(0.3);
    //   // m_shooter.readyToShoot = true;
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_shooter.SetFlywheelRPM(0);
    SmartDashboard.putBoolean("AutoShoot", false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}