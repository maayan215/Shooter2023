// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.ShooterSubsystem;
//import frc.robot.Constants.ShooterConstants;
//import frc.robot.commands.AutoShooterCommand;
import frc.robot.commands.HomeingCommand;
import frc.robot.commands.PresetShooterCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be fds here.
 */
public class RobotContainer {

  ShooterSubsystem m_ShooterSubsystem = ShooterSubsystem.GetInstance();
  
  
  // The robot's subsystems and commands are defined here...
  public static XboxController controller = new XboxController(1);
  // Configure the trigger bindings
  JoystickButton Triangle = new JoystickButton(controller, XboxController.Button.kY.value);
  JoystickButton Circle = new JoystickButton(controller, XboxController.Button.kB.value);
  JoystickButton Square = new JoystickButton(controller, XboxController.Button.kX.value);
  JoystickButton Cross = new JoystickButton(controller, XboxController.Button.kA.value);
  JoystickButton RB = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
  JoystickButton LB = new JoystickButton(controller,XboxController.Button.kLeftBumper.value); 

  




  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    
    LB.onTrue(new HomeingCommand(m_ShooterSubsystem)); 
    Square.onTrue(new PresetShooterCommand(m_ShooterSubsystem, 2000, 15));
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */


  
}
