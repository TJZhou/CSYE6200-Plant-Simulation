/**
 * Biological Plant Growth Simulation application
 * 
 * @author Tianju Zhou NUID 001420546
 *         Northeastern University
 *         CSYE6200: Object-Oriented Design
 *         Professor: Mark Mumson
 *
 * ----------------------Function Description-------------------------
 * A java swing application to simulate the process of plant growth.
 * 
 * Before press the start button, users can change some basic settings
 * which include growth rule, growth generation, plant color, whether 
 * to show growth process and the stems' length and radian.
 * 
 * After press the start button, the panel will show the picture of the 
 * plant, and it can show growth process when the growth process box is 
 * not "no process".
 * 
 * When the growth process box is "fast", "middle" or "slow", then users  
 * can press stop button to pause the draw process; when the draw process
 * is paused, users can press resume button to start again.
 * 
 * In the draw process, it is not able to resize the window. After the 
 * draw process is done, it is able to resize the window.
 * 
 * In the file menu, users can click “exit” to exit the application,
 * and in the help menu, users can click "about" to see the helping
 * file: Readme.md.
 * -------------------------------------------------------------------
 */

/*------------------------use cases-------------------------------*/

Use Case: <FileMenu: Exit>
Id:  1
Level: <Medium>
Description <Exit the application>
Actor(s) <Users>
Trigger <Click file menu and click exit>



Use Case: <HelpMenu: About>
Id:  2
Level: <Medium>
Description <Show helping file: Readme.md>
Actor(s) <Users>
Trigger <Click help menu and click about>



Use Case: <ruleBox>
Id:  3
Level: <Medium>
Description <Choose which rule (rule1/2/3) to simulate and draw.>
Actor(s) <Users>
Trigger <Select different rules in JComboBox>



Use Case: <genTextFiled >
Id:  4
Level: <Medium>
Description <Set the maximum generation to grow (0~9)>
Actor(s) <Users>
Trigger <Type 0 to 9 in the JTextField: genTextField>



Use Case: <colorBox>
Id:  5
Level: <Medium>
Description <Choose which color to draw.>
Actor(s) <Users>
Trigger <Select different colors in JComboBox>



Use Case: <growthBox>
Id:  6
Level: <Medium>
Description <Choose whether or not to show growth process>
Actor(s) <Users>
Trigger <Select different rules in JComboBox (true of false)>



Use Case: <lengthSlider>
Id:  7
Level: <Medium>
Description <Slide the slider to set different growth length>
Actor(s)  <Users>
Trigger <Silde the lengthSlider>



Use Case: <radianSlider>
Id:  8
Level: <Medium>
Description <Slide the slider to set different rotation radian>
Actor(s) <Users>
Trigger <Silde the radianSlider>



Use Case: <midLengthSlider>
Id:  9
Level: <Medium>
Description <Slide the slider to set different growth length of middle stems. Only available at rule2/3; in rule3 which has four growth direction, it effects the growth of two middle directions>
Actor(s) <Users>
Trigger <Silde the midLengthSlider>



Use Case: <midRadianSlider>
Id:  10
Level: <Medium>
Description <Slide the slider to set different rotation radian of middle stems. Only available at rule3; in rule3 which has four growth direction, it effects the radian of two middle directions>
Actor(s) <Users>
Trigger <Silde the midRadianSlider>



Use Case: <startBtn>
Id:  11
Level: <Medium>
Description <Start the draw process>
Actor(s) <Users>
Trigger <Press the start button>



Use Case: <stoptBtn>
Id:  12
Level: <Medium>
Description <Pause the draw process>
Actor(s) <Users>
Trigger <Press the stop button>



Use Case: <resumeBtn>
Id:  13
Level: <Medium>
Description <Resume the paused draw process>
Actor(s) <Users>
Trigger <Press the resume button>
