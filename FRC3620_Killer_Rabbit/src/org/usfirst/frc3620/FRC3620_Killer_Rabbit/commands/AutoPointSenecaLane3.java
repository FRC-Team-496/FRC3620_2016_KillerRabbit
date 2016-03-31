package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPointSenecaLane3 extends CommandGroup {
    
    public  AutoPointSenecaLane3() {
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
    	laneDistance = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("Lane 3 Distance", 44);
    	
    	double turnAngle;
    	turnAngle = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("Lane 3 Angle", 64);
    	
    	addSequential(new AutomatedMove(43, .7));
    	addSequential(new AutomatedTurnCommand(turnAngle));
    	addParallel(new ShooterSetCloseGoal(), 1.5);
    	addSequential(new AutomatedMove(15, .7));
    	addSequential(new AutomatedShortTurnCommand(-turnAngle));
    }
}