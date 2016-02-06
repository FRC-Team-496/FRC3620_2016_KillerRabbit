// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMode;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	static final double bottomSetPoint = 0.7;
	static final double topSetPoint = 0;
	static final double cushion = 0.1;
	static final double creepPower = 0.25;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final CANTalon armCANTalon = RobotMap.armSubsystemArmCANTalon;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public ArmSubsystem() {
		super();

		// TODO Auto-generated constructor stub
		// armCANTalon.changeControlMode(TalonControlMode.Position);
		weAreInManualMode = true;
		armCANTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		armCANTalon.enableBrakeMode(true);
		// SIMs need reverseSensor(true), BAGs need false.
		armCANTalon.reverseSensor(true);
		armCANTalon.setPID(0.3, .00001, 0.01);
		armCANTalon.setPosition(topSetPoint);
		// logger.info("encoder position is " + armCANTalon.getEncPosition());
		// armCANTalon.setEncPosition(0);
		// logger.info("now the encoder position is " +
		// armCANTalon.getEncPosition());
		moveManually(0);
	}

	boolean weAreInManualMode = false;

	// Put methods for controlling this subsystem here. Call these from
	// Commands.

	/*
	 * this gets called from Robot.java when we transition from mode to mode
	 * (disabled -> teleop, teleop -> disabled, etc)
	 */
	public void allInit(RobotMode newRobotMode) {
		if (newRobotMode == RobotMode.TELEOP) {
			logger.info("Armsubsystem sees we are going into Teleop, setting vbusmode");
			moveManually(0);
		}
	}

	public void moveArmToTop() {
		moveArmToSetpoint(topSetPoint, 0, 0, 0);
	}

	public void moveArmToBottom() {
		moveArmToSetpoint(bottomSetPoint, 0, 0, 0);
	}

	void moveArmToSetpoint(double position, double p, double i, double d) {
		if (weAreInManualMode) {
			logger.info("flipping into automatic");
			armCANTalon.changeControlMode(TalonControlMode.Position);
			weAreInManualMode = false;
		}
		// TODO set PID from p, i, d
		armCANTalon.setSetpoint(position);
		logger.info("setting setpoint = " + position);

	}

	public void goIntoAutomaticMode() {
		if (weAreInManualMode) {
			logger.info("flipping into automatic");
			armCANTalon.changeControlMode(TalonControlMode.Position);
			weAreInManualMode = false;

			// Makes sure the new setpoint stays between the low and high
			// setpoints.
			double encoderPosition = armCANTalon.getPosition();
			double desiredSetpoint = encoderPosition;
			desiredSetpoint = Math.min(bottomSetPoint, desiredSetpoint);
			desiredSetpoint = Math.max(topSetPoint, desiredSetpoint);
			armCANTalon.setSetpoint(desiredSetpoint);
			logger.info("encoder position is " + encoderPosition);
			logger.info("setting setpoint to " + armCANTalon.getSetpoint());
		}
	}

	public void moveManually(double directionAndSpeed) {
		// Positive direction and speed moves us towards smaller setpoints.
		// Smaller setpoints are up.
		if (!weAreInManualMode) {
			logger.info("flipping into manual");
			armCANTalon.changeControlMode(TalonControlMode.PercentVbus);
			weAreInManualMode = true;

		}
		double adjustedPower = 0;
		double position = armCANTalon.getPosition();
		if (directionAndSpeed > 0) {
			// moves up toward a smaller top setpoint.
			if (position < topSetPoint) {
				adjustedPower = 0;
			} else if (position < topSetPoint + cushion) {
				adjustedPower = Math.min(creepPower, directionAndSpeed);
			} else {
				adjustedPower = directionAndSpeed;
			}
		} else {
			if (position > bottomSetPoint) {
				adjustedPower = 0;
			} else if (position > bottomSetPoint - cushion) {
				adjustedPower = Math.max(-creepPower, directionAndSpeed);
			} else {
				adjustedPower = directionAndSpeed;
			}
		}
		// if direction and speed are positive, it should move up. Therefore,
		// negative power.
		armCANTalon.set(-adjustedPower);
		if (adjustedPower != directionAndSpeed) {
			logger.info("arm power {} adjusted to {}", directionAndSpeed, adjustedPower);
		}
	}

	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		setDefaultCommand(new ArmManualCommand());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
