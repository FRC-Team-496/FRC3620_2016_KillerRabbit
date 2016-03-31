package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPointSenecaLane5 extends CommandGroup {
    
    public  AutoPointSenecaLane5() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	double laneDistance;
    	laneDistance = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("Lane 5 Distance", 83);
    	
    	double turnAngle;
    	turnAngle = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("Lane 5 Angle", -72);
    	
    	addSequential(new AutomatedMove(85, .7));
    	addSequential(new AutomatedTurnCommand(turnAngle),2);
    	addSequential(new AutomatedMoveWithoutPID(laneDistance , .7));
    	addSequential(new AutomatedTurnCommand(-turnAngle));
    }
}