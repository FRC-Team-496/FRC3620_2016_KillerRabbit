package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RotateRobotToImageCommand extends Command implements PIDSource, PIDOutput {

	double kP = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("VisionP Value", 5);
	double kI = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("VisionI Value", 5);
	double kD = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("VisionD Value", 5);
	
	double sideStick;
	
	PIDController pidRotateRobotToImage = new PIDController(kP, kI, kD, 0.0, this, this);
	
    public RotateRobotToImageCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	
    	pidRotateRobotToImage.setInputRange(0.0f, 1000.0f);
    	pidRotateRobotToImage.setOutputRange(-1, 1);
    	pidRotateRobotToImage.setContinuous(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pidRotateRobotToImage.reset();
    	pidRotateRobotToImage.setAbsoluteTolerance(20.0);
    	pidRotateRobotToImage.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Vision P", pidRotateRobotToImage.getP());
    	SmartDashboard.putNumber("Vision I", pidRotateRobotToImage.getI());
    	SmartDashboard.putNumber("Vision D", pidRotateRobotToImage.getD());
    	SmartDashboard.putNumber("PID Vision Sidestick", sideStick);
    	if(Robot.driveSubsystem.isBlobThere() == false){
    		Robot.driveSubsystem.stopMotors();
    	}
    	else {
    		Robot.driveSubsystem.setDriveForward(0, sideStick);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    	/*if(Math.abs(Robot.driveSubsystem.getTargetCenter())<20){
    		return true;
    	}
    	else{
    		return false;
    	}
    	*/
    }

    // Called once after isFinished returns true
    protected void end() {
    	pidRotateRobotToImage.disable();
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    public void pidWrite(double output){
    	sideStick=output;
    }
    
    public void setPIDSourceType(PIDSourceType pidSource){
    	
    }
    
    public PIDSourceType getPIDSourceType(){
    	return PIDSourceType.kDisplacement;
    }
    
    public double pidGet(){
    	return Robot.driveSubsystem.getTargetCenter();
    }
}
