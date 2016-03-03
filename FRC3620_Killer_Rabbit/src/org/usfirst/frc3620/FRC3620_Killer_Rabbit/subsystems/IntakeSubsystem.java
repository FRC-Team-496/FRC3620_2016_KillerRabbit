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

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class IntakeSubsystem extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final DigitalInput ballSensorDigitalInput = RobotMap.intakeSubsystemBallSensorDigitalInput;
    //private final Encoder intakeRollerEncoder = RobotMap.intakeSubsystemIntakeRollerEncoder;
    private final SpeedController intakeRollerTalonFront = RobotMap.intakeSubsystemIntakeRollerTalonFront;
    private final SpeedController intakeRollerTalonBack = RobotMap.intakeSubsystemIntakeRollerTalonBack;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    boolean weAreIntaking = false;
    public boolean getWeAreIntaking (){
    	return weAreIntaking;
    }
    public boolean ballIsInIntake(){
    	
    	return ballSensorDigitalInput.get();
    	
    }
	public void takeIn () {
		intakeRollerTalonFront.set(-0.75);
		intakeRollerTalonBack.set(0.75);
		weAreIntaking = true;
	}
	
	public void dumpOut () {
		intakeRollerTalonFront.set(1);
		intakeRollerTalonBack.set(-1);
		}
		
	public void dropBallInShooter () {
		intakeRollerTalonFront.set(-0.5);
		intakeRollerTalonBack.set(-0.5);
	}
	
	public void intakeStop () {
		intakeRollerTalonBack.set(0);
		intakeRollerTalonFront.set(0);
		weAreIntaking = false;
	}
	public void ballIsStuck () {
		intakeRollerTalonFront.set(0.5);
		intakeRollerTalonBack.set(0.5);
	}
}

