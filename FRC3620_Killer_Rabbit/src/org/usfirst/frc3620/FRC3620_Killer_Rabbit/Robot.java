// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.*;
import org.usfirst.frc3620.logger.DataLogger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	static RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode;

	Command autonomousCommand;
	SendableChooser autoChooser;

	DataLogger robotDataLogger;
	static Logger logger;

	public static PowerDistributionPanel powerDistributionPanel;
	// public static AHRS ahrs;
	public static CANDeviceFinder canDeviceFinder;
	
	public static Preferences preferences;
	public static String rioName = null;

	public static OI oi;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static DriveSubsystem driveSubsystem;
	public static ShooterSubsystem shooterSubsystem;
	public static ArmSubsystem armSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static DummySubsystem dummySubsystem;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
        preferences = Preferences.getInstance();
        rioName = preferences.getString("rioName", null);
        
		logger = EventLogging.getLogger(Robot.class, Level.INFO);
		
		logger.info("Starting robotInit: name {}, MAC address is {}", rioName, getMACAddresses());

		RobotMap.init();

		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		driveSubsystem = new DriveSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		armSubsystem = new ArmSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		dummySubsystem = new DummySubsystem();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		// instantiate the command used for the autonomous period
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		autonomousCommand = new AutonomousCommand();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		canDeviceFinder = new CANDeviceFinder();
		logger.info("CAN devices = {}", canDeviceFinder.deviceList.toString());
		powerDistributionPanel = new PowerDistributionPanel();

		autoChooser = new SendableChooser();

		autoChooser.addDefault("Default Program", new Autonomous1());
		autoChooser.addObject("Experimental Autonomous", new Autonomous2());
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);

		robotDataLogger = new DataLogger();
		robotDataLogger.setInterval(1.000);
		robotDataLogger.setDataProvider(new RobotDataLoggerDataProvider());
		robotDataLogger.start();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */

	public void disabledInit() {
		allInit(RobotMode.DISABLED);
	}

	public void disabledPeriodic() {
		beginAllPeriodic();
		Scheduler.getInstance().run();
		endAllPeriodic();
	}

	public void autonomousInit() {
		allInit(RobotMode.AUTONOMOUS);

		autonomousCommand = (Command) autoChooser.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		beginAllPeriodic();
		Scheduler.getInstance().run();
		endAllPeriodic();
	}

	public void teleopInit() {
		allInit(RobotMode.TELEOP);

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			((Command) autonomousCommand).cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		beginAllPeriodic();
		Scheduler.getInstance().run();
		endAllPeriodic();
	}

	public void testInit() {
		// This makes sure that the autonomous stops running when
		// test starts running.
		if (autonomousCommand != null)
			((Command) autonomousCommand).cancel();

		allInit(RobotMode.TEST);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		beginAllPeriodic();
		LiveWindow.run();
		endAllPeriodic();
	}

	/*
	 * this routine gets called whenever we change modes
	 */
	void allInit(RobotMode newMode) {
		logger.info("Switching from {} to {}", currentRobotMode, newMode);
		previousRobotMode = currentRobotMode;
		currentRobotMode = newMode;

		// if any subsystems need to know about mode changes, let
		// them know here.

		Robot.armSubsystem.allInit(newMode);
		Robot.driveSubsystem.allInit(newMode);
	}

	/*
	 * these routines get called at the beginning and end of all periodics.
	 */
	void beginAllPeriodic() {
		// if some subsystems to get called in all modes at the beginning
		// of periodic, do it here

		// don't need to do anything
	}

	void endAllPeriodic() {
		// if some subsystems to get called in all modes at the end
		// of periodic, do it here

		// and log data!
		updateDashboard();
	}

	/*
	 * this method takes care of logging to the dashboard
	 */

	void updateDashboard() {
	    if (canDeviceFinder.isSRXPresent(RobotMap.armSubsystemArmCANTalon)) {
	        SmartDashboard.putNumber("Arm Encoder position", RobotMap.armSubsystemArmCANTalon.getEncPosition());
	        SmartDashboard.putNumber("Arm Get", RobotMap.armSubsystemArmCANTalon.get());
	        SmartDashboard.putNumber("Arm Closed loop", RobotMap.armSubsystemArmCANTalon.getClosedLoopError());
	        SmartDashboard.putNumber("Arm Setpoint", RobotMap.armSubsystemArmCANTalon.getSetpoint());
	        SmartDashboard.putString("Arm Control mode", RobotMap.armSubsystemArmCANTalon.getControlMode().toString());
	        SmartDashboard.putNumber("Arm Position", RobotMap.armSubsystemArmCANTalon.getPosition());
	    }
	    SmartDashboard.putBoolean("Arm Encoder Valid", armSubsystem.getEncoderIsValid());

		SmartDashboard.putNumber("NavX Angle",Robot.driveSubsystem.getAngle());
		SmartDashboard.putNumber("NavX Displacement X",Robot.driveSubsystem.getDisplacementX());
		SmartDashboard.putNumber("NavX Displacement Y",Robot.driveSubsystem.getDisplacementY());
		SmartDashboard.putNumber("NavX Displacement Z",Robot.driveSubsystem.getDisplacementZ());
		SmartDashboard.putNumber("NavX Roll",Robot.driveSubsystem.getRoll());
		SmartDashboard.putNumber("NavX Pitch",Robot.driveSubsystem.getPitch());
		
		SmartDashboard.putNumber("DriveLeftEncoder", RobotMap.driveSubsystemLeftDriveEncoder.getDistance());
		SmartDashboard.putNumber("DriveRightEncoder", RobotMap.driveSubsystemRightDriveEncoder.getDistance());		
	}

	/*
	 * subsystems can use these to find out what mode we are in
	 */
	public static RobotMode getCurrentRobotMode() {
		return currentRobotMode;
	}

	public static RobotMode getPreviousRobotMode() {
		return previousRobotMode;
	}

	public List<String> getMACAddresses() {
		List<String> returnValue = new ArrayList<>();
		try {
			for (Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces(); e.hasMoreElements();) {
				NetworkInterface network = e.nextElement();
				byte[] mac = network.getHardwareAddress();
				if (mac != null) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < mac.length; i++) {
						sb.append(String.format("%02X%s", mac[i],
								(i < mac.length - 1) ? "-" : ""));
					}
					returnValue.add(sb.toString());
				}
			}

		} catch (SocketException e) {
			logger.error("Trouble in getMACAddresses: {}", e);
			e.printStackTrace();
		}
		return returnValue;
	}

}
