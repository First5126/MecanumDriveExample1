package org.usfirst.frc.team5126.robot;


//import com.ctre.*;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public class Robot extends SampleRobot {
	
    RobotDrive robotDrive;
    Joystick stick;
    Victor leftmotor;
    

    
    

    // Channels for the wheels
    /*
     * The channels refer to the PWM connections on the roboRio. 
     * In this example we are using a combination of Victor and Talon Speed Controllers. 
     * 
     */
    final int frontLeftChannel	= 2;
    final int rearLeftChannel	= 3;
    final int frontRightChannel	= 1;
    final int rearRightChannel	= 0;
    
    /*
     * For the Test run of the Shooter on Talon SRX
     * Since the TalonSRX is connected through CAN not PWM, we must use the Class CANTalon, 
     * 
     */
    
    
    /* 
     * Adding support for Camera Server to use USB camera on RoboRio
     * 
     */
    
    public static CameraServer cam0;
    
    //private CANTalon shooter1;
    
    //Instate support for reading channels off of the Power Distribution Channel
    
    private static PowerDistributionPanel pdp = new PowerDistributionPanel();
    
   
    
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel	= 0;

    public Robot() {
        robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);	// invert the left side motors
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);		// you may need to change or remove this to match your robot
        robotDrive.setExpiration(0.1);

        stick = new Joystick(joystickChannel);
        
        
        System.out.println("The Robot has begun...... More to continue on");
        
        
        //NOTE--- The actual address of the Talo address needs to be reference on the RoboRIO NI Dashboard. 
        //IT may not be channel 0!!!!!!!!!!!!!!!!!!
        
        //shooter1 = new CANTalon(10);
        
        // Start the Camera Server 
        cam0 = CameraServer.getInstance();
        cam0.startAutomaticCapture(0);
        
        
    }
        

    /**
     * Runs the motors with Mecanum drive.
     */
    public void operatorControl() {
        robotDrive.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	
        	// Use the joystick X axis for lateral movement, Y axis for forward movement, and Z axis for rotation.
        	// This sample does not use field-oriented drive, so the gyro input is set to zero.
        	
        	/*
        	 * Multiply the X and Z axis by -1.00 to invert the controls of the joystick input.. 
        	 * 
        	 * This is to make the control system behave as one would normally expect.
        	 */
            robotDrive.mecanumDrive_Cartesian(stick.getX() * -1.00, stick.getY(), stick.getZ() * -1.00, 0);
            
            //This is a push and hold button one to use the shooter. When the button is released it should turn off. 
            if(stick.getRawButton(1)){
            	
            	//This is setting the motor controller forward at Half Speed. 
            	this.shootBall();
            	
            	System.out.println("Shooter Forwards");
            }
            /*
             * Setting button 2 on the Joystick to go in opposite direction.
             * 
             */
            else if(stick.getRawButton(2)){
            	//This is setting the motor controller to go the opposite direction at half speed. 
            	System.out.println("Shooter Backwards");
            	this.retrieveBall();
            }
            else {
            	//This will turn the motor controller off (when the button(s) is released).
            	//shooter1.set(0.0);
            	this.stopShooter();
            	
            }
            
            
            //adding a button to output begin logging current usage 
            
            if(stick.getRawButton(10)){
            	this.printOutCurrentReadings();
            }
            
            
           
            
            
            Timer.delay(0.005);	// wait 5ms to avoid hogging CPU cycles
        }
    }
    
    public void retrieveBall(){
    	//shooter1.set(1.0);
    	
    }
    
    public void shootBall(){
    	//shooter1.set(-1.0);
    }
    
    public void stopShooter(){
    	//For safety...
    	//shooter1.set(0.0);
    }
    public void printOutCurrentReadings(){
    	
    	System.out.println("Current Channel 0  : " + pdp.getCurrent(0));
    	System.out.println("Current Channel 1: " +  pdp.getCurrent(1));
    }
}
