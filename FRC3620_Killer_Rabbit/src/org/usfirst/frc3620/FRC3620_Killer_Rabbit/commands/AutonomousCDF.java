package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousCDF extends CommandGroup {
    
    public  AutonomousCDF() {
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
    	
    	addSequential(new AutomatedMove(36, .50));
    	addSequential(new ArmLowerCommand());
    	addSequential(new AutoWaitForArmDownCommand());
    	addSequential(new AutomatedMove(12, .50));
    	addSequential(new ArmUpCommand());
    	addSequential(new AutoWaitForArmUpCommand());
    	addSequential(new AutomatedMove(24, .50));
    }
}