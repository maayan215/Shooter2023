// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import SpLib.util.bool.filters.StableBoolean;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShooterCommand extends CommandBase {
  private StableBoolean m_stableBoolean; 
  private ShooterSubsystem m_shooter; 
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
    SmartDashboard.putBoolean("shot", false);

  } 

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double DistanceX = m_shooter.GetDistanceFromTarget();
    
    double V0x = DistanceX / 0.55;
    double V0 = Math.toDegrees(Math.atan(6.56 / V0x));
    double R = 0.1016;
    double r = 0.073025;
    double rpm = 60 * (V0 / (Math.PI * (R + 2 * r)));

    //m_shooter.SetFlyWheelRPM(rpm, ((r+R)/2));

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.Shoot();
    ShooterSubsystem.readyToShoot = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //return m_stableBoolean.get((m_shooter.GetFlywheelRPM() < m_shooter.FlywheelTatgetRPM() + Constants.ShooterConstants.AllowedRPMError)); //TODO: check Target is same units as rpm
  }
}