package org.usfirst.frc.team5126.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public class Robot extends SampleRobot {
	
    RobotDrive robotDrive;
    Joystick stick;
    

    
    

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
     * 
     */
    
    private TalonSRX shooter1;
    
    
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel	= 0;

    public Robot() {
        robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);	// invert the left side motors
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);		// you may need to change or remove this to match your robot
        robotDrive.setExpiration(0.1);

        stick = new Joystick(joystickChannel);
        
        
        //NOTE--- The actual address of the Talo address needs to be reference on the RoboRIO NI Dashboard. 
        //IT may not be channel 0!!!!!!!!!!!!!!!!!!
        
        shooter1 = new TalonSRX(10);
    }
        

    /**
     * Runs the motors with Mecanum drive.
     */
    public void operatorControl() {
        robotDrive.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	
        	// Use the joystick X axis for lateral movement, Y axis for forward movement, and Z axis for rotation.
        	// This sample does not use field-oriented drive, so the gyro input is set to zero.
            robotDrive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), 0);
            
            //This is a push and hold button one to use the shooter. When the button is released it should turn off. 
            if(stick.getRawButton(1)){
            	
            	//This is setting the motor controller forward at Half Speed. 
            	shooter1.set(0.5);
            	System.out.println("Shooter Forwards");
            }
            /*
             * Setting button 2 on the Joystick to go in opposite direction.
             * 
             */
            else if(stick.getRawButton(2)){
            	//This is setting the motor controller to go the opposite direction at half speed. 
            	System.out.println("Shooter Backwards");
            	shooter1.set(-0.5);
            }
            else {
            	//This will turn the motor controller off (when the button(s) is released).
            	shooter1.set(0.0);
            }
            
            
           
            
            
            Timer.delay(0.005);	// wait 5ms to avoid hogging CPU cycles
        }
    }
    
}
