@REM --------------------------------
@REM Script to run JavaExtract  ..
@REM Set the following parameters
@REM --------------------------------

@REM Where is your Java Home Location ?
set JAVA_HOME=D:\j2sdk1.4.0

@REM Where is the Jakarta POI jar file ?
set POI_JAR=D:\JTest\poi\build\jakarta-poi-1.5.1-final-20020615.jar

@REM Where is your JAR that has to be extracted ?
set JAR_TO_BE_EXTRACTED=D:/JTest/JPOI/htmlconverter.jar

@REM Where do you want the output excel document to go ? 
@REM Make sure that folder where excel goes exists !!
set OUTPUT_EXCEL_LOCATION=D:/JTest/JPOI/CLZ_DATA.xls



@REM --------------------------------
@REM DONT EDIT BELOW
@REM --------------------------------

%JAVA_HOME%\bin\java -Xmx200m -classpath .;%JAR_TO_BE_EXTRACTED%;%POI_JAR%; -DJAR_FILE=%JAR_TO_BE_EXTRACTED% -DOUTPUT_FILE=%OUTPUT_EXCEL_LOCATION% JavaExtract &