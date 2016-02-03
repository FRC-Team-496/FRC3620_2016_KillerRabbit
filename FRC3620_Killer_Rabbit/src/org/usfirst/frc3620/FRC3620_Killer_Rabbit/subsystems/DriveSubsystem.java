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

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSubsystem extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController leftFront = RobotMap.driveSubsystemLeftFront;
    private final SpeedController leftRear = RobotMap.driveSubsystemLeftRear;
    private final SpeedController rightFront = RobotMap.driveSubsystemRightFront;
    private final SpeedController rightRear = RobotMap.driveSubsystemRightRear;
    private final RobotDrive robotDrive41 = RobotMap.driveSubsystemRobotDrive41;
    private final Encoder leftDriveEncoder = RobotMap.driveSubsystemLeftDriveEncoder;
    private final Encoder rightDriveEncoder = RobotMap.driveSubsystemRightDriveEncoder;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    int camera0; 
	int camera1;
	int currentCamera;
	Image frame;
	NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    
    public void arcadeDrive()
    {
    	robotDrive41.arcadeDrive(Robot.oi.driverJoystick);
    }
    
    public void setDriveForward(double move, double rotate)
    {
    	if(Math.abs(move) <= 0.2)
    	{
    		move = 0;
    	}
    	if(Math.abs(rotate) <= 0.2)
    	{
    		rotate = 0;
    	}
    	robotDrive41.arcadeDrive(move, rotate);   
    	
    
    }
    
    public void setDriveBackward(double move, double rotate)
    {
    	if(Math.abs(move) <= 0.2)
    	{
    		move = 0;
    	}
    	if(Math.abs(rotate) <= 0.2)
    	{
    		rotate = 0;
    	}
    	robotDrive41.arcadeDrive(-move, rotate);
    	
 
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new SetDriveToFrontCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        setDefaultCommand(new SetDriveToFrontCommand());
    }

	public void stopMotors() {
		robotDrive41.stopMotor();
		// TODO Auto-generated method stub
		
	}
	
	//CAMERA*CODE*****CAMERA*CODE*****CAMERA*CODE****CAMERA*CODE******CAMERA*CODE***CAMERA*CODE
	public DriveSubsystem() {
		super();
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        camera0 = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
       
        
        camera1 = NIVision.IMAQdxOpenCamera("cam1",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        
      
        currentCamera=camera1;
        NIVision.IMAQdxConfigureGrab(currentCamera);
        
	}
	
    public void cameraToFront()
    {
    	NIVision.IMAQdxStopAcquisition(currentCamera);
    	currentCamera=camera1;
    	NIVision.IMAQdxConfigureGrab(currentCamera);
    	
    	
    	
    }
    
    public void cameraToBack()
    {
    	NIVision.IMAQdxStopAcquisition(currentCamera);
    	currentCamera=camera0;
    	NIVision.IMAQdxConfigureGrab(currentCamera);
    	
    	
    }
    public void switchCamera()
    {
    	
    	NIVision.IMAQdxStopAcquisition(currentCamera);
    	if(currentCamera==camera1)
    	{
    		currentCamera=camera0;
    	}
    	else
    	{
    		currentCamera = camera1;
    	}
    	
    	NIVision.IMAQdxConfigureGrab(currentCamera);
    }
    
    public void sendFrame()
    {
    	
    	 NIVision.IMAQdxGrab(currentCamera, frame, 1);
         NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                 DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
         
         
         
         CameraServer.getInstance().setImage(frame);
    	
    }
}

