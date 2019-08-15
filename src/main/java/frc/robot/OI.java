/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.configuration.BallFloorPickupConfiguration;
import frc.robot.commands.configuration.BattleConfiguration;
import frc.robot.commands.configuration.CargoScoreConfiguration;
import frc.robot.commands.end_effector.SpitOut;
import frc.robot.commands.end_effector.SuckIn;
import frc.robot.commands.lift.SetLiftPosition;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private Joystick throttle;
	private Joystick wheel;
	XboxController xBox;

	private static final double MOVE_DEADBAND = 0.1;
	private static final double TURN_DEADBAND = 0.1;
	private static final double LIFT_DEADBAND = 0.1;
	private static final double WRIST_DEADBAND = 0.1;

	OI () {
		throttle = new Joystick(0);
		wheel = new Joystick(1);
		xBox = new XboxController(2);

		// configurations
		JoystickButton aButton = new JoystickButton(xBox, 1);
		aButton.whenPressed(new CargoScoreConfiguration());

		JoystickButton bButton = new JoystickButton(xBox, 2);
		bButton.whenPressed(new BattleConfiguration());

		// end effector controls
		JoystickButton leftBumper = new JoystickButton(xBox, 5);
		leftBumper.whileHeld(new SuckIn()); // note: while pressed

		JoystickButton rightBumper = new JoystickButton(xBox, 6);
		rightBumper.whileHeld(new SpitOut()); // note: while pressed
	}

	public double getMove() {
		return applyDeadband(-throttle.getY(), MOVE_DEADBAND);
	}

	public double getTurn() {
		return applyDeadband(wheel.getX(), TURN_DEADBAND);
	}

	public double getLiftJoystickValue() {
		double value = -xBox.getY(Hand.kLeft);
		return applyDeadband(value, LIFT_DEADBAND);
	}

	public double getWristJoystickValue() {
		double value = -xBox.getY(Hand.kRight);
		return applyDeadband(value, WRIST_DEADBAND);
	}

	/**
     * Returns 0.0 if the given value is within the specified range around zero. The remaining range
     * between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     */
    private static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    } 
}