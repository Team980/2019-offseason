package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Like DifferentialDrive, but for cool people
 */
public class PIDrive {
    private static final double P = 0.01;
    private static final double I = 0;
    private static final double D = 0;

    private static final double MAX_VELOCITY = 17; 
        
    private PIDController leftController;
    private PIDController rightController;
        

    public PIDrive(Encoder leftEncoder, Encoder rightEncoder, SpeedController leftMotor, SpeedController rightMotor) {
        leftEncoder.setPIDSourceType(PIDSourceType.kRate);
        rightEncoder.setPIDSourceType(PIDSourceType.kRate);
        
        leftController = new PIDController(P, I, D, leftEncoder, leftMotor);
        rightController = new PIDController(P, I, D, rightEncoder, rightMotor);

        leftController.enable();
        rightController.enable();
    }

    /**
     * left & right are from -1 to 1
     */
    public void tankDrive(double left, double right) {
        left = limit(left);
        right = limit(right);

        leftController.setSetpoint(left * MAX_VELOCITY);
        rightController.setSetpoint(right * MAX_VELOCITY); 

		Robot.debugTable.getEntry("left setpoint").setNumber(left * MAX_VELOCITY);
		Robot.debugTable.getEntry("right setpoint").setNumber(right * MAX_VELOCITY);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        xSpeed = Math.copySign(xSpeed*xSpeed, xSpeed); // squaring inputs
        zRotation = Math.copySign(zRotation*zRotation, zRotation);
    
        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
        if(xSpeed * zRotation >= 0) { // signs are the same
            tankDrive(maxInput, xSpeed-zRotation);
        } else {
            tankDrive(xSpeed+zRotation, maxInput);
        }
    }

    public void stopMotor() {
        leftController.setSetpoint(0);
        rightController.setSetpoint(0);
        // should we disable the pid controllers?
    }

    public static double limit(double x) {
        return Math.abs(x) > 1 ? Math.signum(x) : x;

    }
}