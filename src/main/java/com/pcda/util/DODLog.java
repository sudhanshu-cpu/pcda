package com.pcda.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DODLog {

	private static final String LOG_FILE_PATH = "/data/dod/dodLogs/"; // for windows
	public static final String Masters = "";

	public static void info(int logFileNo, Class<? extends Object> className, String log) {
		//getLogger(LOG_FILE_PATH + getFileName(logFileNo) + ".log");
		//LogManager.getLogger(className).info(log);
	}

	public static void error(int logFileNo, Class<? extends Object> className, String log) {
		getLogger(LOG_FILE_PATH + getFileName(logFileNo) + ".log");
		LogManager.getLogger(className).error(log);
	}

	public static void printStackTrace(Exception e, Class<? extends Object> classPath, int logFileNo) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		StackTraceElement stackTraceElement = null;
		String methodName = null;
		String className = null;
		String fileName = null;
		int lineNo = 0;
		StringBuilder exception = new StringBuilder();
		exception.append(e + "\n");
		for (int i = 0; i < stackTrace.length; i++) {
			stackTraceElement = stackTrace[i];
			methodName = stackTraceElement.getMethodName();
			className = stackTraceElement.getClassName();
			lineNo = stackTraceElement.getLineNumber();
			fileName = stackTraceElement.getFileName();
			exception.append("at  ");
			exception.append(className + ".");
			exception.append(methodName);
			exception.append("(" + fileName + ":" + lineNo + ")");
			exception.append("\n");
		}
		error(logFileNo, classPath, exception.toString());
	}

	private static String getFileName(int logFileNo) {
		
		String logFileName=null;

		switch(logFileNo){
		case 1 : 
			logFileName="AdgMovReport";
			break;
		case 2 :
			logFileName="CdaVoucher";
			break;
		case 3 :
			logFileName="CgdaVoucher";
			break;
		case 4 :
			logFileName="ChangePersonalNo";
			break;
		case 5 :
			logFileName="ChangeService";
			break;
		case 6 :
			logFileName="TravellerApproval";
			break;
		case 7 :
			logFileName="TravellerEditApproval";
			break;
		case 8 :
			logFileName="TransferInReemplApproval";
			break;
		case 9 :
			logFileName="TransferInApproval";
			break;
		case 10 :
			logFileName="TransferOutApproval";
			break;
		case 11 :
			logFileName="UnitMovement";     
			break;
		case 12 :
			logFileName="AirCanceltnApproval";     
			break;
		case 13 :
			logFileName="ApproveBooking";     
			break;
		case 14 :
			logFileName="RailCanceltnApproval";       
			break;
		case 15 :
			logFileName="Tdr";     
			break;
		case 16:
			logFileName="ClaimRequest";     
			break;
		case 17:
			logFileName="PNRStatus";
			break;
                case 18:
		    logFileName = "CommonLog";
			break;
         case 19 :
	 		logFileName="LoginFilter";
			break;
         case 20 :
		 	logFileName="Login";
			break;
         case 21 :
			logFileName="DisApproveProfile";
        	 break;
         case 22:
			logFileName="TravellerEdit";
			break;	
		 case 23 : 
			logFileName="TransferIn";
        	 break;
 		case 24 :
			logFileName="TransferInReempl";
 			break;
 		case 25 :
			logFileName="TransferOut";
 			break;
 		case 26 :
			logFileName="TravellerProfile";
			break;	
 		case 27 :
			logFileName="UserMasterMissed";
			break;	
 		case 28 :
			logFileName="DraftClaim";     
			break;
 		case 29 :
			logFileName="RejectedClaim";     
			break;	
 		case 30 :
			logFileName="SettledClaim";     
			break;
 		case 31 :
			logFileName="SuplementryClaim";     
			break;	
 		case 32:
			logFileName="AirReport";
			break;	
 		case 33:
			logFileName="ClaimReport";
			break;
 		 case 34:
			logFileName="DAadvanceReport";
 			break;	
 		case 35 :
			logFileName = "RailIrlaReport";
			break;    	
 		 case 36 :
			logFileName = "RaiReport";
        	 break;
 		 case 37:
			logFileName = "TdrReport";
        	 break;
 		case 38 :
			logFileName = "UserReport";
 			break;
 		case 39 :
			logFileName="AirCanceltn";     
 			break;	
 		case 40 :
			logFileName="ExcptnlBooking";     
			break;		
 		case 41 :
			logFileName="MasterMisdDashbord";     
			break;
 		case 42 :
			logFileName="NormalBooking";     
			break;
 		case 43 :
			logFileName="PrintTicket";     
			break;
 		case 44 :
			logFileName="OfflineCancltn";     
			break;
 		case 45 :
			logFileName="RailCancltn";     
			break;	
 		case 46:
			logFileName="AirTxn";
			break;
 		case 47:
			logFileName="FinalClaimRequest";
			break; 
 		 case 48:
  			logFileName = "CommanReport";
  			break;  			
 		 case 49 :
        	 logFileName="NormalRailBooking";
        	 break;
 		 case 50:
        	 logFileName="NormalAirBooking";
        	 break;	
 		case 51 :
 			logFileName="JourneyRequest";
 			break;	 
 		case 52 :
 			logFileName="budget";
 			break;
 		case 53 :
			logFileName="airRecon";     
			break;	
 		case 54 :
			logFileName="AirOffLineUpdate";     
			break;
 		case 55 :
			logFileName="EditTraveler";     
			break;
 		case 56:
			logFileName="BudgetApprove";     
			break;
 		case 57 :
			logFileName="DisApproveProfile";     
			break;
 		case 58 :
			logFileName="TravellerApproval";     
			break;
			case 59:
				logFileName = "GlobalExceptions";
				break;
		case 60:
			logFileName = "OutstandingVoucherGeneration";
			break;
		case 61:
			logFileName = "TranferLogFile";
			break;
		case 62:
			logFileName = "MasterServiceLogFile";
			break;
		case 63:
			logFileName = "OfficeServiceLogFile";
			break;
		case 64:
			logFileName = "AirTicketCancellationLog";
			break;
		case 65:
			logFileName = "ViaRequestLegBookingLog";
			break;
		case 66:
			logFileName = "RailCancellationLog";
			break;
		case 67:
			logFileName = "ExceptionalCancellationLog";
			break;
		case 68:
			logFileName = "PaoUserVerificationReportLog";
			break;
		case 69:
			logFileName = "VoucherSettlementLog";
			break;
		case 70:
			logFileName = "VoucherAcknowledgementLog";
			break;
		case 71:
			logFileName = "AirBookingUpdationLog";
			break;
		case 72:
			logFileName = "AirRefundUpdationLog";
			break;
		case 73:
			logFileName = "CurrentProfileStatus";
			break;

		default: 
			logFileName="server";
			break;
		}
		return logFileName;
	}

	private static void getLogger(String fileName) {

		String pattern = "%d [%p] [%c] [%t] %m%n";

		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		builder.setConfigurationName("DefaultRollingFileLogger");

		LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout").addAttribute("pattern", pattern);

		ComponentBuilder<?> triggeringPolicy = builder.newComponent("Policies")
				.addComponent(builder.newComponent("TimeBasedTriggeringPolicy").addAttribute("interval", 1).addAttribute("modulate", true));

		AppenderComponentBuilder appenderBuilder = builder.newAppender("LogToRollingFile", "RollingFile")
				.addAttribute("fileName", fileName).addAttribute("filePattern", fileName + ".%d{yyyy-MM-dd}")
				.add(layoutBuilder).addComponent(triggeringPolicy);

		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
		builder.add(appenderBuilder);
		rootLogger.add(builder.newAppenderRef("LogToRollingFile"));
		builder.add(rootLogger);
		Configurator.reconfigure(builder.build());
	}
}
