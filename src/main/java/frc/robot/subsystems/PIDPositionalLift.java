/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.commands.lift.HoldLift;

/**
 * Add your docs here.
 */
public class PIDPositionalLift extends PIDSubsystem {
  private static final double DEADBAND = 0.01;//ft
  private static final double EXCLUSION_MIN = 0.02;//ft
	private static final double EXCLUSION_MAX = 1.8;

  /**
   * Add your docs here.
   */
  public PIDPositionalLift() {
    // Intert a subsystem name and PID values here
    super("PidPositionalLift", 2, 0.01, 0);//adjust the P vaue up to give it more speed and have enough power to get to the target positions
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
    setInputRange(EXCLUSION_MIN , EXCLUSION_MAX);

    //setAbsoluteTolerance(DEADBAND);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new HoldLift());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return Robot.robotMap.liftEncoder.getDistance();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    Robot.robotMap.liftMotor.set(output);
  }

  public void stopMotors() {
    disable();
    Robot.robotMap.liftMotor.set(0);
	}

}
