// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class PresetShooterCommand extends CommandBase {
  private ShooterSubsystem m_shooter;
  public double m_RPM;
  public double m_angle;

  /** Creates a new PresetShooterCommand. */
  public PresetShooterCommand(ShooterSubsystem shooter, double RPM) { // TODO: add angle back
    m_shooter = shooter;
    m_RPM = RPM;
    //m_angle = angle;

    addRequirements(m_shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.SetFlywheelRPM(m_RPM);
    // m_shooter.SetHoodAngle(m_angle); TODO: add back angle control

    if (m_shooter.isFlyWheelAtTarget(m_RPM)){
      //m_shooter.readyToShoot = true;
      m_shooter.SetConveyorSpeed(0.3);
    }
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.SetFlywheelRPM(0);
    m_shooter.SetConveyorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
