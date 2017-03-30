
Title:	OCR Text Capturer Application Readme Subtitle:Usage and Setup	Version: 1.0 Author:	Raghuraman Ramaswamy  Copyright:	 Raghuraman Ramaswamy.

This work is shared under  Apache License.
##OCR Text Capturer Application
==========================

###Usages

Extract text from anywhere in screen.  
Useful if working on a remote machine and need to copy text to local machine. (Launch from local machine and keep remote machine visible in a small window on local machine)

When launched will fill screen area and cover with a tint.  
Can be minimized when needed to be set aside.  
Can be closed like any other application.  
When launched click mouse on screen to draw a red rectangle.    
Can finetune rectangle using arrow keys.  
Arrow keys will expand the rectangle in direction of arrow.  
Shift + Arrow keys will shrink the rectangle using the arrow.  
CTRL+C will copy the text in rectangle into the clipboard.  
CTRL+X will copy the image in rectangle into the clipboard.  
CTRL+C or CTRL+X will be followed by a visble removal of the tint.  
After that can click through or type through on anything in the machine through the proxying screen.  
CTRL+V will paste the copied into whatever is chosen notepad/paint application.  
A button will be visible on top left corner to bring back the tint and reposition the red rectangle.  
CTRL+Q can also quit the application.

Can also be used to understand tess4j api.
Does not demonstrate image deskewing as thats not needed in current main use cases.  
If needed use  com.recognition.software.jdeskew.ImadeDeskew class of tess4j api.  
Demonstrates minor text capture enhancement.  

Requires OS to support PerPixelTranslucency. Should not be a problem.  
Tested on Windows 10.  
Can be ported to linux/mac (not tried yet.)  

###SETUP
Download from https://sourceforge.net/projects/tess4j/files/latest/download?source=typ_redirect.  
Follow steps mentioned in lib/PlaceHolder.txt.   
Also Follow steps mentioned in tessdata/PlaceHolder.txt.
Edit run.bat  
Execute run.bat

A prebuilt copy can also be downloaded from https://drive.google.com/open?id=0B7CrE8Cxe21VNHhIVFFIZ1VvWEU.  
run.bat will still need editing.  

License: Apache Licence

Author: Raghuraman Ramaswamy