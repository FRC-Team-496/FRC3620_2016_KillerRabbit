package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutomatedMoveTimed extends Command implements PIDOutput{
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	

	//Last working kP = .05
	static final double kP = .05;
	//Last working kI = .0015
	static final double kI = .0015;	
	//Last working kD = .02
	static final double kD = .02;
	
	static final double kF = .00;
	double sideStick;
	
	double howLongWeWantToMove = 0;
	double howFastToMove = 0;
	
	Timer timer = new Timer();
	
	PIDController pidDriveStraight = new PIDController(kP, kI, kD, kF, Robot.driveSubsystem.getAhrs(), this);
	

    public AutomatedMoveTimed(double howLongInSeconds, double howFast) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	pidDriveStraight.setOutputRange(-1, 1);
   
    	howFastToMove = howFast;
    	howLongWeWantToMove = howLongInSeconds;
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {

    	logger.info("AutomatedMove start");
    	RobotMap.driveSubsystemLeftDriveEncoder.reset();
    	RobotMap.driveSubsystemRightDriveEncoder.reset();
        pidDriveStraight.setSetpoint(Robot.driveSubsystem.getAutomaticHeading());
    	pidDriveStraight.enable();
    	
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("PID Error: " + pidDriveStraight.getError());
    	System.out.println("sideStick value: " + sideStick);
    	
    	SmartDashboard.putNumber("DriveStraight P", pidDriveStraight.getP());
    	SmartDashboard.putNumber("DriveStraight I", pidDriveStraight.getI());
    	SmartDashboard.putNumber("DriveStraight D", pidDriveStraight.getD());
    	
    	SmartDashboard.putNumber("PID DriveStraight Error", pidDriveStraight.getError());
    	SmartDashboard.putNumber("PID DriveStraight Sidestick", sideStick);
    	Robot.driveSubsystem.setDriveForward(-howFastToMove, sideStick);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	return timer.get() > howLongWeWantToMove;
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("AutomatedMove end");
    	pidDriveStraight.disable();
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
      
    public void pidWrite(double output) {
       sideStick = output;
    }

   
}
