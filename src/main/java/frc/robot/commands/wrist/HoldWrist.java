/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Wrist;

public class HoldWrist extends Command {

    private Wrist wrist;

    private double targetPosition;

	public HoldWrist() {
        wrist = Robot.wrist;

		requires(wrist);
	}

	@Override
	protected void initialize() {
		targetPosition = wrist.currentAngle();
		Robot.debugTable.getEntry("wrist target").setNumber(targetPosition);
	}

	@Override
	protected void execute() {
		wrist.moveTowards(targetPosition);
		//System.out.println("hold command" + targetPosition);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		wrist.stopMotors();
	}
}


