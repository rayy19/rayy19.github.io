## ----------------------------------------------------------------------------------------------------------
## TEMPLATE
## Please DO NOT change the naming convention within this template. Some changes may
## lead to your program not functioning as intended.

import sys
sys.path.append('../')

from Common_Libraries.p2_sim_lib import *

import os
from Common_Libraries.repeating_timer_lib import repeating_timer

def update_sim ():
    try:
        arm.ping()
    except Exception as error_update_sim:
        print (error_update_sim)

arm = qarm()
update_thread = repeating_timer(2, update_sim)

#---------------------------------------------------------------------------------
# STUDENT CODE BEGINS
#---------------------------------------------------------------------------------

#Spawning container in
import random
cage_list=[1,2,3,4,5,6]


#Determine location by comparing Id against known attributes
#By: Jared
def bin_location (container_id):
    id_list=[["small","red"],["small","green"],["small","blue"],["large","red"],["large","green"],["large","blue"]]
    known_attributes= id_list[container_id-1]

    small_red= [-0.584,0.248,0.399]
    small_green= [0.0,-0.648,0.366]
    small_blue= [0.0,0.648,0.366]
    large_red= [-0.464,0.197,0.322]
    large_green= [0.0,-0.503,0.322]
    large_blue= [0.0,0.503,0.322]

    if known_attributes[0]== "small":
        if known_attributes[1]== "red":
            bin_xyz= small_red
        elif known_attributes[1]== "green":
            bin_xyz= small_green
        elif known_attributes[1]== "blue":
            bin_xyz= small_blue
    elif known_attributes[0]=="large":
        if known_attributes [1]=="red":
            bin_xyz= large_red
        elif known_attributes [1]=="green":
            bin_xyz= large_green
        elif known_attributes [1]=="blue":
            bin_xyz= large_blue

    return bin_xyz


#Move arm to desired location based on motion sensor
#By: Rayyan
# EMG conditions: L>0.5 and R<0.5

def move_end_effector(x,y,z, current_location):
    
    condition = True #Condition makes it so the container must pick up or drop off before leaving loop
   
    while condition == True:
        arm.move_arm(0.406,0.0, 0.483) #Before entering loop, will return home
        time.sleep(2)
        if arm.emg_left()>0.5 and arm.emg_right()<0.5 and current_location == 0:
            print("pick up")
            arm.move_arm(0.53, 0, 0.026) #Move to pick up zone
            condition = False
        elif arm.emg_left()>0.5 and arm.emg_right()<0.5 and current_location == 1:
            arm.move_arm(x,y,z) #Move to container location
            print("target")
            condition = False
            
    time.sleep(2)


#Used to open and close the gripper
#By: Jared
#EMG Conditions: L > 0.5 and R > 0.5
    
def gripper(container_id, grip): 
    grip_condition = True

    if container_id >=4: #If a large container gripper wont close as much
        grip_amount = 22
    else:
        grip_amount = 29 #If a small container, gripper will close more

    time.sleep(2)

    while grip_condition == True:
        if arm.emg_left() > 0.5 and arm.emg_right() > 0.5 and grip == 0:
            arm.control_gripper(grip_amount)
            print("Close Gripper")
            grip_condition = False
        elif arm.emg_left() > 0.5 and arm.emg_right() > 0.5 and grip == 1:
            arm.control_gripper(-1*grip_amount)
            print("Open Gripper")
            grip_condition = False
        time.sleep(2)

 
#Used to open and close the bin
#By: Rayyan
#EMG Conditions: L<0.5 and R > 0.5 
def open_and_close_bin(container_id, check):
    bin_condition = True
    time.sleep(5)
    while bin_condition == True:
        if arm.emg_left() < 0.5 and arm.emg_right() > 0.5 and check == 0:
            open_close_bin = True
            print("Open Bin")
            bin_condition = False
        elif arm.emg_left() < 0.5 and arm.emg_right() > 0.5 and check == 1:
            open_close_bin = False
            print("Close Bin")
            bin_condition = False

    #Based on container id, coresponding bin will open
    if container_id == 4:
        arm.open_red_autoclave(open_close_bin)
    elif container_id == 5:
        arm.open_green_autoclave(open_close_bin)
    elif container_id == 6:
        arm.open_blue_autoclave(open_close_bin)
    time.sleep(3)


#Used to sequentially call and execute all functions
#By: Rayyan and Jared
def main():
    while len(cage_list) != 0:
            grip = 0
            check = 0
            current_location = 0
            i= random.choice(cage_list)
            container_id= arm.spawn_cage(i) #Spawn container from cage list
            #Effector will move to pick-up
            move_end_effector(bin_location(container_id)[0],bin_location(container_id)[1], bin_location(container_id)[2], current_location)
            gripper(container_id, grip) #Gripper will close based on size
            if container_id >= 4:
                open_and_close_bin(container_id, check) #If large, corresponding bin will open
            current_location = 1
            #Effector will move to target location
            move_end_effector(bin_location(container_id)[0],bin_location(container_id)[1], bin_location(container_id)[2], current_location)
            grip = 1
            gripper(container_id, grip) #Gripper will drop off container
            check = 1
            if container_id >= 4:
                open_and_close_bin(container_id, check) #The large open bin will close
            arm.home() #Arm returns home
            time.sleep(2)
            cage_list.remove(i) #Container will be removed from list
        


main()
print("The End")

#---------------------------------------------------------------------------------
# STUDENT CODE ENDS
#---------------------------------------------------------------------------------
