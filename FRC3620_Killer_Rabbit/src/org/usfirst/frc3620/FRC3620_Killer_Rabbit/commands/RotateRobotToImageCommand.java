package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateRobotToImageCommand extends Command {

    public RotateRobotToImageCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.rotateRobotToImage();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
