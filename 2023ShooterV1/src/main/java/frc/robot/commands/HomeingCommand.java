// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.invoke.ConstantCallSite;

import SpLib.util.bool.filters.StableBoolean;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class HomeingCommand extends CommandBase {
  private ShooterSubsystem m_shooter;
  Timer timer = new Timer();
  private StableBoolean m_stableBoolean;
  /** Creates a new HomeingCommand. */
  public HomeingCommand(ShooterSubsystem shooter,StableBoolean stableboolean) {
    m_shooter = shooter;
    m_stableBoolean = new StableBoolean(Constants.ShooterConstants.stableBoolTimeThreshold);

    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.SetHoodPresetOutput(-0.3);
    m_stableBoolean.reset();
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.SetHoodPresetOutput(0);
    m_shooter.ResetHood();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_stableBoolean.get(m_shooter.GetHoodCurrent() > Constants.ShooterConstants.HoodCurrentLimit || timer.hasElapsed(1));
  }
}



